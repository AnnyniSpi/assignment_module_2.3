package dev.annyni.mapper;

import dev.annyni.dto.LabelDto;
import dev.annyni.dto.PostDto;
import dev.annyni.dto.WriterDto;
import dev.annyni.model.Label;
import dev.annyni.model.Post;
import dev.annyni.model.Writer;
import lombok.RequiredArgsConstructor;

/**
 * todo Document type MapperManager
 */
@RequiredArgsConstructor
public class MapperManager{

    private final LabelMapper labelMapper;
    private final PostMapper postMapper;
    private final WriterMapper writerMapper;

    public Label mapLabelFromDto(LabelDto labelDto) {
        return labelMapper.mapFrom(labelDto);
    }

    public LabelDto mapLabelToDto(Label label) {
        return labelMapper.mapToDto(label);
    }

    public Writer mapWriterFromDto(WriterDto writerDto) {
        return writerMapper.mapFrom(writerDto);
    }

    public WriterDto mapWriterToDto(Writer writer) {
        return writerMapper.mapToDto(writer);
    }

    public Post mapPostFromDto(PostDto postDto) {
        return postMapper.mapFrom(postDto);
    }

    public PostDto mapPostToDto(Post post) {
        return postMapper.mapToDto(post);
    }


}
