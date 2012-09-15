package pl.vegasoft.jpgtimename.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;

import pl.vegasoft.jpgtimename.FileListModel;
import pl.vegasoft.jpgtimename.FileResolver;

public class AddFiles extends AbstractAction {
	
	private FileListModel m_model;
	
	private FileResolver resolver = new FileResolver();
	
	public AddFiles(FileListModel model) {
		m_model = model;
		putValue(Action.NAME, "Add files");
	}

	public void actionPerformed(ActionEvent e) {
		List<File> filesToChange = resolver.getFiles();
		m_model.addAll(filesToChange);
	}

}
