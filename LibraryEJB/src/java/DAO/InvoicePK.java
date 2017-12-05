/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Tran
 */
@Embeddable
public class InvoicePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_student", nullable = false)
    private long idStudent;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_book", nullable = false)
    private long idBook;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_employee", nullable = false)
    private long idEmployee;

    public InvoicePK() {
    }

    public InvoicePK(long id, long idStudent, long idBook, long idEmployee) {
        this.id = id;
        this.idStudent = idStudent;
        this.idBook = idBook;
        this.idEmployee = idEmployee;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(long idStudent) {
        this.idStudent = idStudent;
    }

    public long getIdBook() {
        return idBook;
    }

    public void setIdBook(long idBook) {
        this.idBook = idBook;
    }

    public long getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(long idEmployee) {
        this.idEmployee = idEmployee;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        hash += (int) idStudent;
        hash += (int) idBook;
        hash += (int) idEmployee;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvoicePK)) {
            return false;
        }
        InvoicePK other = (InvoicePK) object;
        if (this.id != other.id) {
            return false;
        }
        if (this.idStudent != other.idStudent) {
            return false;
        }
        if (this.idBook != other.idBook) {
            return false;
        }
        if (this.idEmployee != other.idEmployee) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DAO.InvoicePK[ id=" + id + ", idStudent=" + idStudent + ", idBook=" + idBook + ", idEmployee=" + idEmployee + " ]";
    }
    
}
