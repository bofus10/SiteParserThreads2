/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parsers;

import Processor.Data;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 *
 * @author Mauro
 */
public class CasadelAudio implements Runnable
{
    public void run(){
        
        try {
            Document doc;
            boolean flag_page = false;
            int i = 0, j = 1;
        
                do{
                        //String url = String.format("https://diaonline.supermercadosdia.com.ar/%s%d",categorias[i],j);
                        String url = String.format("https://www.casadelaudio.com/Item/Search/?page=%d&id=0&recsPerPage=30&order=Alpha&sort=True&itemtype=Product&term=&getFilterData=True&filters=&fields=Name",j);
                        doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                                .referrer("http://www.google.com")
                                .timeout(180000)
                                .maxBodySize(0) 
                                .get();
                        //System.out.println(url);
                        //System.out.println(doc);
                        //System.out.println(doc.getElementsContainingText("No se han encontrado resultados").text());
                           

                            if(!doc.getElementsContainingText("No se han encontrado resultados").text().isEmpty()){
                                //System.out.println(doc.getElementsContainingText("No encontramos lo que estás buscando"));
                                System.out.println("Finalizo");
                                flag_page=true;
                            }else{
                                Elements products = doc.getElementsByClass("box_data");
                                    for(Element product : products){

                                         //System.out.println(product);
                                         String sku = product.getElementsByAttribute("item-id").attr("item-id");
                                         String name = product.getElementsByClass("onbody").get(0).text().replaceAll("\"", "");
                                         String link = product.getElementsByClass("onbody").get(0).attr("href");
                                         String best_price = product.getElementsByClass("hidden-xs price").text().replaceAll(".+\\$ ", "").replace(".", "").replace(",", ".");
      
                                         if(!best_price.isEmpty()){
                                            //System.out.println(link+" "+name+";"+best_price);
                                            String item = String.format("('CDA_%s','314',\"%s\",\"https://www.casadelaudio.com%s\",'%s')",sku,name,link,best_price);
                                            //System.out.println(item);
                                            Data.getInstance().setColaParser(item);
                                         }
                                    }

                                j++;  
                            }

                }while(!flag_page);

            Data.getInstance().setCasadelAudio(true);
            System.out.println("CasadelAudio Finalizado");   
            
        } catch (Exception ex) {
            System.out.println(ex);
            System.out.println("Err.CasadelAudio Finalizado"); 
            Data.getInstance().setCasadelAudio(true);
            
        }
        
    }

}
