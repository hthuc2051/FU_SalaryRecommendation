/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import thucnh.dto.JobDto;
import thucnh.entity.TblJob;

/**
 *
 * @author HP
 */
public class JobMapper extends XmlAdapter<JobDto, TblJob> {

    public List<JobDto> toListDto(List<TblJob> listEntity) {
        try {
            if (listEntity != null) {
                List<JobDto> result = new ArrayList<>();
                for (TblJob entity : listEntity) {
                    JobDto dto = marshal(entity);
                    if (dto != null) {
                        result.add(dto);
                    }
                }
                return result;
            }
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public TblJob unmarshal(JobDto v) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JobDto marshal(TblJob entity) {
        try {

            if (entity != null) {
                JobDto dto = new JobDto();
                dto.setExpLevel(entity.getExpLevel());
                dto.setLink(entity.getLink());
                dto.setSalary(entity.getSalary());
                dto.setSkillName(entity.getSkillId().getName());
                return dto;
            }
        } catch (Exception e) {
        }
        return null;
    }
}
