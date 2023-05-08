package com.example.demo.controller;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
  @Autowired
  private UserService userService;

  @PostMapping("/login")
  public ResponseEntity<ResponseDto<UserDto>> login(@RequestBody User user) {
    UserDto userDto = userService.getUserByUserNameAndPassword(user);
    if (userDto != null) {
      ResponseDto<UserDto> responseDto = new ResponseDto<>(HttpStatus.OK.value(), "Đăng nhập thành công", userDto);
      return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    return new ResponseEntity<>(new ResponseDto<>(HttpStatus.NOT_FOUND.value(), "Đăng nhập thất bại, vui lòng kiểm tra lại thông tin đăng nhập", null), HttpStatus.NOT_FOUND);
  }
  @PostMapping("/register")
  public ResponseEntity<ResponseDto<UserDto>> register(@RequestBody @NotNull User u) {
    List<UserDto> listUserDto = userService.getAllUser();
    if (listUserDto != null) {
      if (listUserDto.size() > 0) {
        for (UserDto userDtoInList :
                listUserDto) {
          if (userDtoInList.getUserName().equals(u.getUserName())) {
            return new ResponseEntity<>(new ResponseDto<>(HttpStatus.BAD_REQUEST.value(), "Tên đăng kí đã tồn tại", null), HttpStatus.BAD_REQUEST);
          }
        }
      }
    }
    User user = new User(u.getUserName(), u.getPassword());
    UserDto userDto = userService.createUser(user);
    return new ResponseEntity<>(new ResponseDto<>(HttpStatus.OK.value(), "Đăng kí thành công", userDto), HttpStatus.OK);
  }
  @GetMapping("")
  public ResponseEntity<ResponseDto<List<UserDto>>> getAllUser() {
    List<UserDto> listUserDto = userService.getAllUser();
    if (listUserDto != null) {
      if (listUserDto.size() > 0) {
        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.OK.value(), "Lấy thông tin thành công", listUserDto), HttpStatus.OK);
      }
    }
    return new ResponseEntity<>(new ResponseDto<>(HttpStatus.NO_CONTENT.value(), "Lấy thông tin thành công", null), HttpStatus.NO_CONTENT);

  }
}
