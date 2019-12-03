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
@Table(name = "tblSkill")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblSkill.findAll", query = "SELECT t FROM TblSkill t")
    , @NamedQuery(name = "TblSkill.findById", query = "SELECT t FROM TblSkill t WHERE t.id = :id")
    , @NamedQuery(name = "TblSkill.findByName", query = "SELECT t FROM TblSkill t WHERE t.name = :name")
    , @NamedQuery(name = "TblSkill.findByType", query = "SELECT t FROM TblSkill t WHERE t.type = :type")
    , @NamedQuery(name = "TblSkill.findByHash", query = "SELECT t FROM TblSkill t WHERE t.hash = :hash")})
public class TblSkill implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private String type;
    @Column(name = "hash")
    private Integer hash;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "skillId")
    private Collection<TblJob> tblJobCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "skillId")
    private Collection<TblSalaryRec> tblSalaryRecCollection;

    public TblSkill() {
    }

    public TblSkill(Integer id) {
        this.id = id;
    }

    public TblSkill(Integer id, String name) {
        this.id = id;
        this.name = name;
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

    @XmlTransient
    public Collection<TblJob> getTblJobCollection() {
        return tblJobCollection;
    }

    public void setTblJobCollection(Collection<TblJob> tblJobCollection) {
        this.tblJobCollection = tblJobCollection;
    }

    @XmlTransient
    public Collection<TblSalaryRec> getTblSalaryRecCollection() {
        return tblSalaryRecCollection;
    }

    public void setTblSalaryRecCollection(Collection<TblSalaryRec> tblSalaryRecCollection) {
        this.tblSalaryRecCollection = tblSalaryRecCollection;
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
        if (!(object instanceof TblSkill)) {
            return false;
        }
        TblSkill other = (TblSkill) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "thucnh.entity.TblSkill[ id=" + id + " ]";
    }
    
}
