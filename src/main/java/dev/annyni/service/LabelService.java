package dev.annyni.service;

import dev.annyni.dto.LabelDto;
import dev.annyni.mapper.LabelMapper;
import dev.annyni.mapper.MapperManager;
import dev.annyni.model.Label;
import dev.annyni.repository.LabelRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * todo Document type LabelService
 */
@RequiredArgsConstructor
public class LabelService {

    private final LabelRepository labelRepository;
    private final MapperManager manager;

    public Long createLabel(LabelDto labelDto) {
        Label label = manager.mapLabelFromDto(labelDto);
        return labelRepository.save(label).getId();
    }

    public Optional<LabelDto> getByIdLabel(Long id) {
        return labelRepository.findById(id)
            .map(manager::mapLabelToDto);
    }

    public boolean deleteLabel(Long id) {
        Optional<Label> maybeLabel = labelRepository.findById(id);
        maybeLabel.ifPresent(label -> labelRepository.delete(label.getId()));
        return maybeLabel.isPresent();
    }

    public Long updateLabel(LabelDto labelDto) {
        Label label = manager.mapLabelFromDto(labelDto);
        labelRepository.update(label);
        return label.getId();
    }

    public List<LabelDto> getAllLabels(){
        return labelRepository.findAll().stream()
            .map(manager::mapLabelToDto)
            .collect(Collectors.toList());
    }

}
