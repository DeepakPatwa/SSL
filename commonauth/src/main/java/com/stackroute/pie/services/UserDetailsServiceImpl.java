package com.stackroute.pie.services;

import com.stackroute.pie.domain.CommonAuth;
import com.stackroute.pie.domain.Insured;
import com.stackroute.pie.repository.CommonAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    CommonAuthRepository commonAuthRepository;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      CommonAuth commonAuth = commonAuthRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Insured Not Found with -> username or email : " + username));

        return UserPrinciple.build(commonAuth);
    }
}
