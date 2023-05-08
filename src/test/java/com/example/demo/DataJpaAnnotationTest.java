package com.example.demo;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.exception.DuplicateUserNameException;
import com.example.demo.exception.TooShortUserNameException;
import com.example.demo.repository.UserRepository;
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

    @After
    public void clean() {
        userRepository.deleteAll();
    }

}
