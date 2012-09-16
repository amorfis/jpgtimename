package pl.vegasoft.jpgtimename.tools;

import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;

import java.util.Date;

public abstract class AbstractMetadataDirectoryReader {

    public Date tryGetPhotoTakenDate(Metadata metadata) {
        Directory directory = metadata.getDirectory(directoryClass());
        if (directory == null) {
            return null;
        }

        Date date = directory.getDate(tagType());

        return date;
    }

    protected abstract int tagType();

    protected abstract Class<? extends Directory> directoryClass();

}
