package dev.annyni.controller;

import dev.annyni.dto.WriterDto;
import dev.annyni.service.WriterService;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;

/**
 * todo Document type WriterController
 */
@RequiredArgsConstructor
public class WriterController {

    private final WriterService writerService;

    public Optional<WriterDto> getById(Long id){
        return writerService.getByIdWriter(id);
    }

    public List<WriterDto> getAll(){
        return writerService.getAllWriters();
    }

    public void create(WriterDto writerDto){
        writerService.createWriter(writerDto);
    }

    public void update(WriterDto writerDto){
        writerService.updateWriter(writerDto);
    }

    public void delete(Long id){
        writerService.deleteWriter(id);
    }
}
