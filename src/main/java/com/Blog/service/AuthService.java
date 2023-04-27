package com.Blog.service;

import com.Blog.payload.LoginDto;
import com.Blog.payload.RegisterDto;

public interface AuthService {

    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
