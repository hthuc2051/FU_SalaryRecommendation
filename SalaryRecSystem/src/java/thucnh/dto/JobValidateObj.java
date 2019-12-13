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

@XmlRootElement(name = "JobValidateObj")
@XmlAccessorType(XmlAccessType.FIELD)
public class JobValidateObj implements Serializable {

    @XmlElement(name = "salary")
    private Double salary;
    
    @XmlElement(name = "expLevel")
    private String expLevel;
    
    @XmlElement(name = "link")
    private String link;

    @XmlElement(name = "hash")
    private Integer hash;

    public JobValidateObj() {
    }

    public JobValidateObj(Double salary, String expLevel, String link, Integer hash) {
        this.salary = salary;
        this.expLevel = expLevel;
        this.link = link;
        this.hash = hash;
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

    public Integer getHash() {
        return hash;
    }

    public void setHash(Integer hash) {
        this.hash = hash;
    }
    
    
    
    
}
