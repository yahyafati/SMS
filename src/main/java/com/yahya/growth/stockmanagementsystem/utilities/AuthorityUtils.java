package com.yahya.growth.stockmanagementsystem.utilities;

import com.yahya.growth.stockmanagementsystem.security.Permission;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AuthorityUtils {

    public static Map<String, Boolean[]> getPermissionMap(Set<String> currentAuthority) {
        Map<String, Boolean[]> authorities = Arrays.stream(Permission.values())
                .filter(permission -> !permission.getPermission().contains("report:"))
                .filter(permission -> !permission.getPermission().contains("it:"))
                .filter(permission -> !permission.getPermission().split(":")[1].equals("write"))
                .map(permission -> permission.getPermission().split(":")[0])
                .collect(Collectors.toMap(s -> s, s -> new Boolean[]{currentAuthority.contains(s+":read"), currentAuthority.contains(s+":write")}));
        return authorities;
    }

}
