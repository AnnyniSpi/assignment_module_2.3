import dev.annyni.dto.LabelDto;
import dev.annyni.mapper.MapperManager;
import dev.annyni.model.Label;
import dev.annyni.model.Post;
import dev.annyni.model.Status;
import dev.annyni.repository.LabelRepository;
import dev.annyni.service.LabelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

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

    @Mock
    private LabelRepository labelRepository;

    private LabelService labelService;

    private Label testLabel;

    private MapperManager manager;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
        labelService = new LabelService(labelRepository, manager);

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
        assertEquals("Test", label.name());
        assertEquals(Status.ACTIVE, label.status());
    }

    @Test
    void getAllLabelsTest(){
        List<Label> labels = new ArrayList<>();
        labels.add(testLabel);

        Mockito.when(labelRepository.findAll()).thenReturn(labels);

        List<LabelDto> labelList = labelService.getAllLabels();

        assertNotNull(labelList);
        assertEquals(1, labelList.size());
        assertEquals("Test", labelList.get(0).name());
        assertEquals(Status.ACTIVE, labelList.get(0).status());
    }

    @Test
    void createLabelTest(){
        Mockito.when(labelRepository.save(any(Label.class))).thenReturn(testLabel);

        Long labelId = labelService.createLabel(manager.mapLabelToDto(testLabel));
        Optional<LabelDto> labelDto = labelService.getByIdLabel(labelId);
        LabelDto label = labelDto.get();

        assertNotNull(labelId);
        assertEquals(1L, labelId);
        assertEquals("Test", label.name());
        assertEquals(Status.ACTIVE, label.status());

        verify(labelRepository, times(1)).save(testLabel);
    }

    @Test
    void updateLabelTest(){
        Label updateLabel = Label.builder()
            .id(1L)
            .name("Update")
            .status(Status.ACTIVE)
            .post(new Post())
            .build();

        Mockito.when(labelRepository.update(any(Label.class))).thenReturn(updateLabel);

        Long labelId = labelService.updateLabel(manager.mapLabelToDto(updateLabel));
        Optional<LabelDto> labelDto = labelService.getByIdLabel(labelId);
        LabelDto label = labelDto.get();

        assertNotNull(label);
        assertEquals(1L, labelId);
        assertEquals("Update", label.name());
        assertEquals(Status.ACTIVE, label.status());

        verify(labelRepository, times(1)).save(updateLabel);
    }

    @Test
    void deleteLabelTest(){
        boolean result = labelService.deleteLabel(1L);

        assertTrue(result);
        verify(labelRepository, times(1)).delete(1L);
    }
}
