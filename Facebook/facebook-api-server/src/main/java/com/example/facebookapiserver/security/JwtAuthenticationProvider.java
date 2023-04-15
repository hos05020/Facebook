package com.example.facebookapiserver.security;

import static org.apache.commons.lang3.ClassUtils.isAssignable;
import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

import com.example.facebookapiserver.exception.NotFoundException;
import com.example.facebookapiserver.domain.user.Email;
import com.example.facebookapiserver.domain.user.Role;
import com.example.facebookapiserver.domain.user.User;
import com.example.facebookapiserver.service.user.UserService;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final Jwt jwt;

    private final UserService userService;

    public JwtAuthenticationProvider(Jwt jwt, UserService userService) {
        this.jwt = jwt;
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return isAssignable(JwtAuthenticationToken.class,authentication);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) authentication;
        return processUserAuthentication(authenticationToken.authenticationRequest());

    }

    private Authentication processUserAuthentication(AuthenticationRequest request) {
        try {
            User user = userService.login(new Email(request.getPrincipal()), request.getCredentials());
            JwtAuthenticationToken authenticated =
                // 응답용 Authentication 인스턴스를 생성한다.
                // JwtAuthenticationToken.principal 부분에는 JwtAuthentication 인스턴스가 set 된다.
                // 로그인 완료 전 JwtAuthenticationToken.principal 부분은 Email 인스턴스가 set 되어 있었다.
                new JwtAuthenticationToken(new JwtAuthentication(user.getSeq(), user.getName(), user.getEmail()), null, createAuthorityList(Role.USER.value()));
            // JWT 값을 생성한다.
            // 권한은 ROLE_USER 를 부여한다.
            String apiToken = user.newApiToken(jwt, new String[]{Role.USER.value()});
            authenticated.setDetails(new AuthenticationResult(apiToken, user));
            return authenticated;
        } catch (NotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException(e.getMessage());
        } catch (DataAccessException e) {
            throw new AuthenticationServiceException(e.getMessage(), e);
        }
    }

}
