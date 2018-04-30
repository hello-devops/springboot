package org.spoved.demo.web

import com.fasterxml.jackson.annotation.JsonView
import org.spoved.demo.database.User
import org.spoved.demo.database.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping('v1/account')
class AccountController {
    @Autowired
    private UserRepository userRepository

    @JsonView(User.View.class)
    @GetMapping
    def users() {
        userRepository.findAll()
    }
}
