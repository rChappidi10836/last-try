package com.gdp.backend.controller;

import com.gdp.backend.common.Constants;
import com.gdp.backend.domain.Response;
import com.gdp.backend.enums.EStatusCode;
import com.gdp.backend.service.GDPUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This Controller class used for getting user Roles related data.
 * @author gdp
 *
 */
@RestController
@RequestMapping("roles")
public class RolesController {
    @Autowired
    GDPUserDetailsService gdpUserDetailsService;

    /**
     * This API would be use for getting user Roles related data.
     * @return response this class contains user Roles related data.
     */
    @GetMapping("/my-role")
    public ResponseEntity<Response<Set<String>>> getMyUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Set<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        Response<Set<String>> response = new Response<>();
        response.setStatusCode(EStatusCode.SUCCESS.name());
        response.setOpcoid(gdpUserDetailsService.getUserOpcoId());
        response.setData(Collections.singletonList(roles));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/my-current-role")
    public Set<String> getMyUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Set<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        Response<Set<String>> response = new Response<>();
        response.setStatusCode(EStatusCode.SUCCESS.name());
        response.setOpcoid(gdpUserDetailsService.getUserOpcoId());
        response.setData(Collections.singletonList(roles));
        return roles;
    }

}
