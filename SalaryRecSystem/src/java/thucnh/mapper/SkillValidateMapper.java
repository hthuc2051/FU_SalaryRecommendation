/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.mapper;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import thucnh.dto.SkillValidateObj;
import thucnh.entity.TblSkill;

/**
 *
 * @author HP
 */
public class SkillValidateMapper extends XmlAdapter<SkillValidateObj, TblSkill> {

    @Override
    public TblSkill unmarshal(SkillValidateObj v) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SkillValidateObj marshal(TblSkill entity) {
        try {
            if (entity != null) {
                SkillValidateObj dto = new SkillValidateObj();
                dto.setName(entity.getName());
                dto.setType(entity.getType());
                dto.setHash(entity.getHash());
                return dto;
            }
        } catch (Exception e) {
        }
        return null;
    }
}
