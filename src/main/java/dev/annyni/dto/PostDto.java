package dev.annyni.dto;

import dev.annyni.model.Status;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * todo Document type PostDto
 */
public record PostDto(Long id,
                      String content,
                      Date create,
                      Date update,
                      WriterDto writer,
                      List<LabelDto> labels,
                      Status status) {
}
