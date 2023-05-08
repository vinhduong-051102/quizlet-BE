package com.example.demo.repository;

import com.example.demo.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WordRepository extends JpaRepository<Word, Integer> {
    @Query("from Word w where w.lessonId=:id")
    List<Word> getWordByLessonId(@Param("id") Integer lessonId);
}
