import dev.annyni.dto.PostDto;
import dev.annyni.mapper.MapperManager;
import dev.annyni.model.Post;
import dev.annyni.model.Status;
import dev.annyni.repository.PostRepository;
import dev.annyni.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * todo Document type PostServiceTest
 */
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    private PostService postService;

    private Post testPost;

    private MapperManager manager;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);

        postService = new PostService(postRepository, manager);

        testPost = Post.builder()
            .id(1L)
            .content("test content")
            .created(new Date())
            .updated(new Date())
            .status(Status.ACTIVE)
            .build();
    }

    @Test
    void findByIdTest(){
        Mockito.when(postRepository.findById(anyLong())).thenReturn(Optional.ofNullable(testPost));

        Optional<PostDto> postDto = postService.getByIdPost(1L);
        PostDto post = postDto.get();

        assertNotNull(post);
        assertEquals("test content", post.content());
        assertEquals(Status.ACTIVE, post.status());
    }

    @Test
    void findAllTest(){
        List<Post> posts = new ArrayList<>();
        posts.add(testPost);

        Mockito.when(postRepository.findAll()).thenReturn(posts);

        List<PostDto> postList = postService.getAllPosts();

        assertNotNull(postList);
        assertEquals(1, postList.size());
        assertEquals("test content", postList.get(0).content());
        assertEquals(Status.ACTIVE, postList.get(0).status());
    }

    @Test
    void createTest(){
        Mockito.when(postRepository.save(any(Post.class))).thenReturn(testPost);

        Long postId = postService.createPost(manager.mapPostToDto(testPost));
        Optional<PostDto> postDto = postService.getByIdPost(postId);
        PostDto post = postDto.get();

        assertNotNull(post);
        assertEquals("Test content", post.content());
        assertEquals(Status.ACTIVE, post.status());

        verify(postRepository, times(1)).save(testPost);
    }

    @Test
    void updateTest(){
        Post updatePost = Post.builder()
            .id(1L)
            .content("update content")
            .updated(new Date())
            .status(Status.ACTIVE)
            .build();

        Mockito.when(postRepository.update(any(Post.class))).thenReturn(updatePost);

        Long postId = postService.createPost(manager.mapPostToDto(updatePost));
        Optional<PostDto> postDto = postService.getByIdPost(postId);
        PostDto post = postDto.get();

        assertNotNull(post);
        assertEquals("update content", post.content());
        assertEquals(Status.ACTIVE, post.status());

        verify(postRepository, times(1)).update(updatePost);
    }

    @Test
    void deleteTest(){
        boolean result = postService.deletePost(1L);

        assertTrue(result);
        verify(postRepository, times(1)).delete(1L);
    }
}
