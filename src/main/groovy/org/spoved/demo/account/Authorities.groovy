package org.spoved.demo.account

import org.springframework.security.core.GrantedAuthority

class Authorities {
    static class Authority implements GrantedAuthority {
        String authority

        @Override
        String getAuthority() {
            return authority
        }
    }

    enum Types {
        ROLE_ADMIN(new Authority(authority: 'ROLE_ADMIN')),
        ROLE_API_VIEWER(new Authority(authority: 'ROLE_API_VIEWER'))

        Authority authority

        Types(Authority authority) {
            this.authority = authority
        }
    }
}
