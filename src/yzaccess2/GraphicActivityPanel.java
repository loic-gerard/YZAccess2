/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yzaccess2;

import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JPanel;

/**
 *
 * @author lgerard
 */
public class GraphicActivityPanel extends JPanel {

	public GraphicActivityPanel() {
		super();

		this.setLayout(new GridLayout(0, 4));
		/*for(int i = 0; i < 5; i++){
		 this.add(new GraphicPortCom());
		 }*/
	}

	public void addDouchette(GraphicPortCom douchette) {
		this.add(douchette);
	}
}
