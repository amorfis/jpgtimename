package pl.vegasoft.jpgtimename;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import pl.vegasoft.jpgtimename.actions.AddFiles;
import pl.vegasoft.jpgtimename.actions.ErrorsListener;
import pl.vegasoft.jpgtimename.actions.ProcessFiles;
import pl.vegasoft.jpgtimename.actions.RemoveFiles;
import pl.vegasoft.jpgtimename.tools.FileNamer;

public class FilesWindow extends JFrame {
	
	private JList m_filesList = new JList(new FileListModel());
	
	public FilesWindow() {
		m_filesList.getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		getContentPane().setLayout(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(m_filesList);
		//scrollPane.setPreferredSize(new Dimension(500, 500));
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		getContentPane().add(createButtonsPanel(), BorderLayout.SOUTH);
		pack();
	}
	
	private JPanel createButtonsPanel() {
		JPanel panel = new JPanel(new FlowLayout());
		panel.add(new JButton(new AddFiles((FileListModel) m_filesList.getModel())));
		panel.add(new JButton(new RemoveFiles(m_filesList)));
		
		ProcessFiles action = new ProcessFiles((FileListModel) m_filesList.getModel(), new FileNamer());
		action.addErrorListener(new ErrorsListener() {

			public void errorHappend(String message) {
				JOptionPane.showMessageDialog(FilesWindow.this, message, "Error happend", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		panel.add(new JButton(action));
		
		return panel;
	}

}
