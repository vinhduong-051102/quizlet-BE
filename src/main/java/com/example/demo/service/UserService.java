package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;

import java.util.List;

public interface UserService {
  public UserDto createUser(User user);
  public UserDto getUserByUserNameAndPassword(User user);
  public List<UserDto> getAllUser();
}
