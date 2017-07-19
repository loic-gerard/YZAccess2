/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yzaccess2;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author lgerard
 */
public class GraphicMainInterface extends JFrame {
	private JTextArea logs;
    private GraphicActivityPanel activityPanel; 
    private int maxLines = 50;
    private Vector lines;
	public GraphicActionsPanel actions;
	
	public GraphicMainInterface() {
		super();

		lines = new Vector();

		setSize(1000, 755);
		setTitle(YZAccess2.appzName);

		WindowAdapter winCloser = new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		};
		addWindowListener(winCloser);

		Container contenu = getContentPane();
		contenu.setLayout(new BorderLayout());

		//Logs d'activité
		logs = new JTextArea();
		logs.setVisible(true);
		logs.setText("");

		JScrollPane ScrollBar = new javax.swing.JScrollPane();
		ScrollBar.setPreferredSize(new Dimension(200, 200));
		ScrollBar.setViewportView(logs);
		ScrollBar.setVisible(true);
		ScrollBar.setBounds(20, 90, 500, 200);

		contenu.add(ScrollBar, BorderLayout.SOUTH);
		logs.setText("");

		//Panel avec les ports COM
		activityPanel = new GraphicActivityPanel();
		contenu.add(activityPanel, BorderLayout.CENTER);
		
		//Panel avec les boutons
		actions = new GraphicActionsPanel();
		contenu.add(actions, BorderLayout.EAST);

		setEnabled(true);
		setVisible(true);
		this.toFront();

		this.pack();
		this.setDefaultLookAndFeelDecorated(true);
		this.setExtendedState(this.MAXIMIZED_BOTH);
	}

	public void addDouchette(GraphicPortCom douchette) {
		activityPanel.addDouchette(douchette);
	}
	
	public void removeDouchette(GraphicPortCom douchette){
		activityPanel.remove(douchette);
		this.repaint();
	}

	public void writeInLog(String log) {
		lines.add(log);
		if (lines.size() > maxLines) {
			lines.remove(0);
		}

		String toadd = "";
		for (int i = 0; i < lines.size(); i++) {
			toadd += lines.get(i) + "\n";
		}

		logs.setText(toadd);
		logs.setCaretPosition(logs.getDocument().getLength());
	}
}
