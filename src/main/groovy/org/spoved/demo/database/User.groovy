package org.spoved.demo.database

import javax.persistence.*

@Entity
@Table(name = 'users')
class User implements Serializable {
    private static final long serialVersionUID = 1L

    @Id
    @GeneratedValue
    Long id
    String username
    String password
    @Column(insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    Date registeredDate
}
