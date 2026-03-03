package com.argio.assembler;

import com.argio.dto.user.UserResponseDto;
import com.argio.entity.User;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserAssembler {

    public UserResponseDto toDto(User user) {
        if(user == null)
            return null;

        return new UserResponseDto(
            user.getId(),
            user.getEmail(),
            user.getRole().name(),
            user.getCreatedAt()
        );
    }
}