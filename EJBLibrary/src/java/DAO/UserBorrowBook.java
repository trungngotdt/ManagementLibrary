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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Tran
 */
@Entity
@Table(name = "user_borrow_book", catalog = "Data", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserBorrowBook.findAll", query = "SELECT u FROM UserBorrowBook u")
    , @NamedQuery(name = "UserBorrowBook.findById", query = "SELECT u FROM UserBorrowBook u WHERE u.id = :id")
    , @NamedQuery(name = "UserBorrowBook.findByUserName", query = "SELECT u FROM UserBorrowBook u WHERE u.userName = :userName")
    , @NamedQuery(name = "UserBorrowBook.findByBookName", query = "SELECT u FROM UserBorrowBook u WHERE u.bookName = :bookName")
    , @NamedQuery(name = "UserBorrowBook.findByNumbers", query = "SELECT u FROM UserBorrowBook u WHERE u.numbers = :numbers")
    , @NamedQuery(name = "UserBorrowBook.findByBegin", query = "SELECT u FROM UserBorrowBook u WHERE u.begin = :begin")
    , @NamedQuery(name = "UserBorrowBook.findByEnd", query = "SELECT u FROM UserBorrowBook u WHERE u.end = :end")
    , @NamedQuery(name = "UserBorrowBook.findByStatus", query = "SELECT u FROM UserBorrowBook u WHERE u.status = :status")})
public class UserBorrowBook implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "user_name", nullable = false, length = 2147483647)
    private String userName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "book_name", nullable = false, length = 2147483647)
    private String bookName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numbers", nullable = false)
    private long numbers;
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
    @Column(name = "status")
    private Boolean status;
    @JoinColumn(name = "book_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Book bookId;
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private User userId;

    public UserBorrowBook() {
    }

    public UserBorrowBook(Long id) {
        this.id = id;
    }

    public UserBorrowBook(Long id, String userName, String bookName, long numbers, Date begin, Date end) {
        this.id = id;
        this.userName = userName;
        this.bookName = bookName;
        this.numbers = numbers;
        this.begin = begin;
        this.end = end;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public long getNumbers() {
        return numbers;
    }

    public void setNumbers(long numbers) {
        this.numbers = numbers;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Book getBookId() {
        return bookId;
    }

    public void setBookId(Book bookId) {
        this.bookId = bookId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
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
        if (!(object instanceof UserBorrowBook)) {
            return false;
        }
        UserBorrowBook other = (UserBorrowBook) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DAO.UserBorrowBook[ id=" + id + " ]";
    }
    
}
