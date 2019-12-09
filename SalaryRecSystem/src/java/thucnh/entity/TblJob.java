/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "TblJob")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblJob.findAll", query = "SELECT t FROM TblJob t")
    , @NamedQuery(name = "TblJob.findDistinctJobs", query = "SELECT DISTINCT t.expLevel FROM TblJob t")
    , @NamedQuery(name = "TblJob.findById", query = "SELECT t FROM TblJob t WHERE t.id = :id")
    , @NamedQuery(name = "TblJob.findBySalary", query = "SELECT t FROM TblJob t WHERE t.salary = :salary")
    , @NamedQuery(name = "TblJob.findByExpLevel", query = "SELECT t FROM TblJob t WHERE t.expLevel = :expLevel")
    , @NamedQuery(name = "TblJob.findByLink", query = "SELECT t FROM TblJob t WHERE t.link = :link")
    , @NamedQuery(name = "TblJob.findBySkillAndExpYear", query = "SELECT t FROM TblJob t WHERE t.skillId = :skill AND t.expLevel = :expLevel")
    , @NamedQuery(name = "TblJob.findByHash", query = "SELECT t FROM TblJob t WHERE t.hash = :hash")

})
public class TblJob implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "salary")
    private Double salary;
    @Size(max = 150)
    @Column(name = "expLevel")
    private String expLevel;
    @Size(max = 500)
    @Column(name = "link")
    private String link;
    @Column(name = "hash")
    private Integer hash;
    @JoinColumn(name = "clusterId", referencedColumnName = "id")
    @ManyToOne
    private TblCluster clusterId;
    @JoinColumn(name = "skillId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TblSkill skillId;

    public TblJob() {
    }

    public TblJob(Integer id) {
        this.id = id;
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

    public Integer getHash() {
        return hash;
    }

    public void setHash(Integer hash) {
        this.hash = hash;
    }

    public TblCluster getClusterId() {
        return clusterId;
    }

    public void setClusterId(TblCluster clusterId) {
        this.clusterId = clusterId;
    }

    public TblSkill getSkillId() {
        return skillId;
    }

    public void setSkillId(TblSkill skillId) {
        this.skillId = skillId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblJob)) {
            return false;
        }
        TblJob other = (TblJob) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "thucnh.entity.TblJob[ id=" + id + " ]";
    }

}
