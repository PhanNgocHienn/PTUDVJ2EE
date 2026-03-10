package ptudvj2ee.bai5_qlsp_jpa.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ptudvj2ee.bai5_qlsp_jpa.model.Account;
import ptudvj2ee.bai5_qlsp_jpa.repository.AccountRepository;

@Service
public class AccountService implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Account account = accountRepository.findByLoginName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Could not find user"));
        Set<SimpleGrantedAuthority> authorities = account.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
        return new org.springframework.security.core.userdetails.User(
                account.getLogin_name(),
                account.getPassword(),
                authorities);
    }
}
