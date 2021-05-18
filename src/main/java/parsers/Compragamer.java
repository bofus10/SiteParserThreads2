package parsers;
import Processor.Data;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class Compragamer implements Runnable
{
    public void run()
    {
       String cate[] = {"2","5","6","62","81","19","26","27","48","49","7","8","15","16","31","34","35","36","39","58","38","17","42","47","59","64","65","66","86","83","12","13","14","18","20","21","32","37","52","67","68","69","71","72","73","74","78","89","92","100"};
       String url = "";
       boolean page = true, flag = true;
       Document doc =null;
       int num = 0;
       
	try {
            
            for (int i = 0; i < cate.length; i++) {
            
                url = String.format("https://compragamer.com/index.php?seccion=3&cate=%s&nro_max=300",cate[i]);
                doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.111 Safari/537.36")
                .referrer("http://www.google.com")
                .timeout(180000)  
                .maxBodySize(0)        
                .get();
                
                //System.out.println(doc);
                //exit(0);
                
                Elements products = doc.getElementsByClass("products__item");
                   for(Element product : products){
                       

                     String sku = product.select("button[class^=products-btns]").attr("value");
                     String name = product.getElementsByClass("products__name").text();
                     String link = product.getElementsByClass("products__name").get(0).getElementsByTag("a").attr("href").replaceAll("\\?redir=.+", "");
                     String price = product.getElementsByClass("products__price-new").text().replace(",", "").replaceAll("[^\\.0123456789]","");
                     
                     if(!price.isEmpty() && !sku.isEmpty()){
                     String item = String.format("('CM_%s','324',\"%s\",\"https://compragamer.com%s\",'%s')",sku,name.replaceAll("\"", ""),link,price);
                     Data.getInstance().setColaParser(item);
                     //System.out.println(item);
                     }
                     
                 }  
               //done = true;
               
               
                Thread.sleep(1000);
            } 
            
          System.out.println("CompraGamer Finished");
          Data.getInstance().setCM(true);
       
        } catch (Exception ex) {
            System.out.println("Error CompraGamer: "+ex);
            Data.getInstance().setCM(true);
        }
        
}

}
