package dev.annyni.mapper;

import dev.annyni.dto.WriterDto;
import dev.annyni.model.Writer;
import dev.annyni.repository.PostRepository;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * todo Document type WriterMapper
 */
@RequiredArgsConstructor
public class WriterMapper implements Mapper<WriterDto, Writer>{

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Override
    public Writer mapFrom(WriterDto object) {
        return Writer.builder()
            .firstName(object.firstname())
            .lastName(object.lastname())
            .posts(postRepository.findAll().stream().filter(post -> Objects.equals(post.getWriter().getId(), object.id())).collect(Collectors.toList()))
            .status(object.status())
            .build();
    }

    public WriterDto mapToDto(Writer object) {
        return new WriterDto(
            object.getId(),
            object.getFirstName(),
            object.getLastName(),
            object.getPosts().stream()
                .map(postMapper::mapToDto).collect(Collectors.toList()),
            object.getStatus()
        );
    }
}
