/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.utils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.util.JAXBSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.SAXException;
import thucnh.dto.JobDto;
import thucnh.dto.PdfJobsObj;
import thucnh.entity.TblJob;
import thucnh.entity.TblSkill;

/**
 *
 * @author HP
 */
public class JAXBUtils {

    public static boolean validateJobXml(String filePath, TblJob job) {
        try {
            synchronized (job) {
                JAXBContext context = JAXBContext.newInstance(TblJob.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                Marshaller marshaller = context.createMarshaller();

                SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                Schema schema = factory.newSchema(new File(filePath));
                XmlValidatorHandler handler = new XmlValidatorHandler();

                unmarshaller.setSchema(schema);
                unmarshaller.setEventHandler(handler);

                StringWriter writer = new StringWriter();
                marshaller.marshal(job, writer);
//                String xml = writer.toString();

                marshaller.setEventHandler(handler);
                Validator validator = schema.newValidator();
                validator.validate(new JAXBSource(marshaller, job));
                return true;
            }
        } catch (JAXBException ex) {
            return false;
        } catch (SAXException ex) {
            return false;
        } catch (IOException ex) {
            return false;
        }
    }

    public static boolean validateSkillXml(String filePath, TblSkill skill) {
        try {
            synchronized (skill) {
                JAXBContext context = JAXBContext.newInstance(TblSkill.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                Marshaller marshaller = context.createMarshaller();

                SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                Schema schema = factory.newSchema(new File(filePath));
                XmlValidatorHandler handler = new XmlValidatorHandler();

                unmarshaller.setSchema(schema);
                unmarshaller.setEventHandler(handler);

                StringWriter writer = new StringWriter();
                marshaller.marshal(skill, writer);
//                String xml = writer.toString();

                marshaller.setEventHandler(handler);
                Validator validator = schema.newValidator();
                validator.validate(new JAXBSource(marshaller, skill));
                return true;
            }
        } catch (JAXBException ex) {
            return false;
        } catch (SAXException ex) {
            return false;
        } catch (IOException ex) {
            return false;
        }
    }

    public static void createXMLString(PdfJobsObj dto, String realPath) {
        try {
            JAXBContext ctx = JAXBContext.newInstance(PdfJobsObj.class);
            Marshaller mar = ctx.createMarshaller();
            mar.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            mar.marshal(dto, new File(realPath + AppConstant.JAXB_XML_FOR_PDF));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
