package br.com.southsystem.skiils_up.services;

import br.com.southsystem.skiils_up.models.User;
import br.com.southsystem.skiils_up.repositories.UserRepository;
import br.com.southsystem.skiils_up.security.UserSS;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }

        return new UserSS(user.getId(), user.getEmail(), user.getPassword(),user.getClient());
    }
}
