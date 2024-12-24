package com.capstone.africa.semicolon.comms_bridge.services.jwt_services;


import com.capstone.africa.semicolon.comms_bridge.entities.AppUser;
import com.capstone.africa.semicolon.comms_bridge.entities.UserPrincipal;
import com.capstone.africa.semicolon.comms_bridge.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        AppUser appUser = userRepository.findByUserName(userName);
        if(appUser == null){
            throw new UsernameNotFoundException("User not found");
        }
        return new UserPrincipal(appUser);
    }


}