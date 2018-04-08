package com.lxc.spider;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;

public class JsoupDemo {

	public static void main(String[] args) throws IOException, SAXException {
		test2();
	}
	public static void test2() throws IOException {
		HashMap<String,String> hashMap = new HashMap<String,String>();
		hashMap.put("BAIDUID", "99314DD30A8AA3C91A73B8CE23AC9816:FG=1");
		hashMap.put("BAIDUID", "D4968CF375886FAC7C6B68C2282FF346:FG=1");
		hashMap.put("BDORZ", "B490B5EBF6F3CD402E515D22BCDA1598");
		hashMap.put("BID", "99314DD30A8AA3C91A73B8CE23AC9816:FG=1");
		hashMap.put("BIDUPSID", "8204643C2FE90D93C4FC87D0310E3AA9");
		hashMap.put("FLASHID", "D4968CF375886FAC7C6B68C2282FF346:FG=1");
		hashMap.put("H_PS_PSSID", "1449_26082_21112_18559_26105");
		hashMap.put("PSINO", "2");
		hashMap.put("PSTM", "1522756829");
		hashMap.put("__bsi", "15346816504042017644_00_6_R_R_4_0303_c02f_Y");
		hashMap.put("__bsi", "12980937157626900793_00_14_N_N_8_0303_c02f_Y");
		hashMap.put("famous_banner", "%7B%7D");
		hashMap.put("ft", "1");
		hashMap.put("hword", "7");
		hashMap.put("hz", "0");
		hashMap.put("tnwhiteft", "XzFYUBclcMPGIANCmytknWnBQaFYTzclnHRzn104P104rgY");
		hashMap.put("v_pg", "normal");
		Connection connect = Jsoup.connect("https://www.hao123.com")
				.cookies(hashMap)
				.referrer("https://www.hao123.com/")
				.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36");
		Document document = connect.get();
		Elements select = document.select("a[href]");
		File file=new File("f:/out/hao123.html");
		FileWriter fw=new FileWriter(file);
		for (int i = 0; i < select.size(); i++) {
			Element element = select.get(i);
			fw.write(element.text());
			fw.write(element.getElementsByAttribute("href").toString());
			fw.write("\n");
			fw.flush();
		}
		fw.close();
	}
	public static void test(String url) throws IOException {
		Connection connect = Jsoup.connect("https://www.hao123.com");
		Document document = connect.get();
		Elements select = document.select("a[href]");
		File file=new File("f:/out/hao123.html");
		FileWriter fw=new FileWriter(file);
		for (int i = 0; i < select.size(); i++) {
			Element element = select.get(i);
			fw.write(element.text());
			fw.write(element.getElementsByAttribute("href").toString());
			fw.write("\n");
			fw.flush();
		}
		fw.close();
	}
}
