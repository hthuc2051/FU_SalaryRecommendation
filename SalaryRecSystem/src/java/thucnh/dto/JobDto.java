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
public class JobDto implements Serializable {

    @XmlElement(name = "id")
    private Integer id;

    @XmlElement(name = "skillName")
    private String skillName;

    @XmlElement(name = "salary")
    private Double salary;

    @XmlElement(name = "expLevel")
    private String expLevel;

    @XmlElement(name = "link")
    private String link;

    @XmlElement(name = "active")
    private boolean active;

    public JobDto() {
    }

    public JobDto(String skillName, Double salary, String expLevel, String link) {
        this.skillName = skillName;
        this.salary = salary;
        this.expLevel = expLevel;
        this.link = link;
    }

    public JobDto(Integer id, String skillName, Double salary, String expLevel, String link, boolean active) {
        this.id = id;
        this.skillName = skillName;
        this.salary = salary;
        this.expLevel = expLevel;
        this.link = link;
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

   

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getExpLevel() {
        return expLevel;
    }

    public void setExpLevel(String expLevel) {
        this.expLevel = expLevel;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
