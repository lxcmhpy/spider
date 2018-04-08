package com.lxc.spider;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DownloadNetFile {
	final static String storePath="f:/out/";
	public static void main(String[] args) throws Exception {
		String url="http://cdn.npm.taobao.org/dist/node/v8.9.3/node-v8.9.3-x64.msi";
//		testDebugDownload(url,storePath);
		download("http://img1.mm131.me/pic/3454/0.jpg");
	}

	public static String download(String uri) throws IOException {
		URL url = new URL(uri);
		String file = url.getFile();
		String name = file.substring(file.lastIndexOf("/") + 1, file.length());
		String storePath = "f:/out/" + name;
		// InputStream openStream = url.openStream();
		System.err.println(storePath);
		URLConnection connection = url.openConnection();
		InputStream in = connection.getInputStream();
		FileOutputStream out = new FileOutputStream(storePath);
		DataOutputStream dataout=new DataOutputStream(out);
		while (in.read() != -1) {
			int read = in.read();
//			out.write(read);
			dataout.write(read);
		}
		in.close();
		out.close();

		return file;
	}

	/**
	 * �ϵ�����
	 * @throws InterruptedException 
	 */
	public static void testDebugDownload(String uri,String storePath) throws IOException, InterruptedException{
		//����URL����
		URL url = new URL(uri);
		//ʹ��url��ȡHttpURLConnection����
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		//�ͻ��˵�����ʽ
		conn.setRequestMethod("GET");
		//�Ѿ����ص��ֽ���
		long alreadySize = 0;
		String files = url.getFile();
		String name = files.substring(files.lastIndexOf("/") + 1, files.length());
		String filePath = storePath + name;
		//���ļ�д��d:\kiss.avi��
		File file = new File(filePath);
		//������ڣ�˵��ԭ�����ع�����������û��������
		if(file.exists()){
			//����ļ����ڣ��ͻ�ȡ��ǰ�ļ��Ĵ�С
			alreadySize = file.length();
		}
		/**
		 * Rangeͷ���������ʵ���һ�����߶���ӷ�Χ��
		 * 		����:	��ʾͷ500���ֽڣ�bytes=0-499 ����
		 *	    	 	��ʾ�ڶ���500�ֽڣ�bytes=500-999 ����
		 *     		 	��ʾ���500���ֽڣ�bytes=-500 ����
		 *	     	 	��ʾ500�ֽ��Ժ�ķ�Χ��bytes=500- ����
		 *	     	 	��һ�������һ���ֽڣ�bytes=0-0,-1 ����
		 *           	ͬʱָ��������Χ��bytes=500-600,601-999 ����
	     * ���Ƿ��������Ժ��Դ�����ͷ�����������GET����Range����ͷ����Ӧ����״̬��206��PartialContent�����ض�������200 ��OK����	
		 */
		conn.addRequestProperty("range", "bytes=" + alreadySize + "-");
		conn.connect();
		
		//206,һ���ʾ�ϵ�����
		//��ȡ������������״̬��
		int code = conn.getResponseCode();
		// �����Ӧ�ɹ�����Ϊʹ����range����ͷ����ô��Ӧ�ɹ���״̬��Ϊ206��������200
		if(code == 206){
			//��ȡδ���ص��ļ��Ĵ�С
			// ������������ȡ��Ӧ���ĵĴ�С������Ϊ������range����ͷ����ô����������صľ���ʣ��Ĵ�С
			long unfinishedSize = conn.getContentLength();
			//�ļ��Ĵ�С
			long size = alreadySize + unfinishedSize;
			
			//��ȡ������
			InputStream in = conn.getInputStream();
			//��ȡ�������,����һ��Ŀ���ļ�������2��ʾ��ԭ�����ļ���׷��
			OutputStream out = new BufferedOutputStream(new FileOutputStream(file,true));
			
			//��ʼ����
			byte[] buff = new byte[2048];
			int len;
			StringBuilder sb = new StringBuilder();
			while((len = in.read(buff)) != -1){
				out.write(buff, 0, len);
				//�����ص��ۼӵ�alreadSize��
				alreadySize += len;
				//���ؽ���
				System.out.printf("%.2f%%\n", alreadySize * 1.0 / size * 100);
				//�����ļ���С���Կ��õ�����ô��������ʹ������
				Thread.sleep(2);
			}
			out.close();
			System.out.println("������ɣ�����");
		}else{
			System.out.println("����ʧ�ܣ�����");
		}
		
		//�Ͽ�����
		conn.disconnect();
	}
}
