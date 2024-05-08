package dev.annyni.service;

import dev.annyni.dto.WriterDto;
import dev.annyni.model.Writer;
import dev.annyni.repository.WriterRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * todo Document type WriterService
 */
@RequiredArgsConstructor
public class WriterService {

    private final WriterRepository writerRepository;

    public Long createWriter(WriterDto writerDto) {
        Writer writer = writerDto.toEntity();
        return writerRepository.save(writer).getId();
    }

    public Optional<WriterDto> getByIdWriter(Long id) {
        return writerRepository.findById(id)
            .map(WriterDto::fromEntity);
    }

    public boolean deleteWriter(Long id) {
        Optional<Writer> maybeWriter = writerRepository.findById(id);
        maybeWriter.ifPresent(writer -> writerRepository.delete(writer.getId()));
        return maybeWriter.isPresent();
    }

    public Long updateWriter(WriterDto writerDto) {
        Writer writer = writerDto.toEntity();
        writerRepository.update(writer);
        return writer.getId();
    }

    public List<WriterDto> getAllWriters(){
        return writerRepository.findAll().stream()
            .map(WriterDto::fromEntity)
            .collect(Collectors.toList());
    }
}
