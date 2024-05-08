package dev.annyni.service;

import dev.annyni.dto.PostDto;
import dev.annyni.model.Post;
import dev.annyni.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * todo Document type PostService
 */
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Long createPost(PostDto postDto) {
        Post post = postDto.toEntity();
        return postRepository.save(post).getId();
    }

    public Optional<PostDto> getByIdPost(Long id) {
        return postRepository.findById(id)
            .map(PostDto::fromEntity);
    }

    public boolean deletePost(Long id) {
        Optional<Post> maybePost = postRepository.findById(id);
        maybePost.ifPresent(post -> postRepository.delete(post.getId()));
        return maybePost.isPresent();
    }

    public Long updatePost(PostDto postDto) {
        Post post = postDto.toEntity();
        postRepository.update(post);
        return post.getId();
    }

    public List<PostDto> getAllPosts(){
        return postRepository.findAll().stream()
            .map(PostDto::fromEntity)
            .collect(Collectors.toList());
    }
}
