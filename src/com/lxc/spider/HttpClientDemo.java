package com.lxc.spider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HttpClientDemo {

	public static void main(String[] args) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("https://www.liepin.com/zhaopin/?d_sfrom=search_fp_nvbar&init=1");
		try {
			CloseableHttpResponse execute = httpclient.execute(httpGet);
			HttpEntity entity = execute.getEntity();
			Header contentEncoding = entity.getContentEncoding();
			long contentLength = entity.getContentLength();
			Header contentType = entity.getContentType();
			InputStream content = entity.getContent();
//			System.err.println("contentEncoding	:"+contentEncoding);
//			System.err.println("contentType	:"+contentType);
//			System.err.println("contentLength	:"+contentLength);
			File file=new File("f:/out/test3.html");
			FileOutputStream fos=new FileOutputStream(file);
//			byte[] b=new byte[4096];
//			int read = 0;
//			while (read!=-1) {
//				read=content.read(b);
//				fos.write(b);
//			}
//			fos.close();
			Document document = Jsoup.parse(content, "utf-8", "https://www.liepin.com/");
			Elements select = document.select("img");
			FileWriter fw=new FileWriter(file);
			for (int i = 0; i < select.size(); i++) {
				Element element = select.get(i);
				fw.write(element.text());
				fw.write(element.getElementsByAttribute("src").toString());
				fw.write("\n");
				fw.flush();
			}
			fw.close();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
