package org.spoved.demo.database

import javax.persistence.*

@Entity
@Table
class Authority implements Serializable {
    @Id
    @GeneratedValue
    Long id
    String name

    @OneToMany(mappedBy = 'authority')
    Set<UserAuth> userAuths

    Authority() {
        userAuths = new HashSet<>()
    }
}
