package pl.vegasoft.jpgtimename.actions;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.CanonMakernoteDirectory;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.jpeg.JpegDirectory;
import com.google.common.collect.ImmutableList;
import pl.vegasoft.jpgtimename.FileListModel;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ProcessFiles extends AbstractAction {
	
	private FileListModel m_model;
	
	private List<ErrorsListener> m_errorListeners = new CopyOnWriteArrayList<ErrorsListener>();
    private static final List<Class<? extends Directory>> DIRECTORY_CLASSES = ImmutableList.<Class<? extends Directory>>of(
            ExifIFD0Directory.class,
            CanonMakernoteDirectory.class,
            JpegDirectory.class
    );

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
                Directory directory = getDirectory(metadata, DIRECTORY_CLASSES);

                if (directory == null) {
                    fireError("Metadata directory not found in file " + file.getAbsolutePath());
                } else {
                    Date date  = directory.getDate(ExifIFD0Directory.TAG_DATETIME);
                    Locale plLocale = new Locale("pl", "PL");
                    DateFormatter formatter = new DateFormatter(DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, plLocale));

                    System.out.println("Renaming " + file.getAbsolutePath());

                    File newNamedFile = new File(file.getParentFile(), formatter.valueToString(date).replace(":", "_") + ".jpg");

                    Path source = Paths.get(file.getAbsolutePath());
                    Path dest = Paths.get(newNamedFile.getAbsolutePath());

                    Files.move(source, dest);
                }
			} catch (ImageProcessingException e) {
                fireError("Looks like " + file.getAbsolutePath() + " is not a JPG");
			} catch (ParseException e) {
				fireError("Invalid date in file " + file.getAbsolutePath());
            } catch (FileAlreadyExistsException e) {
                fireError("File already exists " + e.getMessage());
            } catch (IOException e) {
                fireError("IO exception while processing the file " + file.getAbsolutePath());
            }
        }
		
		System.out.println("Finished");
	}

    private Directory getDirectory(Metadata metadata, List<Class<? extends Directory>> directoryClasses) {
        for(Class<? extends Directory> dirClass : directoryClasses) {
            Directory directory = metadata.getDirectory(dirClass);
            if (directory != null) {
                return directory;
            }
        }

        return null;
    }

}
