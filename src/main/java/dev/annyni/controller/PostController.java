package dev.annyni.controller;

import dev.annyni.dto.PostDto;
import dev.annyni.service.PostService;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;

/**
 * todo Document type PostController
 */
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    public Optional<PostDto> getById(Long id){
        return postService.getByIdPost(id);
    }

    public List<PostDto> getAll(){
        return postService.getAllPosts();
    }

    public void create(PostDto postDto){
        postService.createPost(postDto);
    }

    public void update(PostDto postDto){
        postService.updatePost(postDto);
    }

    public void delete(Long id){
        postService.deletePost(id);
    }
}
