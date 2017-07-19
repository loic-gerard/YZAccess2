/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yzaccess2;

import org.ini4j.Wini;
import java.io.File;

public class Config {

	private static boolean config_verbose = true;
	private static int config_cmdretrycount = 10;
	private static int config_cmdretrytime = 500;
	private static String[] config_typesbilletacceptes;
	private static String config_dbpath = "";

	public static void init() {
		try {
			Wini ini = new Wini(new File("config.ini"));
			if (!ini.get("yzaccess", "yzaccess_verbose").equals("true")) {
				config_verbose = false;
			}
			config_cmdretrycount = Integer.parseInt(ini.get("yzaccess", "yzaccess_cmdretrycount"));
			config_cmdretrytime = Integer.parseInt(ini.get("yzaccess", "yzaccess_cmdretrytime"));
			config_typesbilletacceptes = ini.get("yzaccess", "yzaccess_typesbilletacceptes").split(",");
			config_dbpath = ini.get("global", "global_dbpath");

		} catch (Exception e) {
			e.printStackTrace();
			new CrashError("Erreur de lecture du fichier de configuration", e, null);
		}
	}

	public static boolean isVerbose() {
		return config_verbose;
	}

	public static String getDbPath(){
		return config_dbpath;
	}
	
	public static int getCmdRetryCount() {
		return config_cmdretrycount;
	}

	public static int getCmdRetryTime() {
		return config_cmdretrytime;
	}

	public static boolean isTypeBilletAccepte(String typebillet) {
		for (int i = 0; i < config_typesbilletacceptes.length; i++) {
			if (config_typesbilletacceptes[i].equals(typebillet)) {
				return true;
			}
		}
		return false;
	}
}
