/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author HP
 */
public class TrAXUtils {

    public static TransformerFactory tf = null;

    private static TransformerFactory getTransformerFactory() {
        if (tf == null) {
            tf = TransformerFactory.newInstance();
        }
        return tf;
    }

    public static ByteArrayOutputStream transform(InputStream xmlIs, String xslPath)
            throws FileNotFoundException, TransformerConfigurationException, TransformerException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        TransformerFactory factory = TransformerFactory.newInstance();

        StreamSource source = new StreamSource(xmlIs);
        StreamSource xslSource = new StreamSource(new FileInputStream(xslPath));
        StreamResult result = new StreamResult(outputStream);

        Transformer trans = factory.newTransformer(xslSource);
        trans.transform(source, result);

        return outputStream;
    }
}
