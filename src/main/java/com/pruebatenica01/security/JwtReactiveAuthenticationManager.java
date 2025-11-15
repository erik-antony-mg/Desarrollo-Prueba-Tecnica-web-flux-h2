package com.pruebatenica01.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtReactiveAuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtHelper jwtHelper;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        final String jwtToken = authentication.getCredentials().toString();
        if(!jwtHelper.validateJwt(jwtToken)){
            return Mono.empty();
        }

        final String email = jwtHelper.getUsernameFromJwt(jwtToken);
        final List<String> roles = jwtHelper.getRolesFromJwt(jwtToken);

        final List<GrantedAuthority> authorities=roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_"+role))
                .collect(Collectors.toList());

        final Authentication authJwt = new UsernamePasswordAuthenticationToken(email, null, authorities);
        return Mono.just(authJwt);
    }
}
