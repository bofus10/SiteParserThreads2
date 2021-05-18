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

import java.math.BigDecimal;

import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 *
 * @author Mauro
 */
public class NetShoes implements Runnable{
    
    public void run(){
       String url = "";
       boolean page = true, flag = true;
       Document doc =null;
       int num = 0;
       
	try {
            
            do{    
                url = String.format("https://www.stockcenter.com.ar/on/demandware.store/Sites-StockCenter-Site/default/Search-UpdateGrid?cgid=categorias&start=%d&sz=50",num);
                doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.111 Safari/537.36")
                .referrer("http://www.google.com")
                .timeout(180000)     
                .get();
                
                //System.out.println(doc.select("div[class^=product-tile]"));
                Elements products = doc.select("div[class^=product-tile]");
                
                if(doc.select("div[class^=product-tile]").toString().isEmpty()){
                    flag = false;
                    
                }else{
                
                    for(Element product : products){

                      String sku = product.select("a[class^=quickview]").attr("href").replaceAll(".+pid=(\\w+)(\\%.+|)", "$1");
                      String name = product.select("a[class^=link]").text().replaceAll("\"", "");
                      String link = product.select("a[class^=link]").attr("href").replaceAll("\"", "");
                      String price = product.select("div[class^=price]").text().replaceAll("\\$(\\d+\\.\\d+).+", "$1").replaceAll("\\.", "").replaceAll("[^\\.0123456789]","");

                      //System.out.println("Name: "+name+" link: "+link+" sku: "+sku+" price: "+price);
                      if(!price.isEmpty()){
                      String item = String.format("('NetS_%s','262',\"%s\",\"https://www.stockcenter.com.ar%s\",'%s')",sku,name,link,price);
                      //System.out.println(item);
                      Data.getInstance().setColaParser(item); 
                      }

                   } 
                
               }
                
             num = num + 50;
            }while(flag); 
            
            System.out.println("Netshoes Finished");
            Data.getInstance().setNetshoes(true);
        } catch (Exception ex) {
            System.out.println(ex);
                System.out.println("Netshoes Finished with Error");
                Data.getInstance().setNetshoes(true);
            
        }
    }
}