package com.example.social_media.Controllers.Impl;

import com.example.social_media.Controllers.IUserController;
import com.example.social_media.Dto.UserDto;
import com.example.social_media.Entities.User;
import com.example.social_media.Services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController implements IUserController {

    @Autowired
    IUserService userService;

    @GetMapping("/")
    @Override
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "/{id}")
    @Override
    public UserDto findUserById(@PathVariable(name = "id") Long id) {
        return userService.findUserById(id);
    }

    @PostMapping(path = "/add")
    @Override
    public UserDto addUser(@RequestBody UserDto userDto) {
        return userService.addUser(userDto);
    }

    @PutMapping(path = "/update/{id}")
    @Override
    public UserDto updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        return userService.updateUser(id,userDto);
    }

    @PatchMapping("/update/patch/{id}")
    @Override
    public UserDto patchUser(@PathVariable Long id,@RequestBody Map<String, Object> updates) {
        return userService.patchUser(id,updates);
    }

    @DeleteMapping(path = "/delete/{id}")
    @Override
    public void deleteUser(@PathVariable(name = "id") Long id) {
        userService.deleteUser(id);
    }
}
