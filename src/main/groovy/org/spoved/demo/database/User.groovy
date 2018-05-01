package org.spoved.demo.database

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonView
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils

import javax.persistence.*

@Entity
@Table(name = 'users')
class User implements Serializable {
    private static final long serialVersionUID = 1L

    @Id
    @GeneratedValue
    Long id
    @JsonView(View.class)
    String username
    String password
    @Column(insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    Date registeredDate

    // Authority
    @OneToMany(fetch = FetchType.EAGER, mappedBy = 'user')
    Set<UserAuth> auths

    UserAuth addAuth(long authId) {
        UserAuth userAuth = new UserAuth(pk: new UserAuth.PK(userId: id, authId: authId))
        this.auths.add(userAuth)
        return userAuth
    }

    @JsonIgnore
    Collection<? extends GrantedAuthority> getAuthorities() {
        if (auths == null || auths.size() == 0)
            return []
        return AuthorityUtils.createAuthorityList(AuthorityUtils.authorityListToSet(auths).toArray(new String[auths.size()]))
    }

    // View
    interface View {}
}
