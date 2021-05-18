package parsers;
import Processor.Data;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class Musimundo implements Runnable
{
	
    static ArrayList<String> arrayCategorias = new ArrayList<String>();
    public void run()
    {

	{
		
	try {
		obtenerCategorias();
                obtenerArticulos();
                Data.getInstance().setMusi(true);
	} catch (IOException e) {
		// TODO Auto-generated catch block
                System.out.println("Musi: "+e);
                Data.getInstance().setMusi(true);
		e.printStackTrace();
	}
	//System.out.println(arrayCategorias.toString());
	
	System.out.println("Musimundo Finished");

	}
}


	public static void obtenerCategorias() throws IOException {
	String url = "https://www.musimundo.com/";
	String cate = null;
	Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
	.referrer("http://www.google.com")
	.get();
	Elements content = doc.getElementsByClass("mus-link1");
	for (Element categoria : content) {
		if(categoria.attr("href").contains("/c/") && !categoria.attr("href").contains("musica") && !categoria.attr("href").contains("peliculas") ) {
				cate = "https://www.musimundo.com" + categoria.attr("href");
				//System.out.println(cate);
				Document doc2 = Jsoup.connect(cate).userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
						.referrer("http://www.google.com")
						.get();
				String[] cantArticulos = doc2.getElementsByClass("mus-results-title").first().getElementsByTag("p").text().split(" "); //1 desde 3 hasta
	//System.out.println(cantArticulos[3]);
					if (!arrayCategorias.contains(cate + "?q=%3Arelevance&page=0")) {
						arrayCategorias.add(cate + "?q=%3Arelevance&page=0");
						int j= 0;
						for (int i = 21; i< Integer.parseInt(cantArticulos[3]) ; i=i + 21) {
							j++;
							arrayCategorias.add(cate + "?q=%3Arelevance&page=" + j);
	
						}
					}
		}
	}

	}
	
	public static void obtenerArticulos() throws IOException {

		Document doc = null;

		for (int i = 0; i < arrayCategorias.size(); i++) {
		doc = Jsoup.connect(arrayCategorias.get(i)).userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
		.referrer("http://www.google.com")
		.timeout(300000)
                .maxBodySize(0) 
		.get();

		Elements productos = doc.getElementsByClass("mus-product-box-out");

		for (Element producto : productos) {
			String id = producto.getElementsByAttributeValue("name", "productCodePost").attr("value");
			String link = "https://www.musimundo.com" + producto.getElementsByTag("a").attr("href");
			String titulo = producto.getElementsByClass("mus-pro-name").text();
			String precio = producto.getElementsByClass("mus-pro-price-number").first().text().replaceAll("\\$","");
			//System.out.println(id + "," + titulo + "," + precio + "," + link);
                        String item = String.format("('MM_%s','182',\"%s\",\"%s\",'%s')",id,titulo.replaceAll("\"", ""),link,precio.replace(".","").replace(",","."));
                        Data.getInstance().setColaParser(item);
                        //System.out.println(item);
		//if (!producto.text().contains("<") && !producto.text().contains("Subtotal") && producto.text().toString().length() < 1000) {
		//String precio = producto.getElementsByClass("price online").text();
		//String id = producto.getElementsByAttribute("data-product-id").attr("data-product-id");
		//String link = producto.getElementsByClass("productClicked").attr("href");
		//System.out.println("Musimundo_"+id + ",Musimundo," + producto.getElementsByClass("name productClicked").text() + "," + link + "," + precio.substring(precio.indexOf(" ") + 1));
		//String item = String.format("('Musimundo_%s','182',\"%s\",\"%s\",'%s')",id,producto.getElementsByClass("name productClicked").text().replaceAll("\"", ""),link,precio.substring(precio.indexOf(" ") + 1).replaceAll(",","."));
		//Data.getInstance().setColaParser(item);
		//}
		}
		}
		}
	}
