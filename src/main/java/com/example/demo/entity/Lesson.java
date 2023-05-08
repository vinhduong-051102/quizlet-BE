package com.example.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Random;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "\"lesson\"")
public class Lesson {
    public Lesson(Integer userId, String name, String description, Integer amount) {
        int random = new Random().nextInt();
        this.id = random > 0 ? random : -random;
        this.userId = userId;
        this.name = name;
        this.amount = amount;
        this.description = description;
    }
    @Id
    private Integer id;
    @Column
    private Integer userId;
    @Column
    private String name;
    @Column
    private Integer amount;
    @Column String description;
}
