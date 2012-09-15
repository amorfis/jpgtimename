package pl.vegasoft.jpgtimename;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.AbstractListModel;

public class FileListModel extends AbstractListModel {
	
	private List<File> m_files = new ArrayList<File>();

	public File getElementAt(int index) {
		return m_files.get(index);
	}
	
	public void add(File file) {
		addIfNotContains(file);
	}
	
	private void addIfNotContains(File file) {
		int nextIndex = m_files.size();
		if (!m_files.contains(file)) {
			m_files.add(file);
			fireIntervalAdded(this, nextIndex, nextIndex);
		}
	}
	
	public void addAll(Collection<File> files) {
		for(File file : files) {
			addIfNotContains(file);
		}
	}
	
	public void remove(File file) {
		removeFile(file);
	}
	
	private void removeFile(File file) {
		int index = m_files.indexOf(file);
		if (m_files.remove(file)) {
			fireIntervalRemoved(this, index, index);
		}
	}

	public int getSize() {
		return m_files.size();
	}

}
