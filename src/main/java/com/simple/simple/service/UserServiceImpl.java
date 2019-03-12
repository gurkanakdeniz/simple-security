package com.simple.simple.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.simple.simple.entity.RoleEntity;
import com.simple.simple.entity.UserEntity;
import com.simple.simple.repository.RoleRepository;
import com.simple.simple.repository.UserRepository;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
    private RoleRepository roleRepository;

	private UserDetails buildUserForAuthentication(UserEntity user, List<GrantedAuthority> authorities) {
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				user.isActive(), true, true, true, authorities);
	}

	@Override
	public UserEntity findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByEmail(userName);
		List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());

		return buildUserForAuthentication(user, authorities);
	}

	private List<GrantedAuthority> getUserAuthority(Set<RoleEntity> userRoles) {
		Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();

		for (RoleEntity role : userRoles) {
			roles.add(new SimpleGrantedAuthority(role.getRole()));
		}

		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>(roles);

		return grantedAuthorities;
	}
	
    @Override
    public void saveUser(UserEntity user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(false);

        RoleEntity userRole = roleRepository.findByRole("USER");
        
        if (userRole == null) {
            throw new ValidationException();
        } 

        user.setRoles(new HashSet<RoleEntity>(Arrays.asList(userRole)));
        userRepository.save(user);
    }
	
    @Override
    public void updateUser(UserEntity user) {
        userRepository.save(user);
    }

	@Override
	public UserEntity getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserEntity user = null;

		if (authentication != null) {
			user = findUserByEmail(authentication.getName());

			if ((user != null) && user.isActive()) {
				return user;
			}
		}

		user = new UserEntity();
		user.setId(0);

		return user;
	}

}
