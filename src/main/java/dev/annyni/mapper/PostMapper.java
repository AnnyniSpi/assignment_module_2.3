package dev.annyni.mapper;

import dev.annyni.dto.PostDto;
import dev.annyni.model.Post;
import dev.annyni.repository.LabelRepository;
import dev.annyni.repository.WriterRepository;
import lombok.RequiredArgsConstructor;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * todo Document type PostMapper
 */
@RequiredArgsConstructor
public class PostMapper implements Mapper<PostDto, Post>{

    private final WriterRepository writerRepository;
    private final LabelRepository labelRepository;
    private final WriterMapper writerMapper;
    private final LabelMapper labelMapper;

    @Override
    public Post mapFrom(PostDto object) {
        return Post.builder()
            .id(object.id())
            .content(object.content())
            .created(object.create())
            .updated(object.update())
            .writer(writerRepository.findById(object.writer().getId()).orElseThrow(IllegalArgumentException::new))
            .labels(labelRepository.findAll().stream().filter(label -> Objects.equals(label.getPost().getId(), object.id())).collect(Collectors.toList()))
            .status(object.status())
            .build();
    }

    @Override
    public PostDto mapToDto(Post object){
        return new PostDto(
            object.getId(),
            object.getContent(),
            object.getCreated(),
            object.getUpdated(),
            Optional.ofNullable(object.getWriter())
                .map(writerMapper::mapToDto)
                .orElse(null),
            object.getLabels().stream()
                .map(labelMapper::mapToDto)
                .collect(Collectors.toList()),
            object.getStatus()
        );
    }


}
