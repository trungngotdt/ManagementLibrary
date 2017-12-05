/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Tran
 */
@Entity
@Table(name = "invoice", catalog = "Data", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Invoice.findAll", query = "SELECT i FROM Invoice i")
    , @NamedQuery(name = "Invoice.findById", query = "SELECT i FROM Invoice i WHERE i.invoicePK.id = :id")
    , @NamedQuery(name = "Invoice.findByIdStudent", query = "SELECT i FROM Invoice i WHERE i.invoicePK.idStudent = :idStudent")
    , @NamedQuery(name = "Invoice.findByIdBook", query = "SELECT i FROM Invoice i WHERE i.invoicePK.idBook = :idBook")
    , @NamedQuery(name = "Invoice.findByIdEmployee", query = "SELECT i FROM Invoice i WHERE i.invoicePK.idEmployee = :idEmployee")
    , @NamedQuery(name = "Invoice.findByBegin", query = "SELECT i FROM Invoice i WHERE i.begin = :begin")
    , @NamedQuery(name = "Invoice.findByEnd", query = "SELECT i FROM Invoice i WHERE i.end = :end")
    , @NamedQuery(name = "Invoice.findByNumbers", query = "SELECT i FROM Invoice i WHERE i.numbers = :numbers")})
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InvoicePK invoicePK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "begin", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date begin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "end", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date end;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numbers", nullable = false)
    private long numbers;
    @JoinColumn(name = "id_book", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Book book;
    @JoinColumn(name = "id_employee", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Employee employee;
    @JoinColumn(name = "id_student", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Student student;

    public Invoice() {
    }

    public Invoice(InvoicePK invoicePK) {
        this.invoicePK = invoicePK;
    }

    public Invoice(InvoicePK invoicePK, Date begin, Date end, long numbers) {
        this.invoicePK = invoicePK;
        this.begin = begin;
        this.end = end;
        this.numbers = numbers;
    }

    public Invoice(long id, long idStudent, long idBook, long idEmployee) {
        this.invoicePK = new InvoicePK(id, idStudent, idBook, idEmployee);
    }

    public InvoicePK getInvoicePK() {
        return invoicePK;
    }

    public void setInvoicePK(InvoicePK invoicePK) {
        this.invoicePK = invoicePK;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public long getNumbers() {
        return numbers;
    }

    public void setNumbers(long numbers) {
        this.numbers = numbers;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (invoicePK != null ? invoicePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Invoice)) {
            return false;
        }
        Invoice other = (Invoice) object;
        if ((this.invoicePK == null && other.invoicePK != null) || (this.invoicePK != null && !this.invoicePK.equals(other.invoicePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DAO.Invoice[ invoicePK=" + invoicePK + " ]";
    }
    
}
