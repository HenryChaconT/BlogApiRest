package com.udemy.service;

import com.udemy.payload.LoginDto;
import com.udemy.payload.RegisterDto;

public interface AuthService {

    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
