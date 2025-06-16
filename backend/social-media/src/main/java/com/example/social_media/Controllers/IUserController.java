package com.example.social_media.Controllers;

import com.example.social_media.Dto.UserDto;
import com.example.social_media.Entities.User;

import java.util.List;
import java.util.Map;

public interface IUserController {
    public List<UserDto> getAllUsers();
    public UserDto findUserById(Long id);
    public UserDto addUser(UserDto userDto);
    public UserDto updateUser(Long id, UserDto userDto);
    public UserDto patchUser(Long id, Map<String, Object> updates);
    public void deleteUser(Long id);
}
