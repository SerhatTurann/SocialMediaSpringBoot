package com.example.social_media.Services.Impl;

import com.example.social_media.Dto.UserDto;
import com.example.social_media.Entities.User;
import com.example.social_media.Repositories.UserRepository;
import com.example.social_media.Services.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDto> getAllUsers() {
        List<UserDto> userDtos = new ArrayList<>();
        for (User user: userRepository.findAll()){
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user,userDto);
            userDtos.add(userDto);
        }
        return userDtos;
    }

    @Override
    public UserDto findUserById(Long id) {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()){
            UserDto userDto=new UserDto();
            BeanUtils.copyProperties(optional.get(),userDto);
            return userDto;
        }
        return null;
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto,user);
        userRepository.save(user);
        return userDto;
    }

    @Override
    public UserDto updateUser(Long id,UserDto userDto) {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()){
            User _user = optional.get();
            BeanUtils.copyProperties(userDto,_user);
            userRepository.save(_user);
            return userDto;
        }
        return null;
    }

    @Override
    public UserDto patchUser(Long id, Map<String, Object> updates) {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()){
            updates.forEach((field, value) -> {
                switch (field) {
                    case "userName" -> optional.get().setUserName((String) value);
                    case "password" -> optional.get().setPassword((String) value);
                    case "bio" -> optional.get().setBio((String) value);
                    case "profilePhotoUrl" -> optional.get().setProfilePhotoUrl((String) value);
                    // Not: İzin verilen alanları burada açıkça belirlemek güvenlik açısından önemlidir.
                }
            });
            userRepository.save(optional.get());
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(optional.get(),userDto);
            return userDto;
        }
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User findUserByUsername(String username){
       return userRepository.findByUserName(username);

    }
}
