package com.example.demo.repository;

import com.example.demo.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {
    @Query("from Lesson l where l.name=:name and l.userId=:id")
    public List<Lesson> getLessonByName(@Param("name") String name, @Param("id") Integer id);
    @Query("from Lesson l where l.userId=:id")
    public List<Lesson> getLessonByUserId(@Param("id") Integer id);

}
