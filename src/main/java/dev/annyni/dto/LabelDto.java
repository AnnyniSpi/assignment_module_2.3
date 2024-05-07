package dev.annyni.dto;

import dev.annyni.model.Status;

/**
 * todo Document type LabelDto
 */
public record LabelDto (Long id,
                       String name,
                       Status status,
                       PostDto post){
}
