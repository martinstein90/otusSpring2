package com.martin.domain;

import com.martin.security.GrantedAuthorityImpl;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

import static com.martin.domain.User.FIELD_PHONE_NUMBER;
import static com.martin.domain.User.TABLE_TITLE;

@Data @NoArgsConstructor @ToString() @EqualsAndHashCode(exclude = {"id"})
@Entity @Table(name = TABLE_TITLE)
public class User implements UserDetails {

    public static final String TABLE_TITLE = "users";
    public static final String FIELD_USERNAME = "Username";
    public static final String FIELD_PASSWORD = "Password";
    public static final String FIELD_PHONE_NUMBER = "PhoneNumber";
    public static final String FIELD_SMS = "Sms";
    public static final String FIELD_AUTHORITIES = "Authorities";
    public static final String FIELD_ACCOUNT_NON_EXPIRED = "AccountNonExpired";
    public static final String FIELD_ACCOUNT_NON_LOCKED = "AccountNonLocked";
    public static final String FIELD_CREDENTIALS_NON_LOCKED = "CredentialsNonExpired";
    public static final String FIELD_ENABLED = "Enabled";

    public static final String COLLECTION_TABLE_TITLE = "Roles";
    public static final String FILED_JOIN_COLUMN_COLLECTION = "Owner_id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Getter @Setter
    @Column(name = FIELD_USERNAME)
    private String username;

    @Getter @Setter
    @Column(name = FIELD_PASSWORD)
    private String password;

    @Getter @Setter
    @Column(name = FIELD_PHONE_NUMBER)
    private String phoneNumber;

    @Getter @Setter
    @Column(name = FIELD_SMS)
    private String sms;

    @Getter @Setter
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable (  name = COLLECTION_TABLE_TITLE,
                        joinColumns = @JoinColumn(name = FILED_JOIN_COLUMN_COLLECTION))
    private Collection<GrantedAuthorityImpl> authorities;

    @Getter @Setter
    @Column(name = FIELD_ACCOUNT_NON_EXPIRED)
    private boolean accountNonExpired;

    @Getter @Setter
    @Column(name = FIELD_ACCOUNT_NON_LOCKED)
    private boolean accountNonLocked;

    @Getter @Setter
    @Column(name = FIELD_CREDENTIALS_NON_LOCKED)
    private boolean credentialsNonExpired;

    @Getter @Setter
    @Column(name = FIELD_ENABLED)
    private boolean enabled;

    public User(String username,
                String password,
                String phoneNumber,
                Collection<GrantedAuthorityImpl> authorities,
                boolean accountNonExpired,
                boolean accountNonLocked,
                boolean credentialsNonExpired,
                boolean enabled) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.authorities = authorities;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }
}
