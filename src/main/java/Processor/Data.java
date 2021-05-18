/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Processor;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author Mauro
 */
public class Data {
    
     private LinkedBlockingQueue<String> colaParser = new LinkedBlockingQueue<String>();
     private final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";

     
     private static ArrayList<String> categorias = new ArrayList<String>();
     private static ArrayList<String> coto_categorias = new ArrayList<String>();
     //private static String[] coto_categorias = {"https://www.cotodigital3.com.ar/sitios/cdigi/browse/catalogo-textil/_/N-l8joi7","https://www.cotodigital3.com.ar/sitios/cdigi/browse/catalogo-hogar/_/N-qa34ar","https://www.cotodigital3.com.ar/sitios/cdigi/browse/catalogo-aire-libre/_/N-w7wnle","https://www.cotodigital3.com.ar/sitios/cdigi/browse/catalogo-electro/_/N-1ngpk59"};
     private static BiMap<String, String> VendorID = HashBiMap.create();
     private static BiMap<String, String> VendorDisplay = HashBiMap.create();
     
     private boolean dbFlag = false;
     private boolean OSFlag = false;
     private boolean SyncFlag = false;
     private boolean LogFlag = false;
     private boolean LockFlag = false;
     
     private boolean Macowens = false;
     private boolean Musi = false;
     private boolean Mega = false;
     private boolean Garba = false;
     private boolean Easy = false;
     private boolean Rodo = false;
     private boolean Netshoes = false;
     private boolean Tecnostore = false;
     private boolean CasadelAudio = false;
     private boolean LOGGHW = false;
     private boolean MK = false;
     private boolean CM = false;
     private boolean AT = false;
     private boolean GK = false;
             
    
    private static Data instance = null;
    
    
    public Data() {
      
    }
    
    public static Data getInstance(){
       if(instance==null){
       instance = new Data();
      }
      return instance;

    }

    public LinkedBlockingQueue<String> getColaParser() {
        return colaParser;
    }

    public static ArrayList<String> getCategorias() {
        return categorias;
    }
    
    public static ArrayList<String> getCoto_categorias() {
        return coto_categorias;
    }

    public static void setCategorias(ArrayList<String> categorias) {
        Data.categorias = categorias;
    }

    public boolean isDbFlag() {
        return dbFlag;
    }

    public boolean isOSFlag() {
        return OSFlag;
    }

    public void setOSFlag(boolean OSFlag) {
        this.OSFlag = OSFlag;
    }

    public boolean isSyncFlag() {
        return SyncFlag;
    }

    public void setSyncFlag(boolean SyncFlag) {
        this.SyncFlag = SyncFlag;
    }

    public boolean isLogFlag() {
        return LogFlag;
    }

    public void setLogFlag(boolean LogFlag) {
        this.LogFlag = LogFlag;
    }
    
    public void setDbFlag(boolean dbFlag) {
        this.dbFlag = dbFlag;
    }

    public boolean isMacowens() {
        return Macowens;
    }

    public void setMacowens(boolean Macowens) {
        this.Macowens = Macowens;
    }
    public boolean isMK() {
        return MK;
    }

    public void setMK(boolean MK) {
        this.MK = MK;
    }
    public boolean isLOGG() {
        return LOGGHW;
    }

    public void setLOGG(boolean LOGGHW) {
        this.LOGGHW = LOGGHW;
    }

    public boolean isCM() {
        return CM;
    }

    public void setCM(boolean CM) {
        this.CM = CM;
    }
    
        public boolean isAT() {
        return AT;
    }

    public void setAT(boolean AT) {
        this.AT = AT;
    }
        public boolean isGK() {
        return GK;
    }

    public void setGK(boolean GK) {
        this.GK = GK;
    }
    public boolean isNetshoes() {
        return Netshoes;
    }

    public void setNetshoes(boolean Netshoes) {
        this.Netshoes = Netshoes;
    }
    
    public boolean isTecnostore() {
        return Tecnostore;
    }

    public void setTecnostore(boolean Tecnostore) {
        this.Tecnostore = Tecnostore;
    }
    
    public boolean isCasadelAudio() {
        return CasadelAudio;
    }

    public void setCasadelAudio(boolean CasadelAudio) {
        this.CasadelAudio = CasadelAudio;
    }
    
    public void setRodo(boolean Rodo) {
        this.Rodo = Rodo;
    }
    
    public boolean isRodo() {
        return Rodo;
    }

    public boolean isMusi() {
        return Musi;
    }

    public void setMusi(boolean Musi) {
        this.Musi = Musi;
    }

    public boolean isMega() {
        return Mega;
    }

    public void setMega(boolean Mega) {
        this.Mega = Mega;
    }

    public boolean isGarba() {
        return Garba;
    }

    public void setGarba(boolean Garba) {
        this.Garba = Garba;
    }

    public boolean isEasy() {
        return Easy;
    }

    public void setEasy(boolean Easy) {
        this.Easy = Easy;
    }
    
    
    
    public boolean isLockFlag() {
        return LockFlag;
    }

    public void setLockFlag(boolean LockFlag) {
        this.LockFlag = LockFlag;
    }
    
    public static BiMap<String, String> getVendorID() {
        return VendorID;
    }
    
    public static BiMap<String, String> getVendorDisplay() {
        return VendorDisplay;
    }


    public static void setVendorID(BiMap<String, String> VendorID) {
        Data.VendorID = VendorID;
    }


    public void setColaParser(String colaParser) {
        this.colaParser.add(colaParser);
    }

    public void setColaParser(LinkedBlockingQueue<String> colaParser) {
        this.colaParser = colaParser;
    }

}

