/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SalaryRecDto implements Serializable {

    @XmlElement(name = "id")
    private int id;
    @XmlElement(name = "skillId")
    private int skillId;
    @XmlElement(name = "skillName")
    private String skillName;
    @XmlElement(name = "expLevel")
    private String expLevel;
    @XmlElement(name = "salaryRec")
    private double salaryRec;

    public SalaryRecDto() {
    }

    public SalaryRecDto(int id, int skillId, String skillName, String expLevel, double salaryRec) {
        this.id = id;
        this.skillId = skillId;
        this.skillName = skillName;
        this.expLevel = expLevel;
        this.salaryRec = salaryRec;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public String getExpLevel() {
        return expLevel;
    }

    public void setExpLevel(String expLevel) {
        this.expLevel = expLevel;
    }

    public double getSalaryRec() {
        return salaryRec;
    }

    public void setSalaryRec(double salaryRec) {
        this.salaryRec = salaryRec;
    }

}
