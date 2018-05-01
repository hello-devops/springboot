package org.spoved.demo.web

import com.fasterxml.jackson.annotation.JsonView
import io.swagger.annotations.ApiOperation
import org.spoved.demo.account.LoginUser
import org.spoved.demo.database.User
import org.spoved.demo.database.UserRepository
import org.spoved.demo.util.UserUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping('v1/account')
class AccountController {
    @Autowired
    private UserRepository userRepository

    @Autowired
    private UserUtil userUtil

    @JsonView(User.View.class)
    @ApiOperation(value = '로그인 정보')
    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    def my(@AuthenticationPrincipal LoginUser LoginUser) {
        if (LoginUser?.user == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        return userRepository.findOneById(LoginUser.user.id)
    }

    @JsonView(User.View.class)
    @ApiOperation(value = '로그인')
    @PutMapping(value = 'login', produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    def login(String username, String password) {
        User user = userRepository.findOneByUsername(username)
        if (user == null)
            return ResponseEntity.notFound().build()
        userUtil.login(user, password)
        return ResponseEntity.ok().build()
    }

    @JsonView(User.View.class)
    @ApiOperation(value = '회원가입')
    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    def register(String username, String password) {
        User user = userRepository.findOneByUsername(username)
        if (user != null)
            return ResponseEntity.status(HttpStatus.CONFLICT).build()
        user = userUtil.register(username, password)
        userUtil.login(user, password)
        return ResponseEntity.created().build()
    }
}
