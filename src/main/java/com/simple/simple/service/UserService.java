package com.simple.simple.service;

import com.simple.simple.entity.UserEntity;

public interface UserService {

	public UserEntity getCurrentUser();

	public UserEntity findUserByEmail(String email);
	
    public void saveUser(UserEntity user);

    public void updateUser(UserEntity user);

}
