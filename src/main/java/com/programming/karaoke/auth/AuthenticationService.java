package com.programming.karaoke.auth;


import com.programming.karaoke.configure.JwtService;
import com.programming.karaoke.model.user.Role;
import com.programming.karaoke.model.user.User;
import com.programming.karaoke.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {
        var x =  repository.findByEmailAddress(request.getEmailAddress());

        if(x.isPresent())
        {
            return AuthenticationResponse.builder().token("Null").build();
        }
        var user = User.builder()
                .emailAddress(request.getEmailAddress())
                .telephoneNumber(request.getTelephoneNumber())
                .fullName(request.getFullName())
                .password(passwordEncoder.encode(request.getPassword()) )
                .role(Role.USER)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmailAddress(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmailAddress(request.getEmailAddress()).orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
