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
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import thucnh.dao.JobDao;
import thucnh.dto.JobDto;
import thucnh.dto.PdfJobsObj;
import thucnh.utils.AppConstant;
import thucnh.utils.JAXBUtils;

/**
 *
 * @author HP
 */
public class PdfServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String realPath = request.getServletContext().getRealPath("/");
            String salary = request.getParameter("txtSalary");
            String expSkillHash = request.getParameter("txtExpSkillHash");

            JobDao dao = JobDao.getInstance();
//            List<JobDto> top10Jobs = dao.getTopTenRelatedJob(
//                    Double.parseDouble(salary),
//                    Integer.parseInt(expSkillHash));
            List<JobDto> top10Jobs = dao.getTopTenRelatedJob(120000000.0, 293227331);
            PdfJobsObj pdfObj = new PdfJobsObj(top10Jobs);
            JAXBUtils.createXMLString(pdfObj, realPath);
            String xslPath = realPath + AppConstant.JAXB_XSL_FOR_PDF;
            String xmlPath = realPath + AppConstant.JAXB_XML_FOR_PDF;
            String foPath = realPath + AppConstant.JAXB_FO_FOR_PDF;
            
            methodTrAX(xslPath, xmlPath, foPath, realPath);
            File file = new File(foPath);
            FileInputStream input = new FileInputStream(file);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            response.setContentType("application/pdf");

            FopFactory ff = FopFactory.newInstance(new File(AppConstant.FOP_FAC_PATH));
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
