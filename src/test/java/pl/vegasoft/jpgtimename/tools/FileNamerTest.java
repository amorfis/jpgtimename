package pl.vegasoft.jpgtimename.tools;

import org.testng.annotations.Test;
import org.joda.time.LocalDateTime;

import java.text.ParseException;

import static org.fest.assertions.Assertions.assertThat;

public class FileNamerTest {

    private FileNamer namer = new FileNamer();

    @Test
    public void shouldAddDateInFrontOfFilename() throws ParseException {
        // When
        String newName = namer.produceNewFileName("Here I am", new LocalDateTime(2010, 5, 15, 17, 34, 16));

        // Then
        assertThat(newName).isEqualTo("2010-05-15 17_34_16 Here I am");
    }

    @Test
    public void shouldNotAddDateTimeIfAlreadyPresent() throws ParseException {
        // When
        String newName = namer.produceNewFileName("2010-05-15 17_34_16 The File", new LocalDateTime(2010, 5, 15, 17, 34, 16));

        // Then
        assertThat(newName).isEqualTo("2010-05-15 17_34_16 The File");
    }

}
