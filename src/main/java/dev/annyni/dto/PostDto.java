package dev.annyni.dto;

import dev.annyni.model.Label;
import dev.annyni.model.Post;
import dev.annyni.model.Status;
import dev.annyni.model.Writer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

/**
 * todo Document type PostDto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;
    private String content;
    private Date create;
    private Date update;
    private WriterDto writer;
    private List<LabelDto> labels;
    private Status status;

    public Post toEntity(){
        List<Label> labels = this.getLabels().stream()
            .map(LabelDto::toEntity)
            .toList();

        Writer writer = this.toEntity().getWriter();

        return Post.builder()
            .id(this.id)
            .content(this.content)
            .created(this.create)
            .updated(this.update)
            .labels(labels)
            .writer(writer)
            .status(this.status)
            .build();
    }

    public static PostDto fromEntity(Post object){
        List<LabelDto> labelsDto = object.getLabels().stream()
            .map(LabelDto::fromEntity)
            .toList();

        WriterDto writerDto = WriterDto.fromEntity(object.getWriter());

        return PostDto.builder()
            .id(object.getId())
            .content(object.getContent())
            .create(object.getCreated())
            .update(object.getUpdated())
            .labels(labelsDto)
            .writer(writerDto)
            .status(object.getStatus())
            .build();
    }
}
