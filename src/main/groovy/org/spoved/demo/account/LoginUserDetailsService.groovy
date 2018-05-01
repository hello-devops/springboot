package org.spoved.demo.account

import org.spoved.demo.database.User
import org.spoved.demo.database.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class LoginUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository

    @Override
    UserDetails loadUserByUsername(String idString) throws UsernameNotFoundException {
        long id = Long.parseLong(idString)
        User user = userRepository.findOneById(id)
        if (user == null) {
            throw new UsernameNotFoundException("The requested user is not found. $idString")
        }

        return new LoginUser(user)
    }
}
