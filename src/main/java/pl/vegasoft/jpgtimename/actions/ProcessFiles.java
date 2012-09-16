package pl.vegasoft.jpgtimename.actions;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.CanonMakernoteDirectory;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.jpeg.JpegDirectory;
import com.google.common.collect.ImmutableList;
import org.joda.time.LocalDateTime;
import pl.vegasoft.jpgtimename.FileListModel;
import pl.vegasoft.jpgtimename.tools.FileNamer;

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
    private FileNamer namer;

    private List<ErrorsListener> m_errorListeners = new CopyOnWriteArrayList<ErrorsListener>();
    private static final List<Class<? extends Directory>> DIRECTORY_CLASSES = ImmutableList.<Class<? extends Directory>>of(
            ExifIFD0Directory.class,
            CanonMakernoteDirectory.class,
            JpegDirectory.class
    );

    public ProcessFiles(FileListModel model, FileNamer namer) {
		m_model = model;
        this.namer = namer;
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
                    continue;
                }

                Date date  = directory.getDate(ExifIFD0Directory.TAG_DATETIME);
                if (date == null) {
                    fireError("Date null in file " + file.getAbsolutePath());
                    continue;
                }

                System.out.println("Renaming " + file.getAbsolutePath());

                String newFileName = namer.produceNewFileName(file.getName(), LocalDateTime.fromDateFields(date));

                Path source = Paths.get(file.getAbsolutePath());
                Path dest = Paths.get(file.getParent(), newFileName);

                Files.move(source, dest);
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
