/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import thucnh.dao.JobDao;
import thucnh.dao.SalaryRecDao;
import thucnh.dao.SkillDao;
import thucnh.entity.TblJob;
import thucnh.entity.TblSalaryRec;
import thucnh.entity.TblSkill;
import thucnh.utils.AppHelper;

/**
 *
 * @author HP
 */
public class UpdateServlet extends HttpServlet {

    private static final String ERROR = "ERROR";
    private static final String SKILL = "adminPage/skills.jsp";
    private static final String JOB = "adminPage/jobs.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String action = request.getParameter("btnAction");
            if (action.equals("")) {
                // Do nothing
            } else if (action.equals("Update Skill")) {
                Integer id = Integer.parseInt(request.getParameter("txtId"));
                String name = request.getParameter("txtSkillName");
                String type = request.getParameter("txtSkillType");
                SkillDao skillDao = SkillDao.getInstance();
                TblSkill entity = skillDao.findByID(id);
                if (entity != null) {
                    entity.setName(name);
                    entity.setType(type);
                    if (skillDao.update(entity) != null) {
                        request.setAttribute("ALERT", "Update success");
                    } else {
                        request.setAttribute("ALERT", "Update failed ");
                    }
                }
                url = SKILL;
            } else if (action.equals("Update Job")) {
                Integer id = Integer.parseInt(request.getParameter("txtId"));
                String link = request.getParameter("txtJobLink");
                Double salary = Double.parseDouble(request.getParameter("txtJobSalary"));
                JobDao jobDao = JobDao.getInstance();
                TblJob entity = jobDao.findByID(id);
                if (entity != null) {
                    entity.setLink(link);
                    entity.setSalary(salary);
                    int hasValue = AppHelper.hasingString(link);
                    entity.setHash(hasValue);
                    if (jobDao.update(entity) != null) {
                        request.setAttribute("ALERT", "Update success");
                    } else {
                        request.setAttribute("ALERT", "Update failed ");
                    }
                }
                 url = JOB;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
