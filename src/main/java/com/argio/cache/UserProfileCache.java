package com.argio.cache;

import com.argio.dto.user.UserResponseDto;

public class UserProfileCache {

    private UserResponseDto user;

    public UserResponseDto getUser() {
        return user;
    }

    public void setUser(UserResponseDto user){
        this.user = user;
    }
}