package com.example.demo.controller;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.WordDto;
import com.example.demo.entity.Word;
import com.example.demo.service.WordService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/word")
public class WordController {
    @Autowired
    private WordService wordService;
    @PutMapping("/update")
    public ResponseEntity<ResponseDto<WordDto>> updateWordById(@RequestBody Word word) {
        WordDto wordDto = wordService.updateWord(word);
        if(wordDto != null) {
            return new ResponseEntity<>(new ResponseDto<>(HttpStatus.OK.value(), "Cập nhập thuật ngữ thành công", wordDto), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.BAD_REQUEST.value(), "Cập nhật thuật ngữ thất bại", null), HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/addWord")
    public ResponseEntity<ResponseDto<WordDto>> addWord(@RequestBody Word word) {
        WordDto wordDto = wordService.addWord(word);
        if(wordDto != null) {
            return new ResponseEntity<>(new ResponseDto<>(HttpStatus.OK.value(), "Thêm từ thành công", wordDto), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.BAD_REQUEST.value(), "Thêm từ thất bại", null), HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/addListWord")
    public ResponseEntity<ResponseDto<List<WordDto>>> addListWord(@RequestBody @NotNull List<Word> wordList) {
        if(wordList.size() > 0) {
            List<WordDto> wordDtoList = wordService.addListWord(wordList);
            return new ResponseEntity<>(new ResponseDto<>(HttpStatus.OK.value(), "Thêm danh sách thành công", wordDtoList),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.BAD_REQUEST.value(), "Danh sách từ phải có dữ liệu",
                null),
                HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/{lessonId}")
    public ResponseEntity<ResponseDto<List<WordDto>>> getWordByLessonId(@PathVariable("lessonId") int lessonId) {
        List<WordDto> wordDtoList = wordService.getWordByLessonId(lessonId);
        if(wordDtoList != null) {
            return new ResponseEntity<>(new ResponseDto<>(HttpStatus.OK.value(), "Lấy thông tin các thuật ngữ thành công", wordDtoList), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.NOT_FOUND.value(), "Id học phần không tồn tại trong cơ sở dữ liệu", null), HttpStatus.NOT_FOUND);
    }
}
