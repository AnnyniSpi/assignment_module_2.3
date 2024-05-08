package dev.annyni.dto;

import dev.annyni.model.Label;
import dev.annyni.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * todo Document type LabelDto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LabelDto {
    private Long id;
    private String name;
    private Status status;

    public Label toEntity(){
        return Label.builder()
            .id(this.id)
            .name(this.name)
            .status(this.status)
            .build();
    }

    public static LabelDto fromEntity(Label object){
        return LabelDto.builder()
            .id(object.getId())
            .name(object.getName())
            .status(object.getStatus())
            .build();
    }
}
