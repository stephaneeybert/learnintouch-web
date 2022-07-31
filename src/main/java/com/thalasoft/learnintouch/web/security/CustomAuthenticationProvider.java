package com.thalasoft.learnintouch.web.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.thalasoft.learnintouch.data.jpa.domain.Admin;
import com.thalasoft.learnintouch.data.service.jpa.AdminService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    AdminService adminService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String password = authentication.getCredentials().toString();
        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<SimpleGrantedAuthority>();
        Admin admin = adminService.findByLogin(login);
        if (admin != null) {
            if (adminService.checkPassword(admin, password)) {
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                return new UsernamePasswordAuthenticationToken(login, password, grantedAuthorities);
            }
//        } else if (adminService.findByLoginAndPassword(login, password) != null) {
//            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//            return new UsernamePasswordAuthenticationToken(login, password, grantedAuthorities);
        }
        throw new BadCredentialsException("The login " + authentication.getPrincipal() + " and password could not match.");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

//    private class MyAuthoritiesMapper implements GrantedAuthoritiesMapper {
//
//        private static final String LDAP_ROLE_ADMIN = "admin";
//        private static final String LDAP_ROLE_USER = "user";
//
//        public Collection<? extends GrantedAuthority> mapAuthorities(Collection<? extends GrantedAuthority> authorities) {
//            Set<MyAuthority> roles = EnumSet.noneOf(MyAuthority.class);
//
//            for (GrantedAuthority grantedAuthority : authorities) {
//                if (LDAP_ROLE_ADMIN.equals(grantedAuthority.getAuthority())) {
//                    roles.add(MyAuthority.ROLE_ADMIN);
//                } else if (LDAP_ROLE_USER.equals(grantedAuthority.getAuthority())) {
//                    roles.add(MyAuthority.ROLE_USER);
//                }
//            }
//
//            return roles;
//        }
//
//    }
//
//    private enum MyAuthority implements GrantedAuthority {
//        ROLE_ADMIN, ROLE_USER;
//
//        public String getAuthority() {
//            return name();
//        }
//    }

}