/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import thucnh.crawler.MainCrawler;
import thucnh.crawler.MainProcessor;
import thucnh.dao.UserDao;
import static thucnh.utils.AppConstant.*;

/**
 *
 * @author HP
 */
public class ProcessServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String url = ADMIN;
        String btn = request.getParameter("btnAction");
        try {
            if (btn == null) {
                // do nothing
            } else if (btn.equals("Login")) {
                String username = request.getParameter("txtUsername");
                String password = request.getParameter("txtPassword");
                UserDao dao = UserDao.getInstance();
                String role = dao.checkLogin(username, password);
                if (role.equals("admin")) {
                    url = ADMIN;
                }
            } else if (btn.equals("CrawlSkills")) {
                MainCrawler.crawlSkill(getServletContext());
            } else if (btn.equals("CrawlJobs")) {
                MainCrawler.crawlJobs(getServletContext());
            } else if (btn.equals("StopCrawl")) {
                MainCrawler.stopCrawl();
            } else if (btn.equals("Process")) {
                MainProcessor.processCreateData();
                request.setAttribute("DONE", "All processes is done !");
                System.out.println("All processes is done !");
            } else if (btn.equals("Test")) {
                MainCrawler.test(getServletContext());
                url = ADMIN;
            }

        } catch (Exception e) {
            Logger.getLogger(ProcessServlet.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            out.close();
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
