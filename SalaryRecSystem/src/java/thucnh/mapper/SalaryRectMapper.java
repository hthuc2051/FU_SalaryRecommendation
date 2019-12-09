/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import thucnh.dto.SalaryRecDto;
import thucnh.entity.TblSalaryRec;

/**
 *
 * @author HP
 */
public class SalaryRectMapper extends XmlAdapter<SalaryRecDto, TblSalaryRec> {

//    public static SalaryRecDto toDto(TblSalaryRec entity) {
//        if (entity != null) {
//            SalaryRecDto dto = new SalaryRecDto();
//            dto.setId(entity.getId());
//            dto.setSkillId(entity.getSkillId().getId());
//            dto.setExpLevel(entity.getExpLevel());
//            dto.setSalaryRec(entity.getSalaryRec());
//            return dto;
//        }
//        return null;
//    }
    public List<SalaryRecDto> toListDto(List<TblSalaryRec> listEntity) {
        try {
            if (listEntity != null) {
                List<SalaryRecDto> result = new ArrayList<>();
                for (TblSalaryRec entity : listEntity) {
                    SalaryRecDto dto = marshal(entity);
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
    public TblSalaryRec unmarshal(SalaryRecDto v) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SalaryRecDto marshal(TblSalaryRec entity) throws Exception {
        if (entity != null) {
            SalaryRecDto dto = new SalaryRecDto();
            dto.setId(entity.getId());
            dto.setSkillId(entity.getSkillId().getId());
            dto.setExpLevel(entity.getExpLevel());
            dto.setSalaryRec(entity.getSalaryRec());
            dto.setSkillName(entity.getSkillId().getName());
            return dto;
        }
        return null;
    }
}
