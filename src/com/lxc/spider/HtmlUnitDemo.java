package com.lxc.spider;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;


public class HtmlUnitDemo {

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		WebClient webClient=new WebClient();
		webClient.getOptions().setJavaScriptEnabled(false);
		webClient.getOptions().setCssEnabled(false);
		try {
			Page page = webClient.getPage("https://www.liepin.com/zhaopin/?d_sfrom=search_fp_nvbar&init=1");
			WebResponse webResponse = page.getWebResponse();
			String contentAsString = webResponse.getContentAsString();
			System.err.println(contentAsString);
			Document document = Jsoup.parse(contentAsString);
			Elements select = document.select("img");
			File file=new File("f:/out/test4.html");
			FileWriter fw=new FileWriter(file);
			for (int i = 0; i < select.size(); i++) {
				Element element = select.get(i);
				fw.write(element.text());
				fw.write(element.getElementsByAttribute("src").toString());
				fw.write("\n");
				fw.flush();
			}
			fw.close();
		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
