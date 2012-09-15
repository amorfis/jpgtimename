package pl.vegasoft.jpgtimename.actions;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import pl.vegasoft.jpgtimename.FileListModel;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;

public class ProcessFiles extends AbstractAction {
	
	private FileListModel m_model;
	
	private List<ErrorsListener> m_errorListeners = new CopyOnWriteArrayList<ErrorsListener>();
	
	public ProcessFiles(FileListModel model) {
		m_model = model;
		putValue(Action.NAME, "Process files");
	}
	
	public void addErrorListener(ErrorsListener listener) {
		m_errorListeners.add(listener);
	}
	
	public void removeErrorListener(ErrorsListener listener) {
		m_errorListeners.remove(listener);
	}
	
	protected void fireError(String message) {
		for(ErrorsListener listener : m_errorListeners) {
			listener.errorHappend(message);
		}
	}

	public void actionPerformed(ActionEvent event) {
		File[] filesToChange = new File[m_model.getSize()];
		for(int i = 0; i < filesToChange.length; i++) {
			filesToChange[i] = m_model.getElementAt(i);
		}
		
		if (filesToChange.length == 0) {
			fireError("There are no files to change.");
		}
		
		for(File file : filesToChange) {
			try {
				Metadata metadata = ImageMetadataReader.readMetadata(file);
				Directory directory = metadata.getDirectory(ExifIFD0Directory.class);
				Date date  = directory.getDate(ExifIFD0Directory.TAG_DATETIME);
				Locale plLocale = new Locale("pl", "PL");
				DateFormatter formatter = new DateFormatter(DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, plLocale));
				
				System.out.println("Renaming " + file.getAbsolutePath());
				
				file.renameTo(new File(file.getParentFile(), formatter.valueToString(date).replace(":", "_") + ".jpg"));
			} catch (ImageProcessingException e) {
                fireError("Looks like " + file.getAbsolutePath() + " is not a JPG");
			} catch (ParseException e) {
				fireError("Invalid date in file " + file.getAbsolutePath());
            } catch (IOException e) {
                fireError("IO exception while processing the file " + file.getAbsolutePath());
            }
        }
		
		System.out.println("Finished");
	}

}
