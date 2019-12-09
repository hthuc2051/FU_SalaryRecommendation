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
public class ClusterDto implements Serializable {

    @XmlElement(name = "id")
    private Integer id;
    @XmlElement(name = "centroid")
    private Double centroid;
    @XmlElement(name = "hash")
    private Integer hash;

    public ClusterDto() {
    }

    public ClusterDto(Integer id, Double centroid, Integer hash) {
        this.id = id;
        this.centroid = centroid;
        this.hash = hash;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getCentroid() {
        return centroid;
    }

    public void setCentroid(Double centroid) {
        this.centroid = centroid;
    }

    public Integer getHash() {
        return hash;
    }

    public void setHash(Integer hash) {
        this.hash = hash;
    }
    
    
}
