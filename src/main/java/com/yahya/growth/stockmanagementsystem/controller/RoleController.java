package com.yahya.growth.stockmanagementsystem.controller;

import com.google.common.collect.Lists;
import com.yahya.growth.stockmanagementsystem.model.security.Authority;
import com.yahya.growth.stockmanagementsystem.model.security.Role;
import com.yahya.growth.stockmanagementsystem.service.AuthorityService;
import com.yahya.growth.stockmanagementsystem.service.RoleService;
import com.yahya.growth.stockmanagementsystem.utilities.AuthorityUtils;
import com.yahya.growth.stockmanagementsystem.utilities.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/group")
public class RoleController implements BasicControllerSkeleton<Role> {

    private final RoleService roleService;
    private final AuthorityService authorityService;

    @Autowired
    public RoleController(RoleService roleService, AuthorityService authorityService) {
        this.roleService = roleService;
        this.authorityService = authorityService;
    }

    @ModelAttribute("title")
    public String getPageTitle() {
        return "User Group";
    }

    @ModelAttribute("active")
    public List<String> getCurrentlyActive() {
        return Lists.newArrayList("group", "adminTools");
    }

    @Override
    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("groups", roleService.findAll());
//        return "group/all";
        model.addAttribute("pageName", "group/all");
        return "common/header";
    }

    @Override
    @GetMapping("/{id}")
    public String detail(@PathVariable int id, Model model) {
        Role role = roleService.findById(id);
        Set<String> currentAuthority = role.getAuthorities().stream().map(Authority::getAuthority).collect(Collectors.toSet());
        Map<String, Boolean[]> authorities = AuthorityUtils.getPermissionMap(currentAuthority);

        model.addAttribute("allAuthorities", authorities);
        model.addAttribute("group", roleService.findById(id));
        model.addAttribute("reportPermissions", new boolean[] {currentAuthority.contains("report:store"), currentAuthority.contains("report:all")});
        model.addAttribute("permissions", Lists.newArrayList());
//        return "group/detail";
        model.addAttribute("pageName", "group/detail");
        return "common/header";
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('role:write')")
    public String changePermissions(@PathVariable int id, @RequestParam("idPermission") List<String> permissions) {
        Role role = roleService.findById(id);
        role.getAuthorities().clear();
        permissions.stream()
                .map(authorityService::findByName)
                .forEach(role.getAuthorities()::add);
        roleService.save(role);
        return "redirect:/group/" + id;
    }

    @Override
    @GetMapping("/new")
    @PreAuthorize("hasAuthority('role:write')")
    public String addNewItem(Model model) {
        model.addAttribute("group", new Role());
        model.addAttribute("action", "new");
//        return "group/edit";
        model.addAttribute("pageName", "group/edit");
        return "common/header";
    }

    @Override
    @PostMapping("/new")
    @PreAuthorize("hasAuthority('role:write')")
    public String addNewPOST(Role role) {
        role = roleService.save(role);
        return "redirect:/group/" + role.getId();
    }

    @Override
    @GetMapping("/edit")
    @PreAuthorize("hasAuthority('role:write')")
    public String edit(@RequestParam int id, Model model) {
        model.addAttribute("group", roleService.findById(id));
        model.addAttribute("action", "edit");
//        return "group/edit";
        model.addAttribute("pageName", "group/edit");
        return "common/header";
    }

    @Override
    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('role:write')")
    public String editPost(@RequestParam int id, Role role) {

        role.setId(id);
        roleService.save(role);
        return "redirect:/group/" + id;
    }

    @Override
    @Deprecated
    public String delete(int id) {
        throw new UnsupportedOperationException("This delete operation is unsupported.");
    }

    @GetMapping("/delete")
    @PreAuthorize("hasAuthority('role:write')")
    public RedirectView delete(@RequestParam int id, RedirectAttributes redir) {
        RedirectView redirectView = new RedirectView("/group", true);
        if (!roleService.deleteById(id)) {
            FlashMessage flashMessage = new FlashMessage("Role can't be deleted as there are currently users assigned to this role. Assign these users to different roles first.",
                    "", FlashMessage.Type.ERROR);
            redir.addFlashAttribute("dialogFlash", flashMessage);
        }
        return redirectView;
    }
}
