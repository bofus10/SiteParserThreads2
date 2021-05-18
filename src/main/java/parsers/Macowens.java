package parsers;
import Processor.Data;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class Macowens implements Runnable
{

    public void run()
    {
	try {
            
            Document doc;
            int j = 1;
            boolean flag_end = false;
            
                do{
                    String url = String.format("https://www.macowens.com.ar/catalogsearch/result/index/?p=%d&q=%%",j);
                    doc = Jsoup.connect(url)
                            .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                            .referrer("http://www.google.com")
                            .header("Accept", "text/javascript")
                            .timeout(180000)
                            .ignoreContentType(true)
                            .get();
                    
                    Elements productos = doc.getElementsByClass("item");

                    if(doc.getElementsByClass("next i-next").text().isEmpty()){
                        flag_end = true;                       
                    }else{
                        for (Element producto : productos) {
                        String name = producto.getElementsByClass("product-name").text();
                        String id = producto.getElementsByAttribute("data-product-id").attr("data-product-id");
                        String link = producto.getElementsByAttribute("href").first().attr("href");
                        String precio = "";
                            if(!producto.getElementsByClass("special-price").text().isEmpty()){
                                precio = producto.getElementsByClass("special-price").text().replaceAll("\\$ ", "").replace(".", "").replace(",", ".").replaceAll("[^\\.0123456789]","");
                            }else{
                                precio = producto.getElementsByClass("regular-price").text().replaceAll("\\$ ", "").replace(".", "").replace(",", ".").replaceAll("[^\\.0123456789]","");
                            }
                            /*if(id.isEmpty()){
                              System.out.println(producto.getElementsByClass("regular-price").attr("id").replaceAll(".+-", ""));
                              System.out.println(producto.getElementsContainingText("Producto Agotado").text());
                            }*/
                            
                            if(!precio.isEmpty() || !id.isEmpty()){
                                //System.out.println(id + "," + name + "," + precio + "," + link);
                                String item = String.format("('MW_%s','279',\"%s\",\"%s\",'%s')",id,name.replaceAll("\"", ""),link,precio);
                                Data.getInstance().setColaParser(item);
                                //System.out.println(item);
                            }
                
                        } 
                    }
     
                    j++;
                }while(!flag_end);

            System.out.println("Finalizo Macowens");
            Data.getInstance().setMacowens(true);
                 
        } catch (Exception ex) {
            System.out.println(ex);
            System.out.println("ERR Finalizo Macowens"+ " " +ex);
            Data.getInstance().setMacowens(true);
        }
        
    }
}