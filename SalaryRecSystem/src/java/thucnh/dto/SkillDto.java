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
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SkillDto implements Serializable {

    @XmlElement(name = "id")
    private Integer id;
    @XmlElement(name = "name")
    private String name;
    @XmlElement(name = "type")
    private String type;
    @XmlElement(name = "expLevels")
    private List<String> listExpLevel;
    @XmlElement(name = "hash")
    private Integer hash;

    public SkillDto() {
    }

    public SkillDto(Integer id, String name, String type, List<String> listExpLevel, Integer hash) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.listExpLevel = listExpLevel;
        this.hash = hash;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<String> getListExpLevel() {
        return listExpLevel;
    }

    public void setListExpLevel(List<String> listExpLevel) {
        this.listExpLevel = listExpLevel;
    }

}
