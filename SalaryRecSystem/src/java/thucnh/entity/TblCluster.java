/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "TblCluster")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblCluster.findAll", query = "SELECT t FROM TblCluster t")
    , @NamedQuery(name = "TblCluster.findById", query = "SELECT t FROM TblCluster t WHERE t.id = :id")
    , @NamedQuery(name = "TblCluster.findByCentroid", query = "SELECT t FROM TblCluster t WHERE t.centroid = :centroid")
    , @NamedQuery(name = "TblCluster.findByHash", query = "SELECT t FROM TblCluster t WHERE t.hash = :hash")})
public class TblCluster implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "centroid")
    private Double centroid;
    @Column(name = "hash")
    private Integer hash;
    @OneToMany(mappedBy = "clusterId")
    private List<TblJob> listJobs;

    public TblCluster() {
    }

    public TblCluster(Integer id) {
        this.id = id;
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

    @XmlTransient
    public List<TblJob> geTblJobsList() {
        return listJobs;
    }

    public void setTblJobsList(List<TblJob> list) {
        this.listJobs = list;
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
        if (!(object instanceof TblCluster)) {
            return false;
        }
        TblCluster other = (TblCluster) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "thucnh.entity.TblCluster[ id=" + id + " ]";
    }
    
}
