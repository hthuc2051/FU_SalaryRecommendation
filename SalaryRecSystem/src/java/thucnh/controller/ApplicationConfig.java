/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.controller;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author HP
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(thucnh.controller.SummaryJobFacadeREST.class);
        resources.add(thucnh.controller.TblClusterFacadeREST.class);
        resources.add(thucnh.controller.TblJobFacadeREST.class);
        resources.add(thucnh.controller.TblSalaryRecFacadeREST.class);
        resources.add(thucnh.controller.TblSkillFacadeREST.class);
        resources.add(thucnh.controller.TblUserFacadeREST.class);
    }
    
}
