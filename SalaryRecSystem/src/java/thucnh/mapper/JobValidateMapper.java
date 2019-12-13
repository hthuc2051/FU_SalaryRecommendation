/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.mapper;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import thucnh.dto.JobValidateObj;
import thucnh.entity.TblJob;

/**
 *
 * @author HP
 */
public class JobValidateMapper extends XmlAdapter<JobValidateObj, TblJob> {


    @Override
    public JobValidateObj marshal(TblJob entity) {
        try {
            if (entity != null) {
                JobValidateObj dto = new JobValidateObj();
                dto.setExpLevel(entity.getExpLevel());
                dto.setLink(entity.getLink());
                dto.setSalary(entity.getSalary());
                dto.setHash(entity.getHash());
                return dto;
            }
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public TblJob unmarshal(JobValidateObj v) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

