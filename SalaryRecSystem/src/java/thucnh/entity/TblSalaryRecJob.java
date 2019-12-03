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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "tblSalaryRec_Job")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblSalaryRecJob.findAll", query = "SELECT t FROM TblSalaryRecJob t")
    , @NamedQuery(name = "TblSalaryRecJob.findById", query = "SELECT t FROM TblSalaryRecJob t WHERE t.id = :id")})
public class TblSalaryRecJob implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "jobId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TblJob jobId;
    @JoinColumn(name = "salaryRecId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TblSalaryRec salaryRecId;

    public TblSalaryRecJob() {
    }

    public TblSalaryRecJob(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TblJob getJobId() {
        return jobId;
    }

    public void setJobId(TblJob jobId) {
        this.jobId = jobId;
    }

    public TblSalaryRec getSalaryRecId() {
        return salaryRecId;
    }

    public void setSalaryRecId(TblSalaryRec salaryRecId) {
        this.salaryRecId = salaryRecId;
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
        if (!(object instanceof TblSalaryRecJob)) {
            return false;
        }
        TblSalaryRecJob other = (TblSalaryRecJob) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "thucnh.entity.TblSalaryRecJob[ id=" + id + " ]";
    }
    
}
