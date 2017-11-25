/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateful;
import javax.management.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Tran
 */
@Stateful
public class SessionBean implements SessionBeanRemote {
    
    @PersistenceContext(unitName = "EJBLibraryPU")
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
    
    @Override
    public List<UserBorrowBook> AllBorrower() {
        return em.createNamedQuery("UserBorrowBook.findAll").getResultList();//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean UpdateBook(String nameBook, boolean avai, int num) {
        long id = GetIdBook(nameBook);
        String query = "UPDATE public.book b SET name=:bookname, available=:avai, numbers=:num WHERE b.id=:bookid";
        javax.persistence.Query q = em.createNativeQuery(query);
        q.setParameter("bookname", nameBook);
        q.setParameter("avai", avai);
        q.setParameter("bookid", id);
        q.setParameter("num", num);
        
        return q.executeUpdate() > 0;//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean BorrowBook(String nameBook, String nameUser, String mess) {
        List<Book> listBook = AllBook();
        for (Book book : listBook) {
            if (book.getName().equals(nameBook) && book.getAvailable() == true && book.getNumbers() > 0) {
                
                long idUser = GetIdUser(nameUser);
                
                int userBorrowBook = 0;
                javax.persistence.Query qBRB = em.createNativeQuery("SELECT numbers from public.user_borrow_book a where a.book_id =:bookid and a.user_id=:userid ");
                
                qBRB.setParameter("bookid", book.getId());
                qBRB.setParameter("userid", idUser);
                userBorrowBook = qBRB.getResultList().size();
//                UserBorrowBook userBorrowBook = em.find(UserBorrowBook.class, userBorrowBookPK);
//                UserBorrowBook userBorrow;
                javax.persistence.Query q;
                javax.persistence.Query qUpdateNumberBook;
                if (userBorrowBook != 0) {
                    /*
                    q = em.createNativeQuery("UPDATE public.user_borrow_book a SET  numbers =:numberborrow WHERE a.book_id = :bookid and a.user_id= :userid ");
                    //:userid,:username,:bookid, :bookname

                    q.setParameter("numberborrow", Integer.parseInt(qBRB.getSingleResult().toString()) + 1);
                    q.setParameter("userid", idUser);
                    q.setParameter("bookid", book.getId());
                    //return q.executeUpdate()>0;
                    //return  false;

                    qUpdateNumberBook = em.createNativeQuery("UPDATE public.book b	SET  numbers =:number WHERE b.id=:id");
                    qUpdateNumberBook.setParameter("number", book.getNumbers() - 1);
                    qUpdateNumberBook.setParameter("id", book.getId());*/
                    mess = "The name of your book already exists";
                    return false;
                } else {
                    
                    q = em.createNativeQuery("INSERT INTO public.user_borrow_book( user_id, user_name, book_id, book_name, numbers,begin, \"end\",status)	VALUES ( :userid,:username,:bookid, :bookname, 1,:begin,:end,:status)");
                    //:userid,:username,:bookid, :bookname
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    Calendar c = Calendar.getInstance();
                    q.setParameter("userid", idUser);
                    q.setParameter("username", nameUser);
                    q.setParameter("bookid", book.getId());
                    q.setParameter("bookname", book.getName());
                    q.setParameter("begin", c.getTime());
                    c.setTime(new Date());
                    c.add(Calendar.DATE, 7);
                    q.setParameter("end", c.getTime());
                    q.setParameter("status", false);
                    //return q.executeUpdate()>0;
                    //userBorrow = new UserBorrowBook();//userBorrowBookPK, nameUser, book.getName(), 1);
                    //em.persist(userBorrow);
/*
                    qUpdateNumberBook = em.createNativeQuery("UPDATE public.book b SET  numbers =:number WHERE b.id=:id");
                    qUpdateNumberBook.setParameter("number", book.getNumbers() - 1);
                    qUpdateNumberBook.setParameter("id", book.getId());
                     */
                }
                //em.merge(book);
                return q.executeUpdate() > 0;//&& qUpdateNumberBook.executeUpdate() > 0;
                //return true;
            }
        }
        mess = " the system cannot find the book specified";
        return false;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private long GetIdBook(String nameBook) {
        for (Book object : AllBook()) {
            if (object.getName().equals(nameBook)) {
                return object.getId();
            }
        }
        return -1;
    }
    
    private long GetIdUser(String name) {
        for (User object : AllUser()) {
            if (object.getName().equals(name)) {
                return object.getId();
            }
        }
        return -1;
    }
    
    @Override
    public boolean ReturnBook(String nameBook, String nameUser, Object mess) {

        //List<UserBorrowBook> borrower = AllBorrower();
        long bookId = GetIdBook(nameBook);
        long userId = GetIdUser(nameUser);
        if (bookId < 0 && userId < 0) {
            mess = "You don't have any book";
            return false;
        }
        //int userBorrowBook = 0;
        javax.persistence.Query qBRB = em.createNativeQuery("SELECT \"end\" from public.user_borrow_book a where a.book_id =:bookid and a.user_id=:userid ");
        
        qBRB.setParameter("bookid", bookId);
        qBRB.setParameter("userid", userId);
        Object userBorrowBookEndDate = qBRB.getResultList();
        mess = userBorrowBookEndDate;
        String query;/*
        if (userBorrowBook > 0) {*/
        //int numberborrow = Integer.parseInt(qBRB.getSingleResult().toString()) - 1;
        javax.persistence.Query q;
        /*if (numberborrow == 0) {*/
        q = em.createNativeQuery("DELETE FROM public.user_borrow_book u WHERE u.book_id= :bookid and u.user_id = :userid ");
        q.setParameter("userid", userId);
        q.setParameter("bookid", bookId);
        /*
            } else {
                q = em.createNativeQuery("UPDATE public.user_borrow_book a SET  numbers =:numberborrow WHERE a.book_id = :bookid and a.user_id= :userid ");

                q.setParameter("numberborrow", Integer.parseInt(qBRB.getSingleResult().toString()) - 1);
                q.setParameter("userid", userId);
                q.setParameter("bookid", bookId);
            }*/
        Book b = new Book();
        b = em.find(Book.class, bookId);
        
        boolean check = UpdateBook(nameBook, true, Integer.parseInt(String.valueOf(b.getNumbers())) + 1);
        return check == true && q.executeUpdate() > 0;
        //javax.persistence.Query qB;

        //}
        //return false;
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

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public List<Book> SearchBook(String nameBook) {
        List<Book> book;
        //em.getTransaction().begin();
        //nho sua
        javax.persistence.Query q = em.createNativeQuery("select * from public.book a where a.\"name\" like  :names", Book.class);
        q.setParameter("names", "%" + nameBook + "%");
        book = q.getResultList();
        //em.getTransaction().commit();
        return book;
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String FindRoleUser(String nameUser) {
        
        String query = "SELECT role FROM public.\"user\" a where a.name like :names";
        javax.persistence.Query q = em.createNativeQuery(query);
        q.setParameter("names", nameUser);
        return q.getSingleResult().toString();
//To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean AddUser(String nameUser, String passUser, String role, String mess) {
        
        if (GetIdUser(nameUser) > 0) {
            mess = "The name of your user already exists";
            return false;
        }
        String query = "INSERT INTO public.\"user\"( name,  password, role) VALUES ( :Name, :Pass, :Role)";
        javax.persistence.Query q = em.createNativeQuery(query);
        q.setParameter("Name", nameUser);
        q.setParameter("Pass", passUser);
        q.setParameter("Role", role);
        return q.executeUpdate() > 0;

//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean AddBook(String nameBook, boolean avai, int num, String mess) {
        if (Integer.parseInt(String.valueOf(GetIdBook(nameBook))) > 0) {
            mess = "The name of your book already exists";
            return false;
        }
        System.err.println(String.valueOf(GetIdBook(nameBook)));
        String query = "INSERT INTO public.book ( name, available, numbers ) VALUES ( :book, :avai, :num)";
        javax.persistence.Query q = em.createNativeQuery(query);
        q.setParameter("book", nameBook);
        q.setParameter("avai", avai);
        q.setParameter("num", num);
        return q.executeUpdate() > 0;
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean DeleteBook(String nameBook) {
        long id = GetIdBook(nameBook);
        String query = "UPDATE public.book b SET available=:avai WHERE b.id=:bookid";
        javax.persistence.Query q = em.createNativeQuery(query);
        q.setParameter("avai", false);
        q.setParameter("bookid", id);
        return q.executeUpdate() > 0;
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean AllowBorrowBook(String nameBook, String nameUser) {
        //List<Book> listBook = AllBook();
        long idUser = GetIdUser(nameUser);
        long idBook = GetIdBook(nameBook);
        Book book = em.find(Book.class, idBook);
        //int userBorrowBook = 0;
        if (idUser<0||idBook<0) {
            return false;
            //mess="Error with name of user or name of book";
        }
        javax.persistence.Query qBRB = em.createNativeQuery("SELECT status from public.user_borrow_book a where a.book_id =:bookid and a.user_id=:userid ");
        
        qBRB.setParameter("bookid", book.getId());
        qBRB.setParameter("userid", idUser);
        //Object userBorrowBook = qBRB.getResultList();
        /*if (Boolean.valueOf( userBorrowBook.toString())==true) {
                    return false;
                }*/
//                UserBorrowBook userBorrowBook = em.find(UserBorrowBook.class, userBorrowBookPK);
//                UserBorrowBook userBorrow;
        javax.persistence.Query q;
        javax.persistence.Query qUpdateNumberBook;
        q = em.createNativeQuery("UPDATE public.user_borrow_book a SET status =:statu WHERE a.book_id = :bookid and a.user_id= :userid ");
        boolean a = true;
        q.setParameter("statu", a);
        q.setParameter("userid", idUser);
        q.setParameter("bookid", book.getId());
        /*if (userBorrowBook != 0) {*/
 /*
                    q = em.createNativeQuery("UPDATE public.user_borrow_book a SET  numbers =:numberborrow WHERE a.book_id = :bookid and a.user_id= :userid ");
                    //:userid,:username,:bookid, :bookname

                    q.setParameter("numberborrow", Integer.parseInt(qBRB.getSingleResult().toString()) + 1);
                    q.setParameter("userid", idUser);
                    q.setParameter("bookid", book.getId());
                    //return q.executeUpdate()>0;
                    //return  false;

                    qUpdateNumberBook = em.createNativeQuery("UPDATE public.book b	SET  numbers =:number WHERE b.id=:id");
                    qUpdateNumberBook.setParameter("number", book.getNumbers() - 1);
                    qUpdateNumberBook.setParameter("id", book.getId());*/
//return false;
        //} else {
/*
                    q = em.createNativeQuery("INSERT INTO public.user_borrow_book( user_id, user_name, book_id, book_name, numbers,begin, \"end\",status)	VALUES ( :userid,:username,:bookid, :bookname, 1,:begin,:end,:status)");
                    //:userid,:username,:bookid, :bookname
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    Calendar c = Calendar.getInstance();
                    q.setParameter("userid", idUser);
                    q.setParameter("username", nameUser);
                    q.setParameter("bookid", book.getId());
                    q.setParameter("bookname", book.getName());
                    q.setParameter("begin",c.getTime());
                    c.setTime(new Date()); 
                    c.add(Calendar.DATE, 7);
                    q.setParameter("end",c.getTime());
                    q.setParameter("status", false);*/
        //return q.executeUpdate()>0;
        //userBorrow = new UserBorrowBook();//userBorrowBookPK, nameUser, book.getName(), 1);
        //em.persist(userBorrow);

        qUpdateNumberBook = em.createNativeQuery("UPDATE public.book b SET  numbers =:number WHERE b.id=:id");
        qUpdateNumberBook.setParameter("number", book.getNumbers() - 1);
        qUpdateNumberBook.setParameter("id", book.getId());
        //}
        //em.merge(book);
        return q.executeUpdate() > 0 && qUpdateNumberBook.executeUpdate() > 0;
        //return true;
        /*for (Book book : listBook) {
            if (book.getName().equals(nameBook) && book.getAvailable() == true && book.getNumbers() > 0) {

            }
        }
        return false;*/
        //return false;
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
