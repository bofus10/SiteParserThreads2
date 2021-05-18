package parsers;
import Processor.Data;
import java.util.Iterator;
import java.util.LinkedHashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class Armytech implements Runnable
{
    public void run()
    {
        try {
            Document doc;
            boolean  page = true, done = false;
            LinkedHashSet<String> categorias = new LinkedHashSet<String>();
            Iterator it = null;
            String j = "", main = "https://armytech.com.ar/";

            do{

               String url = String.format(main);
               doc = Jsoup.connect(url)
               .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
               .referrer("http://www.google.com")
               .timeout(180000)
               .maxBodySize(0) 
               .get();
               
                if(page){
                    Elements asd = doc.getElementsByClass("prenav").get(0).getElementsByClass("dropdown-submenu");
                        for(Element a : asd){
                            if(!a.getElementsByClass("hidden-xs").attr("href").contains("dministracion")){
                            //System.out.println(a.getElementsByClass("hidden-xs").attr("id").replaceAll("drop", ""));
                            categorias.add(a.getElementsByClass("hidden-xs").attr("id").replaceAll("drop", ""));
                            }
                        }
                    //https://armytech.com.ar/Item/Search/?id=77&itemtype=Product
                    it = categorias.iterator();
                    j = it.next().toString();    
                    main = String.format("https://armytech.com.ar/Item/Search/?id=%s&itemtype=Product",j);
                    page = false;  
                }else{
                  //System.out.println("#################### "+main+" ######################");
                  Elements products = doc.getElementsByClass("box_data");
                  
                   for(Element product : products){
                     if(product.toString().contains("NO DISPONIBLE")){
                         //System.out.println("NO DISPONIBLE: "+product.getElementsByClass("onbody").text());
                     }else{
                         //System.out.println("DISPONIBLE");
                         //System.out.println(product.toString());
                         String sku = product.getElementsByAttribute("item-id").attr("item-id");
                         String name = product.getElementsByClass("onbody").text();
                         String link = product.getElementsByClass("onbody").attr("href");
                         String price = product.getElementsByClass("price").text().replaceAll("[^\\.0123456789]","");
                         //System.out.println("sku: "+sku+" name: "+name+" link: "+link+" price: "+price);
                         
                         String item = String.format("('AT_%s','323',\"%s\",\"https://armytech.com.ar%s\",'%s')",sku,name.replaceAll("\"", ""),link,price);
                         Data.getInstance().setColaParser(item);
                         //System.out.println(item);
                     }
   
                 }
               Thread.sleep(50);  
               j = it.next().toString();    
               main = String.format("https://armytech.com.ar/Item/Search/?id=%s&itemtype=Product",j);
            }          
                
          }while(it.hasNext());
          System.out.println("ArmyTech Finished");
          Data.getInstance().setAT(true);

       
        } catch (Exception ex) {
            System.out.println("Error ArmyTech: "+ex);
            Data.getInstance().setAT(true);
        }
        
}

}
