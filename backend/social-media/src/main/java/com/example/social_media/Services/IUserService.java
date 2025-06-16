package com.example.social_media.Services;

import com.example.social_media.Dto.UserDto;
import com.example.social_media.Entities.User;

import java.util.List;
import java.util.Map;

public interface IUserService {
    public List<UserDto> getAllUsers();
    public UserDto findUserById(Long id);
    public UserDto addUser(UserDto userDto);
    public UserDto updateUser(Long id,UserDto userDto);
    public UserDto patchUser(Long id, Map<String, Object> updates);
    public void deleteUser(Long id);
    public User findUserByUsername(String username);
}
