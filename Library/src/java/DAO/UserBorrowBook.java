/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
 * @author Tran
 */
@Entity
@Table(name = "user_borrow_book", catalog = "Data", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserBorrowBook.findAll", query = "SELECT u FROM UserBorrowBook u")
    , @NamedQuery(name = "UserBorrowBook.findByUserId", query = "SELECT u FROM UserBorrowBook u WHERE u.userBorrowBookPK.userId = :userId")
    , @NamedQuery(name = "UserBorrowBook.findByUserName", query = "SELECT u FROM UserBorrowBook u WHERE u.userName = :userName")
    , @NamedQuery(name = "UserBorrowBook.findByBookId", query = "SELECT u FROM UserBorrowBook u WHERE u.userBorrowBookPK.bookId = :bookId")
    , @NamedQuery(name = "UserBorrowBook.findByBookName", query = "SELECT u FROM UserBorrowBook u WHERE u.bookName = :bookName")
    , @NamedQuery(name = "UserBorrowBook.findByBookNumbers", query = "SELECT u FROM UserBorrowBook u WHERE u.bookNumbers = :bookNumbers")})
public class UserBorrowBook implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UserBorrowBookPK userBorrowBookPK;
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
    @Column(name = "book_numbers", nullable = false)
    private long bookNumbers;
    @JoinColumn(name = "book_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Book book;
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;

    public UserBorrowBook() {
    }

    public UserBorrowBook(UserBorrowBookPK userBorrowBookPK) {
        this.userBorrowBookPK = userBorrowBookPK;
    }

    public UserBorrowBook(UserBorrowBookPK userBorrowBookPK, String userName, String bookName, long bookNumbers) {
        this.userBorrowBookPK = userBorrowBookPK;
        this.userName = userName;
        this.bookName = bookName;
        this.bookNumbers = bookNumbers;
    }

    public UserBorrowBook(long userId, long bookId) {
        this.userBorrowBookPK = new UserBorrowBookPK(userId, bookId);
    }

    public UserBorrowBookPK getUserBorrowBookPK() {
        return userBorrowBookPK;
    }

    public void setUserBorrowBookPK(UserBorrowBookPK userBorrowBookPK) {
        this.userBorrowBookPK = userBorrowBookPK;
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

    public long getBookNumbers() {
        return bookNumbers;
    }

    public void setBookNumbers(long bookNumbers) {
        this.bookNumbers = bookNumbers;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userBorrowBookPK != null ? userBorrowBookPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserBorrowBook)) {
            return false;
        }
        UserBorrowBook other = (UserBorrowBook) object;
        if ((this.userBorrowBookPK == null && other.userBorrowBookPK != null) || (this.userBorrowBookPK != null && !this.userBorrowBookPK.equals(other.userBorrowBookPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DAO.UserBorrowBook[ userBorrowBookPK=" + userBorrowBookPK + " ]";
    }
    
}
