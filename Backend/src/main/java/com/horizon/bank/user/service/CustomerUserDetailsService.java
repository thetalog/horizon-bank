package com.horizon.bank.user.service;
// package com.horizon.bank.auth.service;

// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;

// import com.horizon.bank.auth.entity.User;
// import com.horizon.bank.auth.repository.UserRepository;

// @Service
// public class CustomerUserDetailsService implements UserDetailsService {
//     private final UserRepository userRepository;

//     public CustomerUserDetailsService(UserRepository userRepository) {
//         this.userRepository = userRepository;
//     }
//     @Override
//     public UserDetails loadUserByUsername(String username) {
//         User user = userRepository.findByEmail(username);

//         if (user == null) {
//             throw new UsernameNotFoundException("User not found");
//         }

//         return org.springframework.security.core.userdetails.User
//             .withUsername(user.getEmail())
//             .password(user.getPassword())
//             .authorities("USER")
//             .build();
//     }
    
// }
