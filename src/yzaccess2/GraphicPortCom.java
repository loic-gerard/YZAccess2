/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yzaccess2;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTextArea;
import madura.exec.CallbackTimer;
import madura.kdc.KDCCommunication;
import madura.exec.Callback;

/**
 *
 * @author lgerard
 */
public class GraphicPortCom extends JPanel {

	private JLabel activite;
	private JLabel numero;
	private JTextArea last;
	private JButton open;
	private JButton close;
	private JButton battery;
	private CallbackTimer clearScreenThread;
	private String port;
	private boolean reinitialisation = false;
	private KDCCommunication kdc;
	private String nom;

	public GraphicPortCom(String name, String portName) {
		super();

		port = portName;
		nom = name;
		this.setLayout(null);

		Border paddingBorder = BorderFactory.createEmptyBorder(20, 20, 20, 20);
		setBackground(new Color(255, 114, 0));

		JLabel type = new JLabel("TYPE");
		type.setFont(new Font("Arial", Font.BOLD, 30));
		type.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		add(type);
		type.setBounds(10, 10, 300, 50);

		numero = new JLabel(name + "\n");
		numero.setFont(new Font("Arial", Font.BOLD, 30));
		numero.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		add(numero);
		numero.setBounds(10, 40, 400, 50);

		activite = new JLabel("");
		activite.setFont(new Font("Arial", Font.BOLD, 20));
		activite.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		add(activite);
		activite.setBounds(10, 70, 400, 50);

        //JLabel lastTitle = new JLabel("Dernière activité :");
		//lastTitle.setFont(new Font("Arial", Font.BOLD, 15));
		//lastTitle.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		//add(lastTitle);
		//lastTitle.setBounds(10, 150, 200, 50);
		last = new JTextArea("");
		last.setFont(new Font("Arial", Font.PLAIN, 15));
		add(last);
		last.setBounds(10, 100, 200, 100);
		last.setOpaque(false);
		last.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		last.setText("");

		close = new JButton("Fermer la cnx");
		ActionListener btn_closeListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeCnx();
			}
		};
		close.addActionListener(btn_closeListener);
		close.setCursor(new Cursor(Cursor.HAND_CURSOR));
		add(close);
		close.setBounds(30, 160, 200, 50);
		close.setVisible(true);

		open = new JButton("Ouvrir la cnx");
		ActionListener btn_openListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openCnx();
			}
		};
		open.addActionListener(btn_openListener);
		open.setCursor(new Cursor(Cursor.HAND_CURSOR));
		add(open);
		open.setBounds(30, 160, 200, 50);
		open.setVisible(false);

		battery = new JButton("Batterie ?");
		ActionListener btn_batteryListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getBattery();
			}
		};
		battery.addActionListener(btn_batteryListener);
		battery.setCursor(new Cursor(Cursor.HAND_CURSOR));
		add(battery);
		battery.setBounds(30, 220, 200, 50);
		battery.setVisible(false);

		setBorder(BorderFactory.createMatteBorder(5, 0, 0, 5, Color.BLACK));
	}

	public String getPort() {
		return port;
	}

	public void setKDCCommunication(KDCCommunication k) {
		kdc = k;
	}

	public void getBattery() {

		kdc.sendCommand("B");

	}

	public void setBattery(int porcent) {
		numero.setText(nom + " " + porcent + "%");

		new CallbackTimer(5000, new Callback(this, "cleanBattery"));
	}

	public void cleanBattery() {
		numero.setText(nom);
	}

	public void openCnx() {
		if (reinitialisation) {
			reinitialisation = false;
			open.setVisible(false);
			close.setVisible(false);
			battery.setVisible(false);
			YZAccess2.openDouchette(this);
		}
	}

	public void closeCnx() {
		if (!reinitialisation) {
			reinitialisation = true;
			open.setVisible(false);
			close.setVisible(false);
			battery.setVisible(false);
			YZAccess2.closeDouchette(this);
		}
	}

	public void setGreen() {
		setBackground(new Color(70, 149, 0));
	}

	public void setOrange() {
		setBackground(new Color(255, 114, 0));
	}

	public void setRed() {
		setBackground(new Color(255, 0, 0));
	}

	public void setWhite() {
		setBackground(new Color(226, 241, 254));
	}

	public void setGrey() {
		setBackground(new Color(100, 100, 100));
	}

	public void setOpened() {
		close.setVisible(true);
		open.setVisible(false);
		if (kdc != null) {
			battery.setVisible(true);
		}
		reinitialisation = false;
	}

	public void setClosed() {
		close.setVisible(false);
		open.setVisible(true);
		battery.setVisible(false);
		reinitialisation = true;
		setGrey();
	}

	public void setActivite(String texte){
		activite.setText(texte);
        
    }
	
	public void setLastScaned(String texte){
		last.setText(texte);
	}

	public void clearScreenWithDelay() {
		if (clearScreenThread != null) {
			clearScreenThread.interrupt();
		}
		clearScreenThread = new CallbackTimer(2000, new Callback(this, "clearScreen"));
	}

	public void clearScreen() {
		setWhite();
	}
}
