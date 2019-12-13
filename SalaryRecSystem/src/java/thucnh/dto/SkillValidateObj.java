/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.dto;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@XmlRootElement(name = "SkillValidateObj")
@XmlAccessorType(XmlAccessType.FIELD)
public class SkillValidateObj implements Serializable {

    @XmlElement(name = "name")
    private String name;
    @XmlElement(name = "type")
    private String type;
    @XmlElement(name = "hash")
    private Integer hash;

    public SkillValidateObj() {
    }

    public SkillValidateObj(String name, String type, List<String> listExpLevel, Integer hash) {
        this.name = name;
        this.type = type;
        this.hash = hash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getHash() {
        return hash;
    }

    public void setHash(Integer hash) {
        this.hash = hash;
    }


}
