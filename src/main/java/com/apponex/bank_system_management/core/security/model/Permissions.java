package com.apponex.bank_system_management.core.security.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@RequiredArgsConstructor
public enum Permissions  {
    ADMIN_READ("admin::read"),
    ADMIN_UPDATE("admin::update"),
    ADMIN_CREATE("admin::create"),
    ADMIN_DELETE("admin::delete"),
    MANAGER_READ("MANAGER::read"),
    MANAGER_UPDATE("MANAGER::update"),
    MANAGER_CREATE("MANAGER::create"),
    MANAGER_DELETE("MANAGER::delete"),
    CUSTOMER_READ("CUSTOMER::read"),
    CUSTOMER_UPDATE("CUSTOMER::update"),
    CUSTOMER_CREATE("CUSTOMER::create"),
    CUSTOMER_DELETE("CUSTOMER::delete"),
    OFFICER_READ("OFFICER::read"),
    OFFICER_UPDATE("OFFICER::update"),
    OFFICER_CREATE("OFFICER::create"),
    OFFICER_DELETE("OFFICER::delete")
    ;

    @Getter
    private final String permissions;

}
