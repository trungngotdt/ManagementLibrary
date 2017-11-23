/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Tran
 */
@Stateful
public class SessionBean implements SessionBeanRemote {

    @PersistenceContext(unitName = "LibraryPU")
    private EntityManager em;

    @Override
    public List<User> AllUser() {
        return em.createNamedQuery("User.findAll").getResultList();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Book> AllBook() {
        return em.createNamedQuery("Book.findAll").getResultList();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private long GetIdUser(String name) {
        for (User object : AllUser()) {
            if (object.getName().equals(name)) {
                return object.getId();
            }
        }
        return -1;
    }

    @Override
    public boolean BorrowBook(String nameBook, String nameUser) {
        List<Book> listBook = AllBook();
        for (Book book : listBook) {
            if (book.getName().equals(nameBook) && book.getAvailable() == true&&book.getNumbers()>0) {
                
                book.setNumbers((book.getNumbers() - 1));
                long idUser = GetIdUser(nameUser);
                User user = em.find(User.class, idUser);
                UserBorrowBookPK userBorrowBookPK = new UserBorrowBookPK(idUser, book.getId());
                UserBorrowBook userBorrowBook=em.find(UserBorrowBook.class, userBorrowBookPK);
                UserBorrowBook userBorrow;
                if (userBorrowBook != null) {
                    userBorrow = new UserBorrowBook(userBorrowBookPK, nameUser, book.getName(), userBorrowBook.getBookNumbers()+1);
                    em.merge(userBorrow);
                }
                else
                {                    
                    userBorrow = new UserBorrowBook(userBorrowBookPK, nameUser, book.getName(), 1);
                    em.persist(userBorrow);                   
                }
                em.merge(book);
                
                return true;
            }
        }
        return false;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean ReturnBook(String nameBook, String nameUser) {
        List<UserBorrowBook> borrower = AllBorrower();
        int i = 0;
        for (UserBorrowBook userBorrowBook : borrower) {

            if (userBorrowBook.getBookName().equals(nameBook)
                    && userBorrowBook.getUserName().equals(nameUser)) {
                Book book = em.find(Book.class,
                        userBorrowBook.getUserBorrowBookPK().getBookId());
                book.setNumbers(book.getNumbers()+1);
                if (userBorrowBook.getBookNumbers()-1<=0) {
                    em.remove(userBorrowBook);
                }
                else
                {
                    userBorrowBook.setBookNumbers(userBorrowBook.getBookNumbers()-1);
                    em.merge(userBorrowBook);
                }
                em.merge(book);
                return true;
            }
            i++;
        }
        return false;
    }

    @Override
    public List<UserBorrowBook> AllBorrower() {

        return em.createNamedQuery("UserBorrowBook.findAll").getResultList();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getUserPass(String name) {
        List<User> user = AllUser();
        for (User user1 : user) {
            if (user1.getName().equals(name)) {
                return user1.getPassword().hashCode();
            }
        }
        return -1;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean ChangePass(String nameUser, String passUser) {
        Long id = GetIdUser(nameUser);
        User u = new User(id, nameUser, passUser);
        try {
            em.merge(u);
            return true;
        } catch (Exception e) {
            return false;
        }

//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
