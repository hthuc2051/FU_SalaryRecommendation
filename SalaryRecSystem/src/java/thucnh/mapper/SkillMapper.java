/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import thucnh.dto.SkillDto;
import thucnh.entity.TblSalaryRec;
import thucnh.entity.TblSkill;

/**
 *
 * @author HP
 */
public class SkillMapper extends XmlAdapter<SkillDto, TblSkill> {

    public List<SkillDto> toListDto(List<TblSkill> listEntity) {
        try {
            if (listEntity != null) {
                List<SkillDto> result = new ArrayList<>();
                for (TblSkill entity : listEntity) {
                    SkillDto dto = marshal(entity);
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
    public TblSkill unmarshal(SkillDto v) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SkillDto marshal(TblSkill entity) throws Exception {
        if (entity != null) {
            Collection<TblSalaryRec> salaryRecs = entity.getTblSalaryRecCollection();
            if (salaryRecs != null && salaryRecs.size() > 0) {
                SkillDto dto = new SkillDto();
                List<String> listExpLevel = new ArrayList<>();
                dto.setId(entity.getId());
                dto.setName(entity.getName());
                dto.setType(entity.getType());
                dto.setHash(entity.getHash());
                for (TblSalaryRec salaryRec : salaryRecs) {
                    listExpLevel.add(salaryRec.getExpLevel());
                }
                dto.setListExpLevel(listExpLevel);
                return dto;
            }
        }
        return null;
    }
}
