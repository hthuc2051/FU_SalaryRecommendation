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
public class PdfJobsObj implements Serializable {

    @XmlElement(name = "listJobs")
    private List<JobPdf> listJobs;

    public PdfJobsObj() {
    }

    public PdfJobsObj(List<JobPdf> listJobs) {
        this.listJobs = listJobs;
    }

    public List<JobPdf> getListJobs() {
        return listJobs;
    }

    public void setListJobs(List<JobPdf> listJobs) {
        this.listJobs = listJobs;
    }

}
