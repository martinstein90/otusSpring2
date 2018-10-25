package com.martin.security;

import org.springframework.security.acls.model.Permission;
import org.springframework.security.core.Authentication;

public interface SecurityService {


    void addSecurity(Authentication authentication, long id, Class<?> type);

    void addPermission(long id, Class<?> type, Permission permission, String principal);

    boolean isGranted(long id, Class<?> type, Permission permission, String principal);
}
