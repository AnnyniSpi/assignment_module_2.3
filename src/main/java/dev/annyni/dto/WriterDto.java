package dev.annyni.dto;

import dev.annyni.model.Status;
import dev.annyni.model.Writer;

import java.util.List;

/**
 * todo Document type WriterDto
 */
public record WriterDto(Long id,
                        String firstname,
                        String lastname,
                        List<PostDto> posts,
                        Status status) {

    public Long getId() {
        return this.id;
    }
}
