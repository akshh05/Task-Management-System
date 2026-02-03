package com.example.taskmanagementsystem.service;

import com.example.taskmanagementsystem.enums.Role;
import com.example.taskmanagementsystem.model.User;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {

    @BeforeAll
    void setUpAll(){
        System.out.println("----------Starting the test for Task Management System-------------");
    }

    @BeforeEach
    void setUp(){
        System.out.println("Testing...");
    }

    @AfterEach
    void tearDown(){
        System.out.println("Cleaning...");
    }

    @AfterAll
    void tearDownAll(){
        System.out.println("--------------------------------------------------------------------");
    }

    // Unit Test
    @Test
    void shouldCreateUserObjectSuccessfully(){
        User user = new User();
        user.setName("Intern");
        user.setRole(Role.DEVELOPER);
        assertEquals("Intern", user.getName());
        assertEquals(Role.DEVELOPER, user.getRole());
    }

    // Stub
    interface SimpleUserRepository {
        Optional<User> findById(Long id);
    }

    static class StubUserRepository implements SimpleUserRepository {
        public Optional<User> findById(Long id) {
            User user = new User();
            user.setId(id);
            user.setName("Stub User");
            return Optional.of(user);
        }
    }

    // Fake
    interface FakeUserRepository {
        User save(User user);
        Optional<User> findById(Long id);
    }

    static class InMemoryUserRepository implements FakeUserRepository {

        private final Map<Long, User> store = new HashMap<>();
        private long idCounter = 1;

        public User save(User user) {
            user.setId(idCounter++);
            store.put(user.getId(), user);
            return user;
        }

        public Optional<User> findById(Long id) {
            return Optional.ofNullable(store.get(id));
        }
    }

    static class UserService {

        private final FakeUserRepository repository;

        public UserService(FakeUserRepository repository) {
            this.repository = repository;
        }

        public User createUser(User user) {
            return repository.save(user);
        }

        public User getUserById(Long id) {
            return repository.findById(id).orElse(null);
        }
    }

    @Test
    void shouldReturnUserUsingStub(){
        SimpleUserRepository stubRepo = new StubUserRepository();
        User user = stubRepo.findById(1L).orElse(null);
        assertNotNull(user);
        assertEquals("Stub User", user.getName());
    }

    @Test
    void shouldSaveAndFetchUserUsingFakeRepository(){
        FakeUserRepository fakeRepo = new InMemoryUserRepository();
        UserService userService = new UserService(fakeRepo);

        User user = new User();
        user.setName("Fake User");
        user.setRole(Role.DEVELOPER);

        User saved = userService.createUser(user);
        User fetched = userService.getUserById(saved.getId());

        assertNotNull(saved.getId());
        assertEquals("Fake User", fetched.getName());
        assertEquals(Role.DEVELOPER, fetched.getRole());
    }
}
