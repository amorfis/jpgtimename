package pl.vegasoft.jpgtimename.readers;

import com.drew.metadata.Directory;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import pl.vegasoft.jpgtimename.tools.AbstractMetadataDirectoryReader;

public class ExifSubIfDReader extends AbstractMetadataDirectoryReader {
    @Override
    protected int tagType() {
        return ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL;
    }

    @Override
    protected Class<? extends Directory> directoryClass() {
        return ExifSubIFDDirectory.class;
    }
}
