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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "SummaryJob")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SummaryJob.findAll", query = "SELECT s FROM SummaryJob s")
    , @NamedQuery(name = "SummaryJob.findById", query = "SELECT s FROM SummaryJob s WHERE s.id = :id")
    , @NamedQuery(name = "SummaryJob.findBySalary", query = "SELECT s FROM SummaryJob s WHERE s.salary = :salary")
    , @NamedQuery(name = "SummaryJob.findByNoOfJobs", query = "SELECT s FROM SummaryJob s WHERE s.noOfJobs = :noOfJobs")
    , @NamedQuery(name = "SummaryJob.findByExpSkillHash", query = "SELECT s FROM SummaryJob s WHERE s.expSkillHash = :expSkillHash")})
public class SummaryJob implements Serializable {

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
    @Column(name = "noOfJobs")
    private Integer noOfJobs;
    @Column(name = "expSkillHash")
    private Integer expSkillHash;

    public SummaryJob() {
    }

    public SummaryJob(Integer id) {
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

    public Integer getNoOfJobs() {
        return noOfJobs;
    }

    public void setNoOfJobs(Integer noOfJobs) {
        this.noOfJobs = noOfJobs;
    }

    public Integer getExpSkillHash() {
        return expSkillHash;
    }

    public void setExpSkillHash(Integer expSkillHash) {
        this.expSkillHash = expSkillHash;
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
        if (!(object instanceof SummaryJob)) {
            return false;
        }
        SummaryJob other = (SummaryJob) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "thucnh.entity.SummaryJob[ id=" + id + " ]";
    }
    
}
