package parsers;
import Processor.Data;
import java.util.Iterator;
import java.util.LinkedHashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class Gezatek implements Runnable
{
    public void run()
    {
        try {
            Document doc;

               //String url = String.format("https://compragamer.com/?seccion=3&nro_max=%d",NumMax);
               doc = Jsoup.connect("https://www.gezatek.com.ar/tienda/?busqueda=%")
               .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
               .referrer("http://www.google.com")
               .timeout(180000)
               .maxBodySize(0) 
               .get();
               
               //System.out.println(doc.getElementsByClass("col-md-4"));
               

                  Elements products = doc.getElementsByClass("col-md-4");
                   for(Element product : products){
                     
                     //System.out.println(product);
                    
                     if(product.getElementsByClass("pull-right").text().contains("Hay Stock")){
                     String sku = product.getElementsByTag("h2").get(0).getElementsByTag("a").attr("data-id");
                     String name = product.getElementsByTag("h2").get(0).getElementsByTag("a").text();
                     String link = product.getElementsByTag("h2").get(0).getElementsByTag("a").attr("href");
                     String price = product.getElementsByTag("h3").text().replaceAll("[^\\.0123456789]","");

                     String item = String.format("('GK_%s','325',\"%s\",\"https://gezatek.com.ar%s\",'%s')",sku,name.replaceAll("\"", ""),link,price);
                     Data.getInstance().setColaParser(item);
                     //System.out.println(item);
                     //Thread.sleep(100);
                     }
                 }  


          System.out.println("Gezatek Finished");
          Data.getInstance().setGK(true);
       
        } catch (Exception ex) {
            System.out.println("Error Gezatek: "+ex);
            Data.getInstance().setGK(true);
        }
        
}

}
