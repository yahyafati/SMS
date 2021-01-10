package com.yahya.growth.stockmanagementsystem.controller;

import com.google.common.collect.Lists;
import com.yahya.growth.stockmanagementsystem.model.security.Authority;
import com.yahya.growth.stockmanagementsystem.model.security.Role;
import com.yahya.growth.stockmanagementsystem.service.RoleService;
import com.yahya.growth.stockmanagementsystem.utilities.AuthorityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/group")
public class RoleController implements BasicControllerSkeleton<Role>{

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
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
        roleService.deleteById(id);
        return "redirect:/group";
    }
}