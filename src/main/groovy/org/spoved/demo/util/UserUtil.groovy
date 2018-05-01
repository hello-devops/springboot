package org.spoved.demo.util

import org.spoved.demo.account.LoginUserDetailsService
import org.spoved.demo.database.User
import org.spoved.demo.database.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class UserUtil {
    @Autowired
    private UserRepository userRepository

    @Autowired
    private PasswordEncoder passwordEncoder
    @Autowired
    private AuthenticationManager authenticationManager
    @Autowired
    private LoginUserDetailsService loginUserDetailsService

    User register(String username, String password) {
        User user = new User(username: username, password: passwordEncoder.encode(password))
        return userRepository.save(user)
    }

    void login(User user, String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.id.toString(), password)
        Authentication authenticatedUser = authenticationManager.authenticate(token)
        SecurityContextHolder.getContext().setAuthentication(authenticatedUser)
    }

    void login(User user) {
        UserDetails userDetails = loginUserDetailsService.loadUserByUsername(user.id.toString())
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, userDetails.password, userDetails.authorities)
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken)
    }
}
