/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;

/**
 *
 * @author Tran
 */
@Remote
public interface SessionBeanRemote {
      List<User> AllUser();
    List<Book> AllBook();
    List<UserBorrowBook> AllBorrower();
    
    boolean BorrowBook(String nameBook,String nameUser);
    boolean ReturnBook(String nameBook,String nameUser);
    boolean ChangePass(String nameUser,String passUser);
    int getUserPass(String name);
}
