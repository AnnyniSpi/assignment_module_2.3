import dev.annyni.dto.WriterDto;
import dev.annyni.mapper.MapperManager;
import dev.annyni.model.Status;
import dev.annyni.model.Writer;
import dev.annyni.repository.WriterRepository;
import dev.annyni.service.WriterService;
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
 * todo Document type WriterServiceTest
 */
public class WriterServiceTest {

    @Mock
    private WriterRepository writerRepository;

    private WriterService writerService;

    private Writer testWriter;

    private MapperManager manager;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
        writerService = new WriterService(writerRepository, manager);

        testWriter = Writer.builder()
            .id(1L)
            .firstName("Test")
            .lastName("Test")
            .posts(new ArrayList<>())
            .status(Status.ACTIVE)
            .build();
    }

    @Test
    void findByIdTest(){
        Mockito.when(writerRepository.findById(anyLong())).thenReturn(Optional.ofNullable(testWriter));

        Optional<WriterDto> labelDto = writerService.getByIdWriter(1L);
        WriterDto writer = labelDto.get();

        assertNotNull(writer);
        assertEquals("Test", writer.firstname());
        assertEquals("Test", writer.lastname());
        assertEquals(Status.ACTIVE, writer.status());
    }

    @Test
    void getAllWritersTest(){
        List<Writer> writers = new ArrayList<>();
        writers.add(testWriter);

        Mockito.when(writerRepository.findAll()).thenReturn(writers);

        List<WriterDto> writerList = writerService.getAllWriters();

        assertNotNull(writerList);
        assertEquals(1, writerList.size());
        assertEquals("Test", writerList.get(0).firstname());
        assertEquals("Test", writerList.get(0).lastname());
        assertEquals(Status.ACTIVE, writerList.get(0).status());
    }

    @Test
    void createWriterTest(){
        Mockito.when(writerRepository.save(any(Writer.class))).thenReturn(testWriter);

        Long writerId = writerService.createWriter(manager.mapWriterToDto(testWriter));
        Optional<WriterDto> writerDto = writerService.getByIdWriter(writerId);
        WriterDto writer = writerDto.get();

        assertNotNull(writerId);
        assertEquals(1L, writerId);
        assertEquals("Test", writer.firstname());
        assertEquals("Test", writer.lastname());
        assertEquals(Status.ACTIVE, writer.status());

        verify(writerRepository, times(1)).save(testWriter);
    }

    @Test
    void updateWriterTest(){
        Writer updateWriter = Writer.builder()
            .id(1L)
            .firstName("Update")
            .lastName("Update")
            .posts(new ArrayList<>())
            .status(Status.ACTIVE)
            .build();

        writerService.updateWriter(manager.mapWriterToDto(updateWriter));

        Long writerId = writerService.createWriter(manager.mapWriterToDto(testWriter));
        Optional<WriterDto> writerDto = writerService.getByIdWriter(writerId);
        WriterDto writer = writerDto.get();

        assertNotNull(writerId);
        assertEquals(1L, writerId);
        assertEquals("Update", writer.firstname());
        assertEquals("Update", writer.lastname());
        assertEquals(Status.ACTIVE, writer.status());

        verify(writerRepository, times(1)).update(updateWriter);
    }

    @Test
    void deleteWriterTest(){
        boolean result = writerService.deleteWriter(1L);

        assertTrue(result);
        verify(writerRepository, times(1)).delete(1L);
    }
}
