/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.servlet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import thienVN.Common.Constraint;
import thienVN.JaxB.House;
import thienVN.JaxB.HouseDTO;
import thienVN.JaxB.Houses;
import thienVN.JaxB.JAXBMarshall;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "PrintPDFServlet", urlPatterns = {"/PrintPDFServlet"})
public class PrintPDFServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String realPath = request.getServletContext().getRealPath("/");
            HttpSession session = request.getSession();
            ArrayList<HouseDTO> top3 = (ArrayList<HouseDTO>) session.getAttribute("TOP3");
            Houses houses = new Houses();
            for (HouseDTO dto : top3) {
                House house = new House();
                house.setImage(dto.getImage());
                house.setUrl(dto.getUrl());
                house.setName(dto.getName());
                house.setAddress(dto.getAddress());
                house.setPhone(dto.getPhone());
                house.setArea(dto.getArea());
                house.setElectricPrice(dto.getElectricPrice());
                house.setWaterPrice(dto.getWaterPrice());
                house.setRentPrice(dto.getRentPrice());
                house.setLatitude(BigDecimal.valueOf(dto.getLatitude()));
                house.setLongitude(BigDecimal.valueOf(dto.getLongitude()));
                house.setDistance(BigDecimal.valueOf(Float.parseFloat(dto.getDistance())));
                house.setPoint(BigDecimal.valueOf(dto.getPoint()));
                houses.getHouse().add(house);
            }
            JAXBMarshall.createHomeXMLFile(houses, realPath);
            String xslPath = realPath + Constraint.JAXB_XSL_HOUSE_FOR_PDF;
            String xmlPath = realPath + Constraint.JAXB_XML_HOUSE_FOR_PDF;
            String foPath = realPath + Constraint.JAXB_FO_HOUSE_FOR_PDF;
            methodTrAX(xslPath, xmlPath, foPath, realPath);
            File file = new File(foPath);
            FileInputStream input = new FileInputStream(file);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            response.setContentType("application/pdf");

            FopFactory ff = FopFactory.newInstance(new File("C:/Users/ASUS/Downloads/fop-2.4/fop/conf/fop.xconf"));
            FOUserAgent fua = ff.newFOUserAgent();

            Fop fop = ff.newFop(MimeConstants.MIME_PDF, fua, out);

            TransformerFactory tff = TransformerFactory.newInstance();
            Transformer trans = tff.newTransformer();
            trans.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            File fo = new File(foPath);
            Source src = new StreamSource(fo);
            Result result = new SAXResult(fop.getDefaultHandler());
            trans.transform(src, result);

            byte[] content = out.toByteArray();
            response.setContentLength(content.length);
            response.getOutputStream().write(content);
            response.getOutputStream().flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    private void methodTrAX(String xslPath, String xmlPath, String output, String path) throws FileNotFoundException, TransformerException {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            StreamSource xslFile = new StreamSource(xslPath);
            Transformer trans = tf.newTransformer(xslFile);

            StreamSource xmlFile = new StreamSource(xmlPath);
            StreamResult htmlFile = new StreamResult(new FileOutputStream(output));
            trans.transform(xmlFile, htmlFile);
        } catch (TransformerConfigurationException ex) {
            ex.printStackTrace();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
