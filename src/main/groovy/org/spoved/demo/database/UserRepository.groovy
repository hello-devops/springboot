package org.spoved.demo.database

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository extends JpaRepository<User, Long> {
    User findOneById(long id)

    User findOneByUsername(String username)
}
