package com.example.demo.service;

import com.example.demo.dto.LessonDto;

import java.util.List;

public interface LessonService {
  List<LessonDto> getLesson(Integer userId, String name);

  LessonDto createLesson(Integer userId, String name, String description, Integer amount);

  LessonDto getLessonById(Integer id);

  LessonDto deleteLesson(Integer id);
}
