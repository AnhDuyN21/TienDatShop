package com.example.TienDatShop.util;

import com.example.TienDatShop.entity.Account;
import com.example.TienDatShop.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MyUserDetailService implements UserDetailsService {
    private final AccountRepository accountRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepo.findByEmail(email);
        if (account == null) throw new UsernameNotFoundException("account not found");
        return new UserPrincipal(account);
    }
}
