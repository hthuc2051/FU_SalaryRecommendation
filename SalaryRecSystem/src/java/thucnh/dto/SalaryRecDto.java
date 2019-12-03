/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.dto;

import java.io.Serializable;

/**
 *
 * @author HP
 */
public class SalaryRecDto implements Serializable{
    
    private int id;
    private int skillId;
    private String expLevel;
    private double salaryRec;

    public SalaryRecDto() {
    }

    public SalaryRecDto(int id, int skillId, String expLevel, double salaryRec) {
        this.id = id;
        this.skillId = skillId;
        this.expLevel = expLevel;
        this.salaryRec = salaryRec;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
