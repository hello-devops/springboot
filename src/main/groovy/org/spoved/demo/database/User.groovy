package org.spoved.demo.database

import com.fasterxml.jackson.annotation.JsonView

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

    interface View {}
}
