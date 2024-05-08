package dev.annyni.dto;

import dev.annyni.model.Post;
import dev.annyni.model.Status;
import dev.annyni.model.Writer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * todo Document type WriterDto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WriterDto {
    private Long id;
    private String firstname;
    private String lastname;
    private List<PostDto> posts;
    private Status status;

    public Writer toEntity(){
        List<Post> posts = this.getPosts().stream()
            .map(PostDto::toEntity)
            .toList();

        return Writer.builder()
            .id(this.id)
            .firstName(this.firstname)
            .lastName(this.lastname)
            .posts(posts)
            .status(this.status)
            .build();
    }

    public static WriterDto fromEntity(Writer object){
        List<PostDto> postsDto = object.getPosts().stream()
            .map(PostDto::fromEntity)
            .toList();

        return WriterDto.builder()
            .id(object.getId())
            .firstname(object.getFirstName())
            .lastname(object.getLastName())
            .posts(postsDto)
            .status(object.getStatus())
            .build();
    }
}
