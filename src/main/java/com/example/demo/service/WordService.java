package com.example.demo.service;

import com.example.demo.dto.WordDto;
import com.example.demo.entity.Word;

import java.util.List;

public interface WordService {
    public WordDto addWord(Word word);
    public WordDto getWordById(Integer id);
    public List<WordDto> getWordByLessonId(Integer lessonId);
    public List<WordDto> addListWord(List<Word> wordList);
    public WordDto updateWord(Word word);
}
