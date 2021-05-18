package parsers;
import Processor.Data;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class Rodo implements Runnable
{
	
    static ArrayList<String> arrayCategorias = new ArrayList<String>();
    public void run()
    {
		
	try {
		obtenerCategorias();
                boolean flag_end = false;
                Document doc = null;
                
		for (int i = 0; i < arrayCategorias.size(); i++) {
                    int j = 1;
                    String Last_Id = "";
                    do{
                        String url = String.format("%s%d",arrayCategorias.get(i),j);
                        doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                        .referrer("http://www.google.com")
                        .timeout(300000)
                        .get();

                        Elements productos = doc.getElementsByClass("item last");
                        
                        if(j == 1){
                           Last_Id = productos.get(0).getElementsByTag("img").attr("id").split("-")[3]; 
                            //System.out.println("Last_Id: " + Last_Id);
                        }else{
                            if(Last_Id.equals(productos.get(0).getElementsByTag("img").attr("id").split("-")[3])){ 
                                //System.out.println("Same ID. Last: "+Last_Id+" Actual: "+productos.get(0).getElementsByTag("img").attr("id").split("-")[3]);
                                flag_end = true;
                            }else{
                               //System.out.println("Last: "+Last_Id+" Actual: "+productos.get(0).getElementsByTag("img").attr("id").split("-")[3]);
                               Last_Id = productos.get(0).getElementsByTag("img").attr("id").split("-")[3];
                            }
                        }
                            if(!flag_end){
                                for (Element producto : productos) {
                                        String id[] = producto.getElementsByTag("img").attr("id").split("-");
                                        String link = producto.getElementsByTag("a").first().attr("href");
                                        String titulo = producto.getElementsByClass("product-name").text();
                                        
                                        String precio = "";
                                        if(producto.getElementsByClass("price").size() > 1){
                                        precio = producto.getElementsByClass("price").get(1).text().replaceAll("\\$ ","").replace(".","").replace(",",".");
                                        }else{
                                        precio = producto.getElementsByClass("price").text().replaceAll("\\$ ","").replace(".","").replace(",",".");    
                                        }
                                        //System.out.println(id[3] + "," + titulo + "," + precio + "," + link);
                                        if(!precio.isEmpty()){
                                        String item = String.format("('RD_%s','276',\"%s\",\"%s\",'%s')",id[3],titulo.replaceAll("\"", ""),link,precio);
                                        Data.getInstance().setColaParser(item);
                                        //System.out.println(item);
                                        }
                                }
                                //System.out.println(url);
                                j++;
                            }
                        
                    }while(!flag_end);
                    flag_end = false;
		}
            
            
            
                Data.getInstance().setRodo(true);
                System.out.println("Rodo Finish");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
                Data.getInstance().setRodo(true);
	}

	//System.out.println("Rodo Finished");

    }

     public static void obtenerCategorias() throws IOException {
	String url = "http://rodo.com.ar/productos.html";
	String cate = null;
	Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
	.referrer("http://www.google.com")
	.get();
	Elements content = doc.getElementsByTag("dd").first().getElementsByAttribute("href");
	for (Element categoria : content) {

				cate = categoria.attr("href");
                                arrayCategorias.add(cate + "?limit=48&p=");
				//System.out.println(cate + "?limit=48&p=");

	}

    } 
}