import dev.annyni.dto.LabelDto;
import dev.annyni.model.Label;
import dev.annyni.model.Status;
import dev.annyni.repository.LabelRepository;
import dev.annyni.service.LabelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * todo Document type LabelServiceTest
 */
public class LabelServiceTest {

    private LabelRepository labelRepository;

    private LabelService labelService;

    private Label testLabel;


    @BeforeEach
    void init(){
        labelRepository = Mockito.mock(LabelRepository.class);
        labelService = new LabelService(labelRepository);

        testLabel = Label.builder()
            .id(1L)
            .name("Test")
            .status(Status.ACTIVE)
            .build();
    }

    @Test
    void findByIdTest(){
        Mockito.when(labelRepository.findById(anyLong())).thenReturn(Optional.ofNullable(testLabel));

        Optional<LabelDto> labelDto = labelService.getByIdLabel(1L);
        LabelDto label = labelDto.get();

        assertNotNull(label);
        assertEquals("Test", label.getName());
        assertEquals(Status.ACTIVE, label.getStatus());
    }

    @Test
    void getAllLabelsTest(){
        List<Label> labels = new ArrayList<>();
        labels.add(testLabel);

        Mockito.when(labelRepository.findAll()).thenReturn(labels);

        List<LabelDto> labelList = labelService.getAllLabels();

        assertNotNull(labelList);
        assertEquals(1, labelList.size());
        assertEquals("Test", labelList.get(0).getName());
        assertEquals(Status.ACTIVE, labelList.get(0).getStatus());
    }

    @Test
    void createLabelTest(){
        Mockito.when(labelRepository.save(any(Label.class))).thenReturn(testLabel);

        Long labelId = labelService.createLabel(LabelDto.fromEntity(testLabel));
        Optional<LabelDto> labelDto = labelService.getByIdLabel(labelId);
        if (labelDto.isPresent()){
            LabelDto label = labelDto.get();

            assertNotNull(labelId);
            assertEquals(1L, labelId);
            assertEquals("Test", label.getName());
            assertEquals(Status.ACTIVE, label.getStatus());

            verify(labelRepository, times(1)).save(testLabel);
        }

    }

    @Test
    void updateLabelTest(){
        Label updateLabel = Label.builder()
            .id(1L)
            .name("Update")
            .status(Status.ACTIVE)
            .build();

        Mockito.when(labelRepository.update(any(Label.class))).thenReturn(updateLabel);

        Long labelId = labelService.updateLabel(LabelDto.fromEntity(updateLabel));
        Optional<LabelDto> labelDto = labelService.getByIdLabel(labelId);
        if (labelDto.isPresent()){
            LabelDto label = labelDto.get();

            assertNotNull(label);
            assertEquals(1L, labelId);
            assertEquals("Update", label.getName());
            assertEquals(Status.ACTIVE, label.getStatus());

            verify(labelRepository, times(1)).save(updateLabel);
        }

    }

    @Test
    void deleteLabelTest(){
        labelService.deleteLabel(1L);
        verify(labelRepository, times(1)).delete(1L);
    }
}
