package pl.vegasoft.jpgtimename;

import java.text.ParseException;

import javax.swing.JFrame;

import com.drew.metadata.MetadataException;

public class JpgTimeName {
	
	public static void main(String[] args) throws MetadataException, ParseException {
		
		FilesWindow window = new FilesWindow();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		
//		FileResolver resolver = new FileResolver();
//		File[] filesToChange = resolver.getFiles();
//		
//		if (filesToChange.length == 0) {
//			System.out.println("There are no files to change.");
//			System.exit(1);
//		}
//		
//		for(File file : filesToChange) {
//			try {
//				Metadata metadata = JpegMetadataReader.readMetadata(file);
//				Directory directory = metadata.getDirectory(ExifDirectory.class);
//				Date date  = directory.getDate(ExifDirectory.TAG_DATETIME);
//				Locale plLocale = new Locale("pl", "PL");
//				DateFormatter formatter = new DateFormatter(DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, plLocale));
//				
//				System.out.println("Renaming " + file.getAbsolutePath());
//				
//				file.renameTo(new File(file.getParentFile(), formatter.valueToString(date).replace(":", "_") + ".jpg"));
//			} catch (JpegProcessingException e) {
//				System.out.println("Looks like " + file.getAbsolutePath() + " is not a JPG");
//			} catch (MetadataException e) {
//				System.out.println("File " + file.getAbsolutePath() + " does not have date/time set");
//			}
//		}
	}

}
