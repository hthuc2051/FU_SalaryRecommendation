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
public class InitialDto implements Serializable {

    @XmlElement(name = "salaryRecs")
    private List<SalaryRecDto> salaryRecs;
    @XmlElement(name = "clusters")
    private List<ClusterDto> clusters;
    @XmlElement(name = "skills")
    private List<SkillDto> skills;

    public InitialDto() {
    }

    public InitialDto(List<SalaryRecDto> salaryRecs, List<ClusterDto> clusters, List<SkillDto> skills) {
        this.salaryRecs = salaryRecs;
        this.clusters = clusters;
        this.skills = skills;
    }

    public List<SalaryRecDto> getSalaryRecs() {
        return salaryRecs;
    }

    public void setSalaryRecs(List<SalaryRecDto> salaryRecs) {
        this.salaryRecs = salaryRecs;
    }

    public List<ClusterDto> getClusters() {
        return clusters;
    }

    public void setClusters(List<ClusterDto> clusters) {
        this.clusters = clusters;
    }

    public List<SkillDto> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillDto> skills) {
        this.skills = skills;
    }

    
}
