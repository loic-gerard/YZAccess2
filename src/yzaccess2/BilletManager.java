/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yzaccess2;


import java.sql.ResultSet;
/**
 *
 * @author lgerard
 */
public class BilletManager {
    public static void scanProcess(KDCManager kdc, String code){
        
        Billet billet = new Billet(code, null);
        
        if(!billet.exists()){
            //BILLET INEXISTANT
            
            kdc.interf.setActivite("Billet "+code+" INEXISTANT");
            kdc.interf.setRed();
            kdc.interf.clearScreenWithDelay();
            if(kdc != null){
                kdc.comm.kdcCmd_message("ERR1");
                kdc.comm.kdcCmd_redLight();
                kdc.comm.kdcCmd_errorBeep();
            }
            return;
        }
        
        if(!billet.isValide()){
            //BILLET INVALIDE
            kdc.interf.setActivite("Billet "+code+" INVALIDE");
            kdc.interf.setRed();
            kdc.interf.clearScreenWithDelay();
            billet.logActivity("BILLET INVALIDE");
            
            if(kdc != null){
                kdc.comm.kdcCmd_message("ERR2");
                kdc.comm.kdcCmd_redLight();
                kdc.comm.kdcCmd_errorBeep();
            }
            return;
        }
        
        if(!billet.istypeValide()){
            //TYPE BILLET INVALIDE
            kdc.interf.setActivite("Type billet "+billet.getType()+" INVALIDE");
            kdc.interf.setRed();
            kdc.interf.clearScreenWithDelay();
            billet.logActivity("TYPE "+billet.getType()+" INVALIDE");
            
            if(kdc != null){
                kdc.comm.kdcCmd_message("ERR3");
                kdc.comm.kdcCmd_redLight();
                kdc.comm.kdcCmd_errorBeep();
            }
            return;
        }
        
        //TODO : deja valide
        if(billet.isAlreadyValidated()){
            System.out.println("DEJA VALIDE");
            
            
            //TYPE BILLET DEJA VALIDE
            kdc.interf.setActivite("Billet "+code+" déjà validé \n\rle "+billet.getLastValidation());
            kdc.interf.setRed();
            kdc.interf.clearScreenWithDelay();
            billet.logActivity("Billet "+code+" déjà validé \n\rle "+billet.getLastValidation());
            
            if(kdc != null){
                kdc.comm.kdcCmd_message("ERR4");
                kdc.comm.kdcCmd_redLight();
                kdc.comm.kdcCmd_errorBeep();
            }
            
            return;
        }
        
        kdc.interf.setActivite("Billet "+code+" VALIDE");
        kdc.interf.setGreen();
        kdc.interf.clearScreenWithDelay();
        billet.logActivity("OK");
        
        if(kdc != null){
            kdc.comm.kdcCmd_message("OK !");
            kdc.comm.kdcCmd_greenLight();
            kdc.comm.kdcCmd_okBeep();
        }
        
    }
}
