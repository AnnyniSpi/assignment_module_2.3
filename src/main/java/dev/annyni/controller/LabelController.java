package dev.annyni.controller;

import dev.annyni.dto.LabelDto;
import dev.annyni.service.LabelService;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;

/**
 * todo Document type LabelController
 */
@RequiredArgsConstructor
public class LabelController {

    private final LabelService labelService;

    public Optional<LabelDto> getById(Long id){
        return labelService.getByIdLabel(id);
    }

    public List<LabelDto> getAll(){
        return labelService.getAllLabels();
    }

    public void create(LabelDto label){
        labelService.createLabel(label);
    }

    public void update(LabelDto updateLabel){
        labelService.updateLabel(updateLabel);
    }

    public void delete(Long id){
        labelService.deleteLabel(id);
    }

}
