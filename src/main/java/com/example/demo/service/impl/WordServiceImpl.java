package com.example.demo.service.impl;

import com.example.demo.dto.WordDto;
import com.example.demo.entity.Word;
import com.example.demo.exception.InvalidWordException;
import com.example.demo.exception.LessonIsNotExistsException;
import com.example.demo.exception.TooLongVocabularyException;
import com.example.demo.repository.LessonRepository;
import com.example.demo.repository.WordRepository;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WordServiceImpl implements com.example.demo.service.WordService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private LessonRepository lessonRepository;
    @Override
    public WordDto addWord(@NotNull Word w) {
        if(w.getVocabulary().isBlank() || w.getMeaning().isBlank()) {
            throw new InvalidWordException("Vocabulary and meaning at a word " +
                    "must have value");
        }
        if(!lessonRepository.existsById(w.getLessonId())) {
            throw new LessonIsNotExistsException("Lesson is not exist, please" +
                    " check carefully lessonId");
        }
        if(w.getVocabulary().length() > 225) {
            throw new TooLongVocabularyException("The vocabulary is too long," +
                    " please limit the letter less than 255");
        }
        Word word = new Word(w.getLessonId(), w.getVocabulary(), w.getMeaning(), w.getUuid());
        wordRepository.save(word);
        return modelMapper.map(word, WordDto.class);
    }

    @Override
    public WordDto getWordById(Integer id) {
        Optional<Word> word = wordRepository.findById(id);
        if(!word.isPresent()) {
            return modelMapper.map(word, WordDto.class);
        }
        return null;
    }

    @Override
    public List<WordDto> getWordByLessonId(Integer lessonId) {
        List<Word> wordList = wordRepository.getWordByLessonId(lessonId);
        List<WordDto> wordDtoList = new ArrayList<>();
        if(wordList != null) {
                for (Word w :
                        wordList) {
                    wordDtoList.add(modelMapper.map(w, WordDto.class));
                }
                return wordDtoList;
        }
        if(!lessonRepository.existsById(lessonId)) {
            throw new LessonIsNotExistsException("Lesson is not exist, please" +
                    " check carefully lessonId");
        }
        return null;
    }

    @Override
    public List<WordDto> addListWord(@NotNull List<Word> wordList) {
        List<WordDto> wordDtoList = new ArrayList<>();
        for (Word w :
                wordList) {
            wordDtoList.add(addWord(w));
        }
        return wordDtoList;
    }

    @Override
    public WordDto updateWord(@NotNull Word w) {
        Optional<Word> word = wordRepository.findById(w.getId());

        if(word.isPresent()) {
            Word updateWord = word.get();
            updateWord.setStar(w.getStar());
            updateWord.setMeaning(w.getMeaning());
            updateWord.setVocabulary(w.getVocabulary());
            wordRepository.save(updateWord);
            return modelMapper.map(w, WordDto.class);
        }
        return null;
    }
}
