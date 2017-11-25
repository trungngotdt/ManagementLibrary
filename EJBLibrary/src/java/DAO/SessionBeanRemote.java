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
    List<Book> SearchBook(String nameBook);
    
    String FindRoleUser(String nameUser);
    
    
    boolean AllowBorrowBook(String nameBook,String nameUser);
    boolean DeleteBook(String nameBook);
    boolean AddBook(String nameBook,boolean avai,int num,String mess);
    boolean AddUser(String nameUser,String passUser,String role,String mess);
    boolean UpdateBook(String nameBook,boolean  avai,int num);
    boolean BorrowBook(String nameBook,String nameUser,String mess);
    boolean ReturnBook(String nameBook,String nameUser,Object mess);
    boolean ChangePass(String nameUser,String passUser);
    
    int getUserPass(String name);
}
