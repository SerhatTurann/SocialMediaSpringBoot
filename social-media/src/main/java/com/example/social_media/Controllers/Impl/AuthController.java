package com.example.social_media.Controllers.Impl;

import com.example.social_media.Dto.AuthResponseDto;
import com.example.social_media.Dto.UserDto;
import com.example.social_media.Dto.UserLoginDto;
import com.example.social_media.Entities.User;
import com.example.social_media.Security.JwtTokenProvider;
import com.example.social_media.Services.Impl.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {


    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody UserLoginDto userLoginDto){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userLoginDto.getUserName(),userLoginDto.getPassword());
        Authentication auth = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = "Bearer " + jwtTokenProvider.generateJwtToken(auth);
        User user = userService.findUserByUsername(userLoginDto.getUserName());
        return new AuthResponseDto(jwtToken,user.getUserName(),user.getId());
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserLoginDto userLoginDto){
        if (userService.findUserByUsername(userLoginDto.getUserName())!=null){
            return new ResponseEntity<>("Kullanıcı adı zaten var.", HttpStatus.BAD_REQUEST);
        }

        UserDto user = new UserDto();
        user.setUserName(userLoginDto.getUserName());
        user.setPassword(passwordEncoder.encode(userLoginDto.getPassword()));
        userService.addUser(user);
        return new ResponseEntity<>("Başarıyla kaydedildi.",HttpStatus.CREATED);
    }
}
