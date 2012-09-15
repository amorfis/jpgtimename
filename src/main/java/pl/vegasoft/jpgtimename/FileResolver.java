package pl.vegasoft.jpgtimename;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.prefs.Preferences;

import javax.swing.JFileChooser;
//import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileFilter;

public class FileResolver {
	
	private static final String DIR_KEY = "directory";
	
	private File m_dir = new File(".");
	
	private Preferences m_prefs;
	
	public FileResolver() {
		m_prefs = Preferences.userNodeForPackage(JpgTimeName.class);
		String dir = m_prefs.get(DIR_KEY, ".");
		m_dir = new File(dir);
	}
	
	public List<File> getFiles() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fileChooser.setCurrentDirectory(m_dir);
		fileChooser.setMultiSelectionEnabled(true);
		FileFilter filter = new FileFilter() {

			@Override
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				}
				
				String fileName = f.getName();
				int i = fileName.lastIndexOf('.');
				if (i > 0 && i < fileName.length() - 1) {
					String extension = fileName.substring(i + 1);
					if ("jpg".equalsIgnoreCase(extension)) {
						return true;
					}
				}
			
				return false;
			}

			@Override
			public String getDescription() {
				return "JPG files";
			}
			
		};
//		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG Images", "jpg");
		fileChooser.setFileFilter(filter);
		int response = fileChooser.showOpenDialog(null);
		if (response == JFileChooser.APPROVE_OPTION) {
			List<File> files = new ArrayList<File>();
			
			for(File selected : fileChooser.getSelectedFiles()) {
				if (selected.isDirectory()) {
					files.addAll(getFilesFromDir(selected));
				} else {
					files.add(selected);
				}
			}
			
			m_prefs.put(DIR_KEY, fileChooser.getCurrentDirectory().getAbsolutePath());
			
			return files;
		} else {
			return new ArrayList<File>();
		}
	}
	
	private List<File> getFilesFromDir(File dir) {
		List<File> filesList = new ArrayList<File>();
		for(File file : dir.listFiles()) {
			if (file.isFile()) {
				filesList.add(file);
			} else {
				filesList.addAll(getFilesFromDir(file));
			}
		}
		
		return filesList;
	}

}
