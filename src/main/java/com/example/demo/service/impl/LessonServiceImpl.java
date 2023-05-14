package com.example.demo.service.impl;

import com.example.demo.dto.LessonDto;
import com.example.demo.entity.Lesson;
import com.example.demo.exception.LessonIsEmptyException;
import com.example.demo.exception.LessonIsNotExistsException;
import com.example.demo.exception.UserIsNotExistsException;
import com.example.demo.repository.LessonRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LessonServiceImpl implements com.example.demo.service.LessonService {
  @Autowired
  private LessonRepository lessonRepository;
  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private UserRepository userRepository;

  @Override
  public LessonDto createLesson(Integer userId, String name, String description, Integer amount) {
    Lesson lesson = new Lesson(userId, name, description, amount);
    if(userRepository.findById(userId).isEmpty()) {
      throw new UserIsNotExistsException("User is not exist, please check " +
              "carefully userId");
    }
    LessonDto lessonDto = modelMapper.map(lesson, LessonDto.class);
    lessonRepository.save(lesson);
    return lessonDto;
  }

  @Override
  public LessonDto getLessonById(Integer id) {
    Optional<Lesson> lesson = lessonRepository.findById(id);
    if(lesson.isEmpty()) {
      throw new LessonIsNotExistsException("Lesson is not exist, please" +
              " check carefully lessonId");
    }
    return modelMapper.map(lesson, LessonDto.class);
  }

  @Override
  public LessonDto deleteLesson(Integer id) {
    Optional<Lesson> lesson = lessonRepository.findById(id);
    if (lessonRepository.findById(id).isPresent()) {
      lessonRepository.deleteById(id);
      return modelMapper.map(lesson, LessonDto.class);
    }
    return null;
  }

  @Override
  public List<LessonDto> getLesson(Integer userId, @NotNull String name) {
    List<Lesson> listLesson;
    List<LessonDto> listLessonDto = new ArrayList<>();
    if (name.isEmpty()) {
      listLesson = lessonRepository.getLessonByUserId(userId);
      if (listLesson != null) {
        for (Lesson l :
                listLesson) {
          listLessonDto.add(modelMapper.map(l, LessonDto.class));
        }
        return listLessonDto;
      }
    } else {
      listLesson = lessonRepository.getLessonByName(name, userId);
      if (listLesson != null) {
        for (Lesson l :
                listLesson) {
          listLessonDto.add(modelMapper.map(l, LessonDto.class));
        }
        return listLessonDto;
      }
    }
    return new ArrayList<>();
  }

}
