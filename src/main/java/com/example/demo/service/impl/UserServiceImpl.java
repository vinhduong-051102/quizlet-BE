package com.example.demo.service.impl;

import com.example.demo.exception.DuplicateUserNameException;
import com.example.demo.exception.TooShortUserNameException;
import com.example.demo.repository.UserRepository;
import org.modelmapper.ModelMapper;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements com.example.demo.service.UserService {
  @Autowired
  private ModelMapper modelMapper;
  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDto createUser(User user) {
    List<User> userList = userRepository.findAll();
    Optional<User> userIsSimilar =
            userList.stream().parallel().filter(u -> Objects.equals(u.getUserName(), user.getUserName())).findAny();
    if(userIsSimilar.isPresent()) {
      throw new DuplicateUserNameException("User with username " + user.getUserName() + " already exists");
    }

    if(user.getUserName().length() < 5) {
      throw new TooShortUserNameException("User name have to at least 5 " +
              "letters");
    }

    UserDto userDto = modelMapper.map(user, UserDto.class);
    userRepository.save(user);
    return userDto;
  }

  @Override
  public UserDto getUserByUserNameAndPassword(User user) {
    User userFromDB = userRepository.findByUserNameAndPassword(user.getUserName(), user.getPassword());
    if(userFromDB != null) {
      return modelMapper.map(userFromDB, UserDto.class);
    }
    return null;
  }
  @Override
  public List<UserDto> getAllUser() {
    List<User> listUser = userRepository.findAll();
    if(listUser.size() > 0) {
      List<UserDto> listUserDto = new ArrayList<>();
      for (User user :
           listUser) {
        listUserDto.add(modelMapper.map(user, UserDto.class));
      }
      return listUserDto;
    }
    return null;
  }
}
