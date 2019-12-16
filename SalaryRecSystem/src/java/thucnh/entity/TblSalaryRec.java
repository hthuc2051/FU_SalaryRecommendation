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
@Table(name = "TblSalaryRec")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblSalaryRec.findAll", query = "SELECT t FROM TblSalaryRec t")
    , @NamedQuery(name = "TblSalaryRec.findById", query = "SELECT t FROM TblSalaryRec t WHERE t.id = :id")
    , @NamedQuery(name = "TblSalaryRec.checkExistedRecSalary", query = "SELECT t FROM TblSalaryRec t WHERE t.skillId = :skill AND t.expLevel = :expLevel")
    , @NamedQuery(name = "TblSalaryRec.findByExpLevel", query = "SELECT t FROM TblSalaryRec t WHERE t.expLevel = :expLevel")
    , @NamedQuery(name = "TblSalaryRec.findBySalaryRec", query = "SELECT t FROM TblSalaryRec t WHERE t.salaryRec = :salaryRec")})
public class TblSalaryRec implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Size(max = 150)
    @Column(name = "expLevel")
    private String expLevel;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "salaryRec")
    private Double salaryRec;
    @Column(name = "active")
    private Boolean active;
    @JoinColumn(name = "skillId", referencedColumnName = "id")
    @ManyToOne
    private TblSkill skillId;

    public TblSalaryRec() {
    }

    public TblSalaryRec(Integer id) {
        this.id = id;
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

    public Double getSalaryRec() {
        return salaryRec;
    }

    public void setSalaryRec(Double salaryRec) {
        this.salaryRec = salaryRec;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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
