package pl.vegasoft.jpgtimename.actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JList;

import pl.vegasoft.jpgtimename.FileListModel;

public class RemoveFiles extends AbstractAction {
	
	private JList m_list;
	
	public RemoveFiles(JList list) {
		m_list = list;
		putValue(Action.NAME, "Remove files");
	}

	public void actionPerformed(ActionEvent e) {
		Object[] selected = m_list.getSelectedValues();
		for(Object file : selected) {
			((FileListModel) m_list.getModel()).remove((File) file);
		}
	}

}
