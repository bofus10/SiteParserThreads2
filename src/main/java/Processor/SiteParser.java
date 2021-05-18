/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Processor;



import java.io.IOException;
import java.sql.Connection;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.logging.Logger;
import java.util.Date;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import parsers.Armytech;

import parsers.Easy;
import parsers.Garbarino;
import parsers.Macowens;
import parsers.Megatone;
import parsers.Musimundo;
import parsers.NetShoes;
import parsers.Rodo;
import parsers.CasadelAudio;
import parsers.Compragamer;
import parsers.Gezatek;
import parsers.LOGGHW;
import parsers.Markova;
import parsers.TecnoStore;



public class SiteParser {

    static ExecutorService executorPool = Executors.newFixedThreadPool(5);
    static Logger vendors = Logger.getLogger("MyLog");
    static SimpleFormatter formatter = new SimpleFormatter();
    static int count = 0;
    static int flag = 1;
    static int dbcount = 0;

   public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, InterruptedException {

       //Codigo
       DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
       Data.getInstance();

		
	while(true){
            if(flag == 1){
                //getFalabellaCategories();
                //getCotoCategories();
                //submit work to the thread pool
                executorPool.execute(new DBWriter());
                    
                    executorPool.execute(new NetShoes());
                    executorPool.execute(new LOGGHW());
                    executorPool.execute(new Compragamer());
                    executorPool.execute(new Armytech());
                    executorPool.execute(new Easy());
                    executorPool.execute(new Gezatek());
                    executorPool.execute(new Markova());
                    executorPool.execute(new Musimundo());
                    executorPool.execute(new CasadelAudio());
                    //executorPool.execute(new Rodo());
                    //executorPool.execute(new Megatone());  
                    executorPool.execute(new Garbarino());
                    executorPool.execute(new TecnoStore());
                    executorPool.execute(new Macowens());
                    
                
                flag = 0;
                Date date = new Date();
                System.out.println(dateFormat.format(date) +" Todo Cargado");
            }
            else if(Data.getInstance().isDbFlag() && getParsesStatus()){
                    System.out.println("Realoading Pool");
                    
                if(LocalDateTime.now().getHour() == 0 && LocalDateTime.now().getMinute() < 15){ 
                    System.out.println("Waiting for BKP to finish");
                    
                }else{
                    
                    Data.getInstance().setDbFlag(false);
                    Data.getInstance().setMacowens(false);
                    Data.getInstance().setMusi(false);
                    //Data.getInstance().setMega(false);
                    Data.getInstance().setGarba(false);
                    Data.getInstance().setEasy(false);
                    //Data.getInstance().setRodo(false);
                    Data.getInstance().setTecnostore(false);
                    Data.getInstance().setNetshoes(false);
                    Data.getInstance().setCasadelAudio(false);
                    Data.getInstance().setLOGG(false);
                    Data.getInstance().setCM(false);
                    Data.getInstance().setGK(false);
                    Data.getInstance().setAT(false);
                    Data.getInstance().setMK(false);
                    flag = 1;
                        
                    }      
            }
            
            try {
                Thread.sleep(3000);
                WatchDogMonitor();
                //System.out.println("Parsers: "+getParsesStatus()+" Fala: "+Data.getCategorias().isEmpty() + "Coto: "+Data.getCoto_categorias().isEmpty());

                } catch (InterruptedException e) {
                    System.out.println("MLParser:93"+e);
                }
            

        }
        
        
    }
   
   public static void getCotoCategories(){
       Data.getCoto_categorias().add("https://www.cotodigital3.com.ar/sitios/cdigi/browse/catalogo-textil/_/N-l8joi7");
       Data.getCoto_categorias().add("https://www.cotodigital3.com.ar/sitios/cdigi/browse/catalogo-hogar/_/N-qa34ar");
       Data.getCoto_categorias().add("https://www.cotodigital3.com.ar/sitios/cdigi/browse/catalogo-aire-libre/_/N-w7wnle");
       Data.getCoto_categorias().add("https://www.cotodigital3.com.ar/sitios/cdigi/browse/catalogo-electro/_/N-1ngpk59");
   }
   
   public static void getFalabellaCategories() throws IOException{
            Document doc;
            
            String url = String.format("https://www.falabella.com.ar/falabella-ar/");
            doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                    .referrer("http://www.google.com")
                    .get();
            
            Elements titles = doc.getElementsByClass("fb-masthead__child-links__item");

            for (Element title : titles) {
                String MainCategory = title.getElementsByClass("fb-masthead__child-links__item__link js-masthead__child-links__item__link").attr("href");
                String CategoryURL = String.format("https://www.falabella.com.ar%s?isPLP=1", MainCategory);
                Data.getCategorias().add(CategoryURL);
                //System.out.println(CategoryURL);
            }
            
   }
   
   public static void Write2Log(String msg, String file) throws IOException{
           
          
        if(Data.getInstance().isLogFlag()){
           switch(file){
               case "vendors":
                             vendors.info(msg);
                             //fh_vendors.close();
               break;
               /*
               case "sql":
                          sql.info(msg);
                          //fh.close();
               break;
               
               case "offers":
                             offers.info(msg);
                             //fh.close();
               break;*/
           }
           
        }else{
            
            Data.getInstance().setLogFlag(true);
            FileHandler fh_vendors = new FileHandler("./log/sitios2.log"); 
            //FileHandler fh_sql = new FileHandler("./log/sql.log"); 
            //FileHandler fh_offer = new FileHandler("./log/offers.log");
            
            vendors.addHandler(fh_vendors);
            vendors.setUseParentHandlers(false);
            fh_vendors.setFormatter(formatter);
           /* sql.addHandler(fh_sql);
            sql.setUseParentHandlers(false);
            fh_sql.setFormatter(formatter);
            offers.addHandler(fh_offer);
            offers.setUseParentHandlers(false);
            fh_offer.setFormatter(formatter);*/
            
        }  
                      
   }
   
//   public static void getVendorID_Map() throws SQLException{
//       Connection connThread = DriverManager.getConnection(Data.getInstance().getDB_URL(),Data.getInstance().getUSER(),Data.getInstance().getPASS()); 
//       //Connection connThread = DriverManager.getConnection("jdbc:mysql://10.11.99.180/parser?autoReconnect=true&useSSL=false","joaco","AvanzitDB@11"); 
//       connThread.setAutoCommit(false);
//       Statement stmntThreadVendor = connThread.createStatement();
//       String sql = "SELECT * FROM vendors_site;";
//       
//       ResultSet rs = stmntThreadVendor.executeQuery(sql);
//       
//       while(rs.next()) {
//        Data.getVendorID().put(String.valueOf(rs.getInt("vendors_id")), rs.getString("name"));
//        //tiendas.add(rs.getString("name"));
//        Data.getCategorias().add(rs.getString("name"));
//       }
//       
//       stmntThreadVendor.close();
//
//      
//   }
   
   public static boolean getParsesStatus(){
       //Data.getInstance().isMusi() && 
       if(/*Data.getInstance().isMega() &&*/ Data.getInstance().isGarba() && /*Data.getInstance().isEasy() && */
          /*Data.getInstance().isRodo() &&*/ Data.getInstance().isMacowens() && Data.getInstance().isMusi() &&
          Data.getInstance().isNetshoes() && Data.getInstance().isTecnostore() && Data.getInstance().isCasadelAudio() 
               && Data.getInstance().isLOGG() && Data.getInstance().isCM() && Data.getInstance().isGK() && Data.getInstance().isAT() && Data.getInstance().isMK()
         ){
           System.out.println("All parsers done, reloading");
           return true;
       }else{
           /*
           if(!Data.getInstance().isFravega()){
               System.out.println("Fravega");
           }
           if(!Data.getInstance().isMusi()){
               System.out.println("Musi");
           }
           if(!Data.getInstance().isMega()){
               System.out.println("Mega");
           }
           if(!Data.getInstance().isGarba()){
               System.out.println("Garba");
           }
           if(!Data.getInstance().isEasy()){
               System.out.println("Easy");
           }*/
           
           return false;
       }
   }
   
   public static void WatchDogMonitor(){
       
       if(Data.getInstance().isLockFlag()){
           
               System.out.println("Lock Detected. Restarting...");
               Data.getInstance().setLockFlag(false);
               executorPool.shutdownNow();
                System.exit(1);

       }
       
       if(Data.getInstance().isDbFlag()){
           dbcount++;
           if(dbcount > 150){
               dbcount=0;
               System.out.println("Lock Detected. Restarting...");
               Data.getInstance().setDbFlag(false);
               executorPool.shutdownNow();
               System.exit(1);
           }
       }
   }
}
