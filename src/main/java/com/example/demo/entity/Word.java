package com.example.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Random;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "\"word\"")
public class Word {
    public Word(Integer lessonId, String vocabulary, String meaning, UUID uuid) {
        int random = new Random().nextInt();
        this.id = random > 0 ? random : -random;
        this.lessonId = lessonId;
        this.vocabulary = vocabulary;
        this.meaning = meaning;
        this.uuid = uuid;
        this.star = false;
    }
    @Id
    private Integer id;
    private UUID uuid;
    @Column
    private Integer lessonId;
    @Column
    private String vocabulary;
    @Column
    private String meaning;
    @Column Boolean star;
}
