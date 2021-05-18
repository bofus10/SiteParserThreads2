/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parsers;

import Processor.Data;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.util.Iterator;
import java.util.LinkedHashSet;
import org.json.*;


/**
 *
 * @author Mauro
 */
public class LOGGHW implements Runnable
{   
    
    public  void run()
    {
	try {
            
            Document doc;
            String j = "";
            
      
                    String url = String.format("https://www.logg.com.ar/public/articulos/?sinstock=1");
                    doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                            .referrer("http://www.google.com")
                            .timeout(180000)
                            .ignoreContentType(true)
                            .get();
                    //System.out.println(doc);
                   
                    //System.out.println(doc.getElementsByTag("body").text().replaceAll("^\\[", "").replaceAll("\\]$", ""));
                    //JSONObject obj = new JSONObject(doc.getElementsByTag("body").text());
                    JSONArray arr = new JSONArray(doc.getElementsByTag("body").text());
                    
                    //System.out.println(arr.toString());
                    
                    if(arr.length() == 0){
                           System.out.println("LOGG Hardware Vacio");
                        }else{
                            for (int i = 0; i < arr.length(); i++)
                            {
                                int id = arr.getJSONObject(i).getInt("id");
                                String articulo = arr.getJSONObject(i).getString("webname").replaceAll("\"", "");
                                String price = String.valueOf(arr.getJSONObject(i).getInt("precioefectivo")).replace(".", "").replace(",", ".");
                                String link = arr.getJSONObject(i).getString("href").replaceAll("\"", "");
                               
                               //System.out.println("id: "+id + " articulo "+ articulo + " price" + price + "link" + link);
                               if(!price.isEmpty()){
                               String item = String.format("('LOG_%s','316',\"%s\",\"https://www.logg.com.ar%s\",'%s')",id,articulo,link,price);
                               //System.out.println(item);
                               Data.getInstance().setColaParser(item);
                               }
                            }  
                        }
            

            System.out.println("LOGG Finished");
            Data.getInstance().setLOGG(true);
            
        } catch (Exception ex) {
            Data.getInstance().setLOGG(true);
            System.out.println(ex);
            System.out.println("Err.LOGG Finished");
        }
        
    }


}
