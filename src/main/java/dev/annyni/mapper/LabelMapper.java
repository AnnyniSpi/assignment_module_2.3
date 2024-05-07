package dev.annyni.mapper;

import dev.annyni.dto.LabelDto;
import dev.annyni.dto.PostDto;
import dev.annyni.model.Label;
import dev.annyni.model.Post;
import dev.annyni.repository.PostRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * todo Document type LabelMapper
 */
@RequiredArgsConstructor
public class LabelMapper implements Mapper<LabelDto, Label> {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Override
    public Label mapFrom(LabelDto object) {
        return Label.builder()
            .name(object.name())
            .status(object.status())
            .post(postRepository.findById(object.post().id()).orElseThrow(IllegalArgumentException::new))
            .build();
    }

    public LabelDto mapToDto(Label object) {
        return new LabelDto(
            object.getId(),
            object.getName(),
            object.getStatus(),
            Optional.ofNullable(object.getPost())
                .map(postMapper::mapToDto)
                .orElse(null)
            );
    }
}
