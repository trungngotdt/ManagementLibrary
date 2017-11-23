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
public class UserBorrowBookPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "user_id", nullable = false)
    private long userId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "book_id", nullable = false)
    private long bookId;

    public UserBorrowBookPK() {
    }

    public UserBorrowBookPK(long userId, long bookId) {
        this.userId = userId;
        this.bookId = bookId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) userId;
        hash += (int) bookId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserBorrowBookPK)) {
            return false;
        }
        UserBorrowBookPK other = (UserBorrowBookPK) object;
        if (this.userId != other.userId) {
            return false;
        }
        if (this.bookId != other.bookId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DAO.UserBorrowBookPK[ userId=" + userId + ", bookId=" + bookId + " ]";
    }
    
}
