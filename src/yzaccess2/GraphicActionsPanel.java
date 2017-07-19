/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yzaccess2;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Insets;
/**
 *
 * @author lgerard
 */
public class GraphicActionsPanel extends JPanel implements ActionListener{
	private JButton discoverNew;
	private JButton closeAll;
	private JButton testAll;
	private JTextArea infos;
	
	public GraphicActionsPanel(){
		super();
		
		this.setLayout(new GridLayout(4, 0));
		
		discoverNew = new JButton("Analyser les ports COM");
		closeAll = new JButton("Fermer toutes les connexions");
		testAll = new JButton("Envoyer un test");
		discoverNew.addActionListener(this);
		closeAll.addActionListener(this);
		testAll.addActionListener(this);
		infos = new JTextArea("");
		infos.setBackground(null);
		infos.setMargin(new Insets(10,10,10,10));

		discoverNew.setEnabled(false);
		closeAll.setEnabled(false);
		testAll.setEnabled(false);
		this.add(infos);
		this.add(discoverNew);
		this.add(closeAll);
		this.add(testAll);
		
		updateInfos("Chargement en cours...");
	}
	
	public void updateInfos(String message){
		infos.setText(YZAccess2.appzName+" ("+YZAccess2.version+")\n"+message);
	}
	
	
	public void actionPerformed(ActionEvent e) {
        //go.setEnabled(false);
        this.repaint();
        
        if(e.getSource().equals(discoverNew)){
			discoverNew.setEnabled(false);
			closeAll.setEnabled(false);
			testAll.setEnabled(false);
			YZAccess2.appz.detectNewDevices();
        }else if(e.getSource().equals(closeAll)){
			YZAccess2.appz.closeAll();
        }else if(e.getSource().equals(testAll)){
			YZAccess2.appz.testAll();
        }
    }
	
	public void allowDiscover(){
		discoverNew.setEnabled(true);
	}
	
	public void allowCloseAll(){
		closeAll.setEnabled(true);
	}
	
	public void allowTestAll(){
		testAll.setEnabled(true);
	}
}
