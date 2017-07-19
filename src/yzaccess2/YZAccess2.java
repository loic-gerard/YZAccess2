/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yzaccess2;
import java.util.Vector;
import madura.debug.Log;
import madura.kdc.KDCCommunication;
import madura.kdc.KDCDiscoverer;

public class YZAccess2 {

	public static String appzName = "Yzeure And Rock - Access";
    public static String version = "Version 2.0.0";
    public static YZAccess2 appz;
	
	public static GraphicMainInterface interf;
	public static DbConnexion cnx;
	
	private static Vector douchettes;
	
	private static KDCDiscoverer discoverer;
	
	public static void main(String[] args) {
		appz = new YZAccess2();
	}
	
	public YZAccess2(){
		//Initialisation
		douchettes = new Vector();
		
		//Création de l'interface
		interf = new GraphicMainInterface();
		
		Log.addOnLogCallBack(this, "eventOnLog");
		
		Log.log(appzName);
		Log.log(version);
		Log.log("----------------------------------------");
		
		//Chargement du fichier de configuration (config.ini)
		Config.init();
		
		//Connexion à la DBB
        cnx = new DbConnexion();
        cnx.connect();
		
		//Détection des douchettes
		detectNewDevices();
		
	}
	
	public void detectNewDevices(){
		interf.actions.updateInfos("Détection des devices en cours...");
		discoverer = new KDCDiscoverer();
		discoverer.setOnEndedCallBack(this, "eventOnNewDevicesDetected");
		discoverer.start();
	}
	
	public void testAll(){
		for(int i = 0; i<douchettes.size(); i++){
			KDCManager kdc = (KDCManager)douchettes.get(i);
			kdc.comm.kdcCmd_okBeep();
			kdc.comm.kdcCmd_greenLight();
			kdc.comm.kdcCmd_message("TEST OK !");
		}
	}
	
	public void closeAll(){
		for(int i = 0; i<douchettes.size(); i++){
			KDCManager kdc = (KDCManager)douchettes.get(i);
			kdc.comm.close();
			YZAccess2.interf.removeDouchette(kdc.interf);
		}
		douchettes = new Vector();
	}
	
	public void eventOnNewDevicesDetected(Object in_devices){
		Vector devices = (Vector)in_devices;
		interf.actions.updateInfos("Initialisation des devices...");
		
		for(int i = 0; i < devices.size(); i++){
			String port = (String) devices.get(i);
			addDouchette(port);
		}
		
		interf.actions.updateInfos("Fonctionnement normal");
		interf.actions.allowCloseAll();
		interf.actions.allowDiscover();
		interf.actions.allowTestAll();
	}
	
	public void addDouchette(String port){
		GraphicPortCom gpc = new GraphicPortCom(port, port);
		KDCManager kdc = new KDCManager(port, gpc);
		douchettes.add(kdc);
		interf.addDouchette(gpc);
		kdc.connect();
	}
	
	public void eventOnLog(Object in_message, Object in_classe){
		String message = (String)in_message;
		String classe = (String)in_classe;
		
		if(interf != null){
			interf.writeInLog(message);
		}
	}
	
	public static void closeDouchette(GraphicPortCom pc) {
		for(int i = 0; i<douchettes.size(); i++){
			KDCManager kdc = (KDCManager)douchettes.get(i);
			if(kdc.interf == pc){
				kdc.comm.close();
				YZAccess2.interf.removeDouchette(kdc.interf);
				douchettes.remove(kdc);
			}
		}
	}

	public static void openDouchette(GraphicPortCom pc) {
		
	}
	
}
