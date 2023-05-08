package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Random;


@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "\"user\"")
public class User {

  public User(String userName, String password) {
    int random = new Random().nextInt();
    this.id = random > 0 ? random : -random;
    this.userName = userName;
    this.password = password;
  }

  @Id
  @Column(name = "id")
  private Integer id;
  @Column(name = "username")
  private String userName;
  @Column(name = "password")
  private String password;

}
