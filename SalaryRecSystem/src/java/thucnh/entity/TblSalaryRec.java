/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "tblSalaryRec")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblSalaryRec.findAll", query = "SELECT t FROM TblSalaryRec t")
    , @NamedQuery(name = "TblSalaryRec.findById", query = "SELECT t FROM TblSalaryRec t WHERE t.id = :id")
    , @NamedQuery(name = "TblSalaryRec.findByExpLevel", query = "SELECT t FROM TblSalaryRec t WHERE t.expLevel = :expLevel")
    , @NamedQuery(name = "TblSalaryRec.findBySalaryRec", query = "SELECT t FROM TblSalaryRec t WHERE t.salaryRec = :salaryRec")})
public class TblSalaryRec implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "expLevel")
    private String expLevel;
    @Basic(optional = false)
    @Column(name = "salaryRec")
    private double salaryRec;
    @JoinColumn(name = "skillId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TblSkill skillId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "salaryRecId")
    private Collection<TblSalaryRecJob> tblSalaryRecJobCollection;

    public TblSalaryRec() {
    }

    public TblSalaryRec(Integer id) {
        this.id = id;
    }

    public TblSalaryRec(Integer id, String expLevel, double salaryRec) {
        this.id = id;
        this.expLevel = expLevel;
        this.salaryRec = salaryRec;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public TblSkill getSkillId() {
        return skillId;
    }

    public void setSkillId(TblSkill skillId) {
        this.skillId = skillId;
    }

    @XmlTransient
    public Collection<TblSalaryRecJob> getTblSalaryRecJobCollection() {
        return tblSalaryRecJobCollection;
    }

    public void setTblSalaryRecJobCollection(Collection<TblSalaryRecJob> tblSalaryRecJobCollection) {
        this.tblSalaryRecJobCollection = tblSalaryRecJobCollection;
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
        if (!(object instanceof TblSalaryRec)) {
            return false;
        }
        TblSalaryRec other = (TblSalaryRec) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "thucnh.entity.TblSalaryRec[ id=" + id + " ]";
    }
    
}
