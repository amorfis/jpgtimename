package pl.vegasoft.jpgtimename.tools;

import org.joda.time.LocalDateTime;

import javax.swing.text.DateFormatter;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

public class FileNamer {

    public String produceNewFileName(String oldFileName, LocalDateTime photoTaken) throws ParseException {
        Locale plLocale = new Locale("pl", "PL");
        DateFormatter formatter = new DateFormatter(DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, plLocale));

        String dateTimeAsString = formatter.valueToString(photoTaken.toDate()).replace(":", "_");

        String newName = dateTimeAsString + " " + oldFileName;

        if (oldFileName.startsWith(dateTimeAsString)) {
            newName = oldFileName;
        }

        return newName;
    }

}
