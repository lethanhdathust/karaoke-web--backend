package com.programming.karaoke.configure;

import com.programming.karaoke.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
//    todo Việc sử dụng @Override để ghi đè phương thức trong interface UserDetailsService không thích hợp trong trường hợp này,
//    todo vì Spring Security không tìm kiếm các lớp được ghi đè để xác thực người dùng.

    private final UserRepository userRepository;
//    khi sử dụng phương thức userDetailsService() này trong ứng dụng của bạn,
//    bạn sẽ nhận được một đối tượng UserDetailsService,
//    nhưng khi Spring Security sử dụng nó để tìm kiếm người dùng,
//    nó sẽ sử dụng phương thức loadUserByUsername() của anonymous inner class
//    được trả về bởi phương thức userDetailsService() để trả về một đối tượng
//    UserDetails mô tả thông tin về người dùng.
    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                return userRepository.findByEmailAddress(email).orElseThrow(() -> new UsernameNotFoundException("Cann't found the user name"));
            }
        };
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoded());
        return daoAuthenticationProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
     return   configuration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoded() {
        return new BCryptPasswordEncoder();
    }
}
