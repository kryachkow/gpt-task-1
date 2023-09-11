package com.my.chatgpttask.service.impl;

import com.my.chatgpttask.dto.UserManageDto;
import com.my.chatgpttask.dto.UserViewDto;
import com.my.chatgpttask.entity.User;
import com.my.chatgpttask.exception.FollowingAlreadyExistsException;
import com.my.chatgpttask.exception.FollowingYourselfForbiddenException;
import com.my.chatgpttask.repository.UserRepository;
import com.my.chatgpttask.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DirtiesContext
class UserServiceImplTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    private static User testUser;
    private static User secondTestUser;

    @Test
    void getAllUsers() {
        //given
        generateBasicData();
        //when
        List<UserViewDto> allUsers = userService.getAllUsers();
        //then
        assertEquals(2, allUsers.size());
    }

    @Test
    void getUserById() {
        //given
        generateBasicData();
        //when
        UserViewDto userById = userService.getUserById(testUser.getId());
        //then
        assertEquals(testUser.getId(), userById.getId());
        assertEquals(testUser.getEmail(), userById.getEmail());
        assertEquals(testUser.getUsername(), userById.getUsername());
    }

    @Test
    void createUser() {
        //given
        UserManageDto newUser = UserManageDto.builder()
                .username("newUser")
                .email("newnail@example.com")
                .password("12345678")
                .build();
        //when
        UserViewDto user = userService.createUser(newUser);
        //then
        assertEquals(newUser.getEmail(), user.getEmail());
        assertEquals(newUser.getUsername(), user.getUsername());
    }

    @Test
    void getAllFollowersByUserId() {
        //given
        generateBasicData();
        //when
        List<UserViewDto> followers = userService.getAllFollowersByUserId(testUser.getId());
        //then
        assertEquals(1, followers.size());
    }

    @Test
    void getAllFollowingUsersByUserId() {
        //given
        generateBasicData();
        //when
        List<UserViewDto> followers2 = userService.getAllFollowingUsersByUserId(secondTestUser.getId());
        //then
        assertEquals(1, followers2.size());
    }

    @Test
    void followUser() {
        //given
        generateBasicData();
        //when
        UserViewDto userViewDto = userService.followUser(testUser.getId(), secondTestUser.getId());
        //then
        assertEquals(1, userViewDto.getFollowingsId().size());
        assertThrows(FollowingAlreadyExistsException.class, () -> userService.followUser(testUser.getId(), secondTestUser.getId()));
        assertThrows(FollowingYourselfForbiddenException.class, () -> userService.followUser(testUser.getId(), testUser.getId()));
    }


    public void generateBasicData() {
        if (testUser != null) {
            return;
        }
        User user = new User();
        user.setEmail("email4@email.com");
        user.setUsername("user4");
        user.setPassword("12345678");
        testUser = userRepository.save(user);

        User newUser = new User();
        newUser.setEmail("email6@email.com");
        newUser.setUsername("user6");
        newUser.setPassword("12345678");
        testUser.getFollowers().add(newUser);
        newUser.getFollowing().add(testUser);
        secondTestUser = userRepository.save(newUser);
        userRepository.save(testUser);


    }


}