package com.gdp.backend.service;

import com.gdp.backend.common.Constants;
import com.gdp.backend.model.GDPUser;
import com.gdp.backend.repository.GDPUserRepository;
import com.gdp.backend.repository.ResourceRepository;
import com.gdp.backend.util.CurrentUsernameUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * This Service class used for Web SecurityConfig to know the details GDP User details.
 * @author gdp
 *
 */
@Service
public class GDPUserDetailsService implements UserDetailsService {

	String ROLE_PREFIX = "ROLE_";
    @Autowired
    private GDPUserRepository gdpUserRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    public GDPUserDetailsService(GDPUserRepository gdpUserRepository,ResourceRepository resourceRepository) {
        this.gdpUserRepository = gdpUserRepository;
        this.resourceRepository = resourceRepository;
    }

    
    /**
     * This method would be use for load the User details using UserName.
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        try {
            String Username=username;
            System.out.println(Username);
            Optional<GDPUser> user = gdpUserRepository.findByUsername(Username);
            Constants.USER = user ;
            if (!user.isPresent()) {
                throw new UsernameNotFoundException("User not found");
            }
            String userid = username.split("@")[0];
            Constants.RESOURCE_OPCO_ID = resourceRepository.findByUserId(userid).get().getOpcoid();
            return new org.springframework.security.core.userdetails.User(user.get().getUsername(), "", getAuthorities(user.get()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new UsernameNotFoundException("User not found");
        }
    }

    /**
     * This method would be use for getting the Authorities to the GDPUser.
     * @param user is the GDP User.
     * @return this method would be return authorities.
     */
    private Set<GrantedAuthority> getAuthorities(GDPUser user) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(ROLE_PREFIX+(user.getRoles().getRole()));
        authorities.add(grantedAuthority);
        return authorities;
    }

    public String getUserOpcoId(){
        String userid = CurrentUsernameUtil.getCurrentUsername().split("@")[0];
        return resourceRepository.findByUserId(userid).get().getOpcoid();
    }
}
