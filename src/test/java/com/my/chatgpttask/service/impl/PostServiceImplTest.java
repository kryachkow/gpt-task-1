package com.my.chatgpttask.service.impl;

import com.my.chatgpttask.dto.PostManageDto;
import com.my.chatgpttask.dto.PostViewDto;
import com.my.chatgpttask.entity.Post;
import com.my.chatgpttask.entity.User;
import com.my.chatgpttask.exception.LikeAlreadyExistsException;
import com.my.chatgpttask.repository.PostRepository;
import com.my.chatgpttask.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DirtiesContext
class PostServiceImplTest {


    @Autowired
    private PostServiceImpl postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    private static User testUser;

    @Test
    @DisplayName("Get all posts")
    void getAllPosts() {
        //given
        generateBasicData();
        //when
        List<PostViewDto> allPosts = postService.getAllPosts();

        //then
        assertEquals(3, allPosts.size());

    }

    @Test
    @DisplayName("Get post by Id")
    void getPostById() {
        //given
        generateBasicData();
        Post post = postRepository.getPostsByAuthorId(testUser.getId()).get(0);

        //when
        PostViewDto dto = postService.getPostById(post.getId());

        //then
        assertEquals(post.getTitle(), dto.getTitle());
        assertEquals(post.getBody(), dto.getBody());
    }

    @Test
    @DisplayName("Get posts by user Id")
    void getPostsByUserId() {
        //given
        generateBasicData();
        Long userId = testUser.getId();

        //when
        List<PostViewDto> posts = postService.getPostsByUserId(userId);

        //then
        assertEquals(2, posts.size());
    }

    @Test
    @DisplayName("Create posts")
    void createPost() {
        //given
        generateBasicData();
        PostManageDto postManageDto = PostManageDto.builder()
                .authorId(testUser.getId())
                .title("Title4")
                .body("Body")
                .build();

        //when
        PostViewDto postViewDto = postService.createPost(postManageDto);

        //then
        assertEquals(postManageDto.getAuthorId(), postViewDto.getAuthorId());
        assertEquals(postManageDto.getTitle(), postViewDto.getTitle());
        assertEquals(postManageDto.getBody(), postViewDto.getBody());

    }

    @Test
    @DisplayName("Add like to post")
    void addUserLike() {
        //given
        generateBasicData();
        Post post = postRepository.getPostsByAuthorId(testUser.getId()).get(0);

        //when
        PostViewDto postViewDto = postService.addUserLike(testUser.getId(), post.getId());

        //then
        assertEquals(1, postViewDto.getLikedByIds().size());
        assertThrows(LikeAlreadyExistsException.class, () -> postService.addUserLike(testUser.getId(), post.getId()));
    }

    @Test
    @DisplayName("Get all posts liked by users")
    void getAllPostsLikedByUser() {
        //given
        generateBasicData();
        User user = userRepository.getUserById(testUser.getId());

        //when
        List<PostViewDto> list = postService.getAllPostsLikedByUser(user.getId());

        //then
        assertEquals(2, list.size());

    }


    public void generateBasicData() {
        if (testUser != null) {
            return;
        }
        User user = new User();
        user.setEmail("email@email.com");
        user.setUsername("user");
        user.setPassword("12345678");
        testUser = userRepository.save(user);

        Post post1 = new Post();
        Post post2 = new Post();

        post1.setTitle("Title1");
        post2.setTitle("Title2");

        post1.setBody("Body1");
        post2.setBody("Body2");

        post1.setAuthor(testUser);
        post2.setAuthor(testUser);

        post1.setCreatedAt(new Date());
        post2.setCreatedAt(new Date());
        post2.getLikedBy().add(testUser);
        testUser.getLikedPosts().add(post2);

        postRepository.save(post1);
        postRepository.save(post2);
        userRepository.save(testUser);


        User addtionalUser = new User();
        addtionalUser.setEmail("email1@email.com");
        addtionalUser.setUsername("user1");
        addtionalUser.setPassword("12345678");

        addtionalUser = userRepository.save(addtionalUser);

        Post post3 = new Post();
        post3.setTitle("Title3");
        post3.setBody("Body3");
        post3.setAuthor(addtionalUser);
        post3.setCreatedAt(new Date());
        postRepository.save(post3);

    }
}