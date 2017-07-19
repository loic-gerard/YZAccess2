/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yzaccess2;

import java.awt.Color;
import madura.kdc.KDCCommunication;
import madura.debug.Log;

public class KDCManager {
	public GraphicPortCom interf;
	private String port;
	public KDCCommunication comm;
	private boolean ready = false;
	
	public KDCManager(String portCOM, GraphicPortCom in_interf){
		interf = in_interf;
		port = portCOM;
		comm = new KDCCommunication(port);
		comm.setOnConnexionOpenedCallBack(this, "eventOnConnexionOpened");
		comm.setOnErrorCallBack(this, "eventOnError");
		comm.setOnMessageReadedCallBack(this, "eventOnMessage");
		comm.setOnReadyCallBack(this, "eventOnReady");
	}
	
	public void connect(){
		interf.setActivite("Connexion en cours...");
		interf.setOrange();
		comm.start();
	}
	
	public void eventOnConnexionOpened(Object kdc){
		interf.setActivite("Ouverture du device...");
	}
	
	public void eventOnError(Object kdc, Object in_details){
		String details = (String)in_details;
		interf.setActivite("Erreur : "+details);
		interf.setRed();
	}
	
	public void eventOnMessage(Object kdc, Object in_message){
		String message = (String)in_message;
		interf.setLastScaned("Scanné : "+message);
		BilletManager.scanProcess(this, message);
	}
	
	public void eventOnReady(Object kdc){
		ready = true;
		interf.setActivite("Connecté");
		interf.setGreen();
	}
}
