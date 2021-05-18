/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parsers;
import Processor.Data;
import org.json.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


/**
 *
 * @author Mauro
 */
public class Markova  implements Runnable
{

    public void run()
   {
	try {
            
            Document doc;
            int j = 1;
            boolean flag_end = false;
            
                do{
                    String url = String.format("https://markova.com/api/items?page_number=%d",j);
                    doc = Jsoup.connect(url)
                            .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                            .referrer("http://www.google.com")
                            .header("Accept", "text/javascript")
                            .timeout(180000)
                            .ignoreContentType(true)
                            .get();
                    
                    JSONObject obj = new JSONObject(doc.getElementsByTag("body").text());
                    JSONArray arr = obj.getJSONArray("items");
                    //System.out.println(arr.toString());
                    
                    if(arr.length() == 0){
                           System.out.println("Vacio");
                           flag_end=true;
                        }else{
                            for (int i = 0; i < arr.length(); i++)
                            {
                                int id = arr.getJSONObject(i).getInt("variant_id");
                                String articulo = arr.getJSONObject(i).getString("title").replaceAll("\"", "");
                                String price = String.valueOf(arr.getJSONObject(i).getJSONObject("variant").getDouble("non_formatted_special_price"));
                                String link = arr.getJSONObject(i).getString("slug").replaceAll("\"", "");
                               
                               if(price.equals("0.0")){
                               price = String.valueOf(arr.getJSONObject(i).getJSONObject("variant").getDouble("non_formatted_price"));
                               }
                               
                               if(!price.isEmpty()){
                                   String item = String.format("('MK_%s','318',\"%s\",\"https://markova.com/productos/%s\",'%s')",id,articulo,link,price);
                                   //System.out.println(item);
                                   Data.getInstance().setColaParser(item);
                               }
                               
                            }  
                        }
                    j++;
                }while(!flag_end);

            System.out.println("MK Finished");
            Data.getInstance().setMK(true);
            
        } catch (Exception ex) {
            Data.getInstance().setMK(true);
            System.out.println(ex);
            System.out.println("Err.MK Finished");
        }
        
    }
}