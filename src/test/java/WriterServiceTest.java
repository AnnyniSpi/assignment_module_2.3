import dev.annyni.dto.WriterDto;
import dev.annyni.model.Status;
import dev.annyni.model.Writer;
import dev.annyni.repository.WriterRepository;
import dev.annyni.service.WriterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
 * todo Document type WriterServiceTest
 */
public class WriterServiceTest {

    @Mock
    private WriterRepository writerRepository;

    private WriterService writerService;

    private Writer testWriter;

    @BeforeEach
    void init(){
        writerRepository = Mockito.mock(WriterRepository.class);
        writerService = new WriterService(writerRepository);

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
        assertEquals("Test", writer.getFirstname());
        assertEquals("Test", writer.getLastname());
        assertEquals(Status.ACTIVE, writer.getStatus());
    }

    @Test
    void getAllWritersTest(){
        List<Writer> writers = new ArrayList<>();
        writers.add(testWriter);

        Mockito.when(writerRepository.findAll()).thenReturn(writers);

        List<WriterDto> writerList = writerService.getAllWriters();

        assertNotNull(writerList);
        assertEquals(1, writerList.size());
        assertEquals("Test", writerList.get(0).getFirstname());
        assertEquals("Test", writerList.get(0).getLastname());
        assertEquals(Status.ACTIVE, writerList.get(0).getStatus());
    }

    @Test
    void createWriterTest(){
        Mockito.when(writerRepository.save(any(Writer.class))).thenReturn(testWriter);

        Long writerId = writerService.createWriter(WriterDto.fromEntity(testWriter));
        Optional<WriterDto> writerDto = writerService.getByIdWriter(writerId);
        if (writerDto.isPresent()){
            WriterDto writer = writerDto.get();

            assertNotNull(writerId);
            assertEquals(1L, writerId);
            assertEquals("Test", writer.getFirstname());
            assertEquals("Test", writer.getLastname());
            assertEquals(Status.ACTIVE, writer.getStatus());

            verify(writerRepository, times(1)).save(testWriter);
        }

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

        writerService.updateWriter(WriterDto.fromEntity(updateWriter));

        Long writerId = writerService.createWriter(WriterDto.fromEntity(testWriter));
        Optional<WriterDto> writerDto = writerService.getByIdWriter(writerId);
        if (writerDto.isPresent()){
            WriterDto writer = writerDto.get();

            assertNotNull(writerId);
            assertEquals(1L, writerId);
            assertEquals("Update", writer.getFirstname());
            assertEquals("Update", writer.getLastname());
            assertEquals(Status.ACTIVE, writer.getStatus());

            verify(writerRepository, times(1)).update(updateWriter);
        }
    }

    @Test
    void deleteWriterTest(){
        writerService.deleteWriter(1L);
        verify(writerRepository, times(1)).delete(1L);
    }
}
