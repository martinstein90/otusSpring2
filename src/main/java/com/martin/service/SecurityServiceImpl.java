package com.martin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.*;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private MutableAclService aclService;

    @Override
    public void addSecurity(Authentication authentication, long id, Class<?> type) {

        Sid sidOwner = new PrincipalSid(authentication);
        ObjectIdentityGenerator generator = new ObjectIdentityRetrievalStrategyImpl();
        ObjectIdentity identity = generator.createObjectIdentity(id, type.getName());

        AuditableAcl acl = (AuditableAcl)aclService.createAcl(identity);
        acl.setOwner(sidOwner);
        acl.setParent(null);
        acl.setEntriesInheriting(false);

        acl.insertAce(acl.getEntries().size(), BasePermission.ADMINISTRATION, sidOwner, true);
        acl.updateAuditing(acl.getEntries().size()-1, true, true);
        acl.insertAce(acl.getEntries().size(), BasePermission.ADMINISTRATION, new GrantedAuthoritySid("ROLE_ADMIN"), true);
        acl.updateAuditing(acl.getEntries().size()-1, true, true);

        aclService.updateAcl(acl);
    }

    @Override
    public void addPermission(long id, Class<?> type, Permission permission, String principal) {

        ObjectIdentity identity = new ObjectIdentityImpl(type.getName(), id);
        MutableAcl acl = (MutableAcl) aclService.readAclById(identity);
        acl.insertAce(acl.getEntries().size(), permission, new PrincipalSid(principal), true);
    }

    @Override
    public boolean isGranted(long id, Class<?> type, Permission permission, String principal) {
        ObjectIdentity oid = new ObjectIdentityImpl(type.getName(), id);
        Acl acl = aclService.readAclById(oid);
        List<Permission> permissions = Arrays.asList(permission);
        List<Sid> sids = Arrays.asList((Sid) new PrincipalSid(principal));

        if (!acl.isGranted(permissions, sids, true)) {
            throw new RuntimeException("Access denied.");
        }
        return true;
    }

}
