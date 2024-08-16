package com.apponex.bank_system_management.core.security.model.role;

import com.apponex.bank_system_management.core.security.model.Permissions;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum Role {
    USER(Collections.emptySet()),
    CUSTOMER(
            Set.of(
                    Permissions.CUSTOMER_CREATE,
                    Permissions.CUSTOMER_READ,
                    Permissions.CUSTOMER_UPDATE,
                    Permissions.CUSTOMER_DELETE
            )
    ),
    OFFICER(
            Set.of(
                    Permissions.OFFICER_CREATE,
                    Permissions.OFFICER_READ,
                    Permissions.OFFICER_UPDATE,
                    Permissions.OFFICER_DELETE
            )
    ),
    MANAGER(
            Set.of(
                    Permissions.MANAGER_CREATE,
                    Permissions.MANAGER_READ,
                    Permissions.MANAGER_UPDATE,
                    Permissions.MANAGER_DELETE
            )
    ),
    ADMIN(
            Set.of(
                Permissions.ADMIN_READ,
                Permissions.ADMIN_UPDATE,
                Permissions.ADMIN_DELETE,
                Permissions.ADMIN_CREATE,
                Permissions.MANAGER_CREATE,
                Permissions.MANAGER_READ,
                Permissions.MANAGER_UPDATE,
                Permissions.MANAGER_DELETE
            )
    )
    ;

    @Getter
    private final Set<Permissions> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermissions()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
