package com.example.demo;
import com.example.demo.dto.LessonDto;
import com.example.demo.dto.UserDto;
import com.example.demo.dto.WordDto;
import com.example.demo.entity.User;
import com.example.demo.entity.Word;
import com.example.demo.exception.*;
import com.example.demo.repository.LessonRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WordRepository;
import com.example.demo.service.impl.LessonServiceImpl;
import com.example.demo.service.impl.UserServiceImpl;
import com.example.demo.service.impl.WordServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan(basePackages = {"com.example.demo"})
public class DataJpaAnnotationTest {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private LessonServiceImpl lessonService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private WordServiceImpl wordService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WordRepository wordRepository;
    @Autowired
    private LessonRepository lessonRepository;

    @Test
    public void allComponentAreNotNull() {
        Assertions.assertThat(dataSource).isNotNull();
        Assertions.assertThat(jdbcTemplate).isNotNull();
        Assertions.assertThat(entityManager).isNotNull();
        Assertions.assertThat(lessonService).isNotNull();
        Assertions.assertThat(userService).isNotNull();
        Assertions.assertThat(wordService).isNotNull();
    }

    @Test
    // ID 1: Đăng kí người dùng mới hợp lệ
    public void id1() {
        User mockUser = new User("mockUserName", "mockPassword");
        UserDto u = userService.createUser(mockUser);
        Assertions.assertThat(mockUser.getId()).isEqualTo(u.getId());
    }

    @Test
    // ID 2: Đăng kí người dùng mới với "username" đã tồn tại trong hệ thống
    public void id2() {
        String mockUserName = "mockUserName";
        try {
            User mockUser1 = new User(mockUserName, "mockPassword");
            userService.createUser(mockUser1);
            User mockUser2 = new User(mockUserName, "mockPassword");
            userService.createUser(mockUser2);
            fail("Expected DuplicateUserNameException but no exception was thrown.");
        } catch (DuplicateUserNameException exception) {
            assertEquals("User with username " + mockUserName + " already " +
                            "exists",
                    exception.getMessage());
        }
    }

    @Test
    // ID 3: Đăng kí thông tin người dùng mới với dữ liệu không hợp lệ ( username ít hơn 5 kí tự )
    public void id3() {
        try {
            User mockUser = new User("mock", "mockPassword");
            userService.createUser(mockUser);
            fail("Expected TooShortUserNameException but no exception was thrown.");
        } catch (TooShortUserNameException ex) {
            assertEquals("User name have to at least 5 letters",
                    ex.getMessage());
        }
    }

    @Test
    // ID 4: Đăng kí với password là chuỗi rỗng
    public void id4() {
        try {
            User mockUser = new User("mockUser", "");
            userService.createUser(mockUser);
            fail("Expected UserNameOrPasswordIsEmptyException but no exception was thrown.");
        } catch (UserNameOrPasswordIsEmptyException ex) {
            assertEquals("User name and password must have value",
                    ex.getMessage());
        }
    }

    @Test
    // ID 5: Đăng nhập hợp lệ
    public void id5() {
        User mockUser = new User("mockUserName", "mockPassword");
        userService.createUser(mockUser);
        UserDto userLogin = userService.getUserByUserNameAndPassword(mockUser);
        Assertions.assertThat(userLogin.getId()).isEqualTo(mockUser.getId());
    }

    @Test
    // ID 6: Đăng nhập người dùng sai thông tin ( sai username hoặc password )
    public void id6() {
        try {
            User mockUser = new User("mockUserName", "mockPassword");
            userService.createUser(mockUser);
            userService.getUserByUserNameAndPassword(new User("mockk",
                            "mockPassword"));
            fail("Expected InvalidUserException but no exception was thrown.");

        } catch (InvalidUserException ex) {
            assertEquals("User name or password is wrong", ex.getMessage());
        }
    }

    @Test
    // ID 7: Đăng nhập với thông tin username và password là chuỗi rỗng
    public void id7() {
        try {
            userService.getUserByUserNameAndPassword(new User("",
                    ""));
            fail("Expected UserNameOrPasswordIsEmptyException but no exception was thrown.");
        } catch (UserNameOrPasswordIsEmptyException ex) {
            assertEquals("User name and password must have value",
                    ex.getMessage());
        }
    }

    @Test
    // ID 8: Tạo thông tin học phần mới hợp lệ
    public void id8() {
        User mockUser = new User("mockUserName", "mockPassword");
        UserDto userDto = userService.createUser(mockUser);
        LessonDto lessonDto = lessonService.createLesson(userDto.getId(), "unit 1", "This " +
                "lesson will teach you how can test a database", 1);
        Assertions.assertThat(lessonDto.getDescription()).isEqualTo("This " +
                "lesson " +
                "will teach you how can test a database");
    }

    @Test
    // ID 9: Tạo thông tin học phần mới với "userId" không tồn tại
    public void id9() {
        try {
            lessonService.createLesson(123, "unit 1", "This" +
                    " " +
                    "lesson will teach you how can test a database", 1);
            fail("Expected UserIsNotExistsException but no exception was thrown.");
        } catch (UserIsNotExistsException ex) {
            assertEquals("User is not exist, please check carefully userId",
                    ex.getMessage());
        }
    }

    @Test
    // ID 10: Tạo từ vựng mới hợp lệ
    public void id10() {
        User mockUser = new User("mockUserName", "mockPassword");
        UserDto userDto = userService.createUser(mockUser);
        LessonDto lessonDto = lessonService.createLesson(userDto.getId(), "unit 1", "This " +
                "lesson will teach you how can test a database", 1);
        Word mockWord = new Word(lessonDto.getId(), "blackbox testing", "Kiểm" +
                " thử hộp đen", UUID.randomUUID());
        WordDto wordDto = wordService.addWord(mockWord);
        Assertions.assertThat(wordDto.getUuid()).isEqualTo(mockWord.getUuid());
    }

    @Test
    // ID 11: Tạo từ vựng mới không có vocabulary hoặc meaning
    public void id11() {
        try {
            User mockUser = new User("mockUserName", "mockPassword");
            UserDto userDto = userService.createUser(mockUser);
            LessonDto lessonDto = lessonService.createLesson(userDto.getId(), "unit 1", "This " +
                    "lesson will teach you how can test a database", 1);
            Word mockWord = new Word(lessonDto.getId(), "", "Kiểm" +
                    " thử hộp đen", UUID.randomUUID());
            wordService.addWord(mockWord);
            fail("Expected InvalidWordException but no exception was thrown.");

        } catch (InvalidWordException ex) {
            assertEquals("Vocabulary and meaning at a word " +
                    "must have value", ex.getMessage());
        }
    }

    @Test
    // ID 12: Tạo từ vựng mới của học phần không tồn tại
    public void id12() {
        try {

            Word mockWord = new Word(123, "blackbox testing", "Kiểm" +
                    " thử hộp đen", UUID.randomUUID());
            wordService.addWord(mockWord);
            fail("Expected LessonIsNotExistsException but no exception was thrown.");

        } catch (LessonIsNotExistsException ex) {
            assertEquals("Lesson is not exist, please" +
                    " check carefully lessonId", ex.getMessage());
        }
    }

    @Test
    // ID 13: Tạo từ vựng mới có vocabulary dài hơn 225 ký tự
    public void id13() {
        try {
            String longString = "The long text .............................." +
                    "..................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................";
            User mockUser = new User("mockUserName", "mockPassword");
            UserDto userDto = userService.createUser(mockUser);
            LessonDto lessonDto = lessonService.createLesson(userDto.getId(), "unit 1", "This " +
                    "lesson will teach you how can test a database", 1);
            Word mockWord = new Word(lessonDto.getId(), longString, "Kiểm" +
                    " thử hộp đen", UUID.randomUUID());
            wordService.addWord(mockWord);
            fail("Expected TooLongVocabularyException but no exception was thrown.");

        } catch (TooLongVocabularyException ex) {
            assertEquals("The vocabulary is too long," +
                    " please limit the letter less than 255", ex.getMessage());
        }
    }

    @Test
    // ID 14: Tìm kiếm thông tin của học phần hợp lệ
    public void id14() {
        User mockUser = new User("mockUserName", "mockPassword");
        UserDto userDto = userService.createUser(mockUser);
        LessonDto lessonDto1 = lessonService.createLesson(userDto.getId(),
                "unit 1", "This " +
                "lesson will teach you how can test a database", 1);
        LessonDto lessonDto2 = lessonService.getLessonById(lessonDto1.getId());
        Assertions.assertThat(lessonDto1.getId()).isEqualTo(lessonDto2.getId());
    }

    @Test
    // ID 15: Tìm kiếm thông tin của học phần không tồn tại
    public void id15() {
        try {
            lessonService.getLessonById(123);
            fail("Expected LessonIsNotExistsException but no exception was thrown.");

        } catch (LessonIsNotExistsException ex) {
            assertEquals("Lesson is not exist, please" +
                    " check carefully lessonId", ex.getMessage());
        }
    }

    @Test
    // ID 16: Lấy thông tin học phần dựa vào userId hợp lệ
    public void id16() {
        User mockUser = new User("mockUserName", "mockPassword");
        UserDto userDto = userService.createUser(mockUser);
        LessonDto lessonDto = lessonService.createLesson(userDto.getId(), "unit 1", "This " +
                "lesson will teach you how can test a database", 1);
        List<LessonDto> lessonDtos = lessonService.getLesson(userDto.getId(),
                "");
        Assertions.assertThat(lessonDtos.get(0).getId()).isEqualTo(lessonDto.getId());
    }

    @Test
    // ID 17: Lấy thông tin học phần dựa vào userId chưa tạo bất kỳ học phần nào trước đây
    public void id17() {
        User mockUser = new User("mockUserName", "mockPassword");
        UserDto userDto = userService.createUser(mockUser);
        List<LessonDto> lessonDtos = lessonService.getLesson(userDto.getId(),
                "");
        Assertions.assertThat(lessonDtos.size()).isEqualTo(0);
    }

    @Test
    // ID 18: Lấy thông học phần dựa vào userId không tồn tại
    public void id18() {
        List<LessonDto> lessonDtos = lessonService.getLesson(123,
                "");
        Assertions.assertThat(lessonDtos).isEqualTo(null);
    }

    @After
    public void clean() {
        userRepository.deleteAll();
        lessonRepository.deleteAll();
        wordRepository.deleteAll();
    }

}
