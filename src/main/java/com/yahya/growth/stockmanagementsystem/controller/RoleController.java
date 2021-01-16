package com.yahya.growth.stockmanagementsystem.controller;

import com.google.common.collect.Lists;
import com.yahya.growth.stockmanagementsystem.model.security.Authority;
import com.yahya.growth.stockmanagementsystem.model.security.Role;
import com.yahya.growth.stockmanagementsystem.service.AuthorityService;
import com.yahya.growth.stockmanagementsystem.service.RoleService;
import com.yahya.growth.stockmanagementsystem.utilities.AuthorityUtils;
import com.yahya.growth.stockmanagementsystem.utilities.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/group")
public class RoleController implements BasicControllerSkeleton<Role>{

    private final RoleService roleService;
    private final AuthorityService authorityService;

    @Autowired
    public RoleController(RoleService roleService, AuthorityService authorityService) {
        this.roleService = roleService;
        this.authorityService = authorityService;
    }

    @Override
    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("groups", roleService.findAll());
        return "group/all";
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
        return "group/detail";
    }

    @PostMapping("/{id}")
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
    public String addNewItem(Model model) {
        model.addAttribute("group", new Role());
        model.addAttribute("action", "new");
        return "group/edit";
    }

    @Override
    @PostMapping("/new")
    public String addNewPOST(Role role) {
        role = roleService.save(role);
        return "redirect:/group/" + role.getId();
    }

    @Override
    @GetMapping("/edit")
    public String edit(@RequestParam int id, Model model) {
        model.addAttribute("group", roleService.findById(id));
        model.addAttribute("action", "edit");
        return "group/edit";
    }

    @Override
    @PostMapping("/edit")
    public String editPost(@RequestParam int id, Role role) {

        role.setId(id);
        roleService.save(role);
        return "redirect:/group/" + id;
    }

    @Override
    @GetMapping("/delete")
    public String delete(@RequestParam int id) {
        // TODO What should happen to users who are members of a role when the role gets deleted?
        roleService.deleteById(id);
        return "redirect:/group";
    }
}
