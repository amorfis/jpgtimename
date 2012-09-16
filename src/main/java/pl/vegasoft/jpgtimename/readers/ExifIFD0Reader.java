package pl.vegasoft.jpgtimename.readers;

import com.drew.metadata.Directory;
import com.drew.metadata.exif.ExifIFD0Directory;
import pl.vegasoft.jpgtimename.tools.AbstractMetadataDirectoryReader;

public class ExifIFD0Reader extends AbstractMetadataDirectoryReader {
    @Override
    protected int tagType() {
        return ExifIFD0Directory.TAG_DATETIME;
    }

    @Override
    protected Class<? extends Directory> directoryClass() {
        return ExifIFD0Directory.class;
    }
}
