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
     List<Student> AllStudent();
     List<Employee> AllEmploy();
    List<Book> AllBook();
    List<Invoice> AllBorrower();
    List<Book> SearchBook(String nameBook);
    
    String FindRoleUser(String nameUser);
    
    
    String AllowBorrowBook(String nameBook,String nameUser,String nameStaff);
    boolean DeleteBook(String nameBook);
    boolean AddBook(String nameBook,boolean avai,int num,String mess);
    boolean AddEmployee(String nameUser,String passUser,String role,String mess);
    boolean UpdateBook(String nameBook,boolean  avai,int num);
    boolean BorrowBook(String nameBook,String nameUser,String mess);
    String ReturnBook(String nameBook,String nameUser,Object mess,String nameStaff);
    boolean ChangePass(String nameUser,String passUser);
    boolean AddStudent(String nameStu);
    
    int getEmployeePass(String name);
}
