package com.example.demo.controller;

import com.example.demo.dto.LessonDto;
import com.example.demo.dto.ResponseDto;
import com.example.demo.entity.Lesson;
import com.example.demo.entity.Word;
import com.example.demo.service.LessonService;
import com.example.demo.service.WordService;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;


@RestController
@RequestMapping("/api/v1/lesson")
public class LessonController {
  @Data
  public static class CreateLessonRequest {
    private Lesson lesson;
    private List<Word> wordList;
  }
  @Autowired
  private LessonService lessonService;
  @Autowired
  private WordService wordService;

  @GetMapping("/")
  public ResponseEntity<ResponseDto<List<LessonDto>>> getLesson(@PathParam("userId") Integer userId,
                                                                @PathParam("name") String name) {
    List<LessonDto> lessonDtoList = lessonService.getLesson(userId, name);
    return new ResponseEntity<>(new ResponseDto<>(HttpStatus.OK.value(), "Lấy thông tin học phần thành công", lessonDtoList)
            , HttpStatus.OK);
  }
  @GetMapping("/{id}")
  public ResponseEntity<ResponseDto<LessonDto>> getLesson(@PathVariable("id") Integer id) {
    LessonDto lessonDto = lessonService.getLessonById(id);
    if(lessonDto != null) {
      return new ResponseEntity<>(new ResponseDto<>(HttpStatus.OK.value(), "Lấy thông tin học phần thành công", lessonDto)
              , HttpStatus.OK);
    }
    return new ResponseEntity<>(new ResponseDto<>(HttpStatus.BAD_REQUEST.value(), "Lấy thông tin học phần thất bại", lessonDto)
            , HttpStatus.BAD_REQUEST);
  }
  @PostMapping("/create")
  public ResponseEntity<ResponseDto<LessonDto>> createLesson(@RequestBody @NotNull CreateLessonRequest request) {
    LessonDto lessonDto = lessonService.createLesson(request.lesson.getUserId(), request.lesson.getName(), request.lesson.getDescription(),
            request.lesson.getAmount());
    Integer lessonId = lessonDto.getId();
    for (Word w :
            request.wordList) {
      w.setLessonId(lessonId);
    }
    wordService.addListWord(request.wordList);
    return new ResponseEntity<>(new ResponseDto<>(HttpStatus.OK.value(), "Tạo học phần mới thành công", lessonDto), HttpStatus.OK);
  }
}
