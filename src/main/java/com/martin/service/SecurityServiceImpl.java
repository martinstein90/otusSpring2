package com.martin.service;

import com.martin.domain.Author;
import com.martin.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.ObjectIdentityRetrievalStrategyImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private MutableAclService aclService;

    private long u;

    @Override
    public void addSecurity(String owner, long authorId, Class<?> type) {
        System.out.println("----------------------addSecurity " + owner + authorId + type);

        Sid sidOwner = new PrincipalSid(owner);
        //TODO Доступ к автору по умолчанию имеют ROLE_ADMIN, owner, Permission.ADMINISTRATION

        ObjectIdentityGenerator generator = new ObjectIdentityRetrievalStrategyImpl();
        ObjectIdentity identity = generator.createObjectIdentity(authorId, Author.class.getName());

        MutableAcl acl = aclService.createAcl(identity);
        acl.setOwner(sidOwner);

        acl.insertAce(acl.getEntries().size(), BasePermission.READ, new PrincipalSid("Pety"), true);

        aclService.updateAcl(acl);

        u=authorId;

    }

    public void addPermission(long authorId, Class<?> type) {
        System.out.println("------------------------addPermission");
        ObjectIdentity identity = new ObjectIdentityImpl(Author.class.getName(), u);

        MutableAcl acl = (MutableAcl) aclService.readAclById(identity);
        System.out.println("acl = " + acl);
        acl.getOwner();
        acl.insertAce(acl.getEntries().size(), BasePermission.READ, new PrincipalSid("Katya"), true);
    }



    /*
    *
acl.insertAce(acl.getEntries().size(),
BasePermission.ADMINISTRATION, admin, true);
    * */


    /*
final ObjectIdentity oid = new ObjectIdentityImpl(BankAccount.class, 10);
Acl acl = aclService.readAclById(oid);
final List<Permission> permissions = Arrays.asList(BasePermission.READ);
final List<Sid> sids = Arrays.asList((Sid) new GrantedAuthoritySid("ROLE_ADMIN"));

if (!acl.isGranted(permissions, sids, false)) {
throw new RuntimeException("Access denied.");
}
 */
}
