/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.crawler;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletContext;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import thucnh.thread.BaseThread;
import static thucnh.utils.AppConstant.*;
/**
 *
 * @author HP
 */
public class BaseCrawler extends BaseThread{

    protected ServletContext context;

    public BaseCrawler(ServletContext context) {
        this.context = context;
    }

    public ServletContext getContext() {
        return context;
    }

    protected BufferedReader getBufferedReaderForURL(String urlString) throws MalformedURLException, IOException {

        URL url = new URL(urlString);
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
        URLConnection connection = url.openConnection();
        connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
        connection.setReadTimeout(TIME_OUT);
        connection.setConnectTimeout(TIME_OUT);
        InputStream is = connection.getInputStream();
        if (is == null) {
            return null;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        return reader;
    }

    protected XMLEventReader parseStringToXMLEventReader(String xmlSection) throws UnsupportedEncodingException, XMLStreamException {
        byte[] byteArray = xmlSection.getBytes("UTF-8");
        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
        XMLInputFactory factory = XMLInputFactory.newFactory();
        XMLEventReader reader = factory.createXMLEventReader(inputStream);

        return reader;
    }

}