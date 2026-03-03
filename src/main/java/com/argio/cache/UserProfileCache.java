package com.argio.cache;

import com.argio.dto.user.UserResponseDto;

/**
 * A class representing a cache for storing and retrieving user profile data.
 *
 * This class holds an instance of {@link UserResponseDto} to store
 * details about the currently cached user. It provides getter and setter
 * methods to access and update the cached user data.
 */
public class UserProfileCache {

    private UserResponseDto user;

    public UserResponseDto getUser() {
        return user;
    }

    public void setUser(UserResponseDto user){
        this.user = user;
    }
}