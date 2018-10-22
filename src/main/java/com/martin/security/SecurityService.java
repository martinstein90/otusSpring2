package com.martin.security;

public interface SecurityService {

    void addSecurity(String principal, long authorId, Class<?> type) ;
    void addPermission(long authorId, Class<?> type);
}
