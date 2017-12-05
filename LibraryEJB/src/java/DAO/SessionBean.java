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
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Tran
 */
@Stateful
public class SessionBean implements SessionBeanRemote {

    @PersistenceContext(unitName = "LibraryEJBPU")
    private EntityManager em;

    @Override
    public List<Student> AllStudent() {
        return em.createNamedQuery("Student.findAll").getResultList();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Book> AllBook() {
        return em.createNamedQuery("Book.findAll").getResultList();
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Invoice> AllBorrower() {
        return em.createNamedQuery("Invoice.findAll").getResultList();
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

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
        String query = "select role from public.employee e where e.\"name\" like :names";
        javax.persistence.Query q = em.createNativeQuery(query);
        q.setParameter("names", nameUser);
        return q.getSingleResult().toString();
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private long GetIdStudent(String nameStudent) {
        for (Student object : AllStudent()) {
            if (object.getName().equals(nameStudent)) {
                return object.getId();
            }
        }
        return -1;
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
    public boolean AddEmployee(String nameUser, String passUser, String role, String mess) {
        String messa="";
        if (GetIdEmployee(nameUser) > 0) {
            messa = "The name of your user already exists";
            return false;
        }
        boolean check=role.equals("admin")||role.equals("staff");
        if (!check) {
            messa="Role : admin or staff";
            return false;
        }
        String query = "insert into public.employee (name,password,role) values ( :Name, :Pass, :Role)";
        javax.persistence.Query q = em.createNativeQuery(query);
        q.setParameter("Name", nameUser);
        q.setParameter("Pass", passUser);
        q.setParameter("Role", role);
        return q.executeUpdate() > 0;
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

        return q.executeUpdate() > 0;
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean BorrowBook(String nameBook, String nameUser, String mess) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String ReturnBook(String nameBook, String nameUser, Object mess, String nameStaff) {
        long idBook = GetIdBook(nameBook);
        long idStudent = GetIdStudent(nameUser);
        long idStaff = GetIdEmployee(nameStaff);
        Book book=em.find(Book.class, idBook);
        String messa="";
        String query = "select * from public.invoice i where i.id_book = :books and i.id_student =:student";
        javax.persistence.Query qBK = em.createNativeQuery(query);
        qBK.setParameter("books", idBook);
        qBK.setParameter("student", idStudent);
        int numberBorrower=qBK.getResultList().size();
        if (numberBorrower==0) {
            messa="You don't have this book";
            return messa;
        }
        int numberBook=Integer.parseInt( String.valueOf( book.getNumbers()))+1;
        boolean isUpdate= UpdateBook(nameBook, true, numberBook);
        if (!isUpdate) {
            messa="Something wrong!";
            return messa;
        }
        
        javax.persistence.Query qdate;
        qdate=em.createNativeQuery("select i.\"end\" from public.invoice i where i.id_book=:book and i.id_student=:student");
        qdate.setParameter("book", idBook);
        qdate.setParameter("student", idStudent);
        Date current=new Date();
        Date endDate=(Date) qdate.getResultList().get(0);
        if (endDate.before(current)) {
            messa=messa+"Late : YES";
        }
        else
        {
            messa=messa+"Late : NO";
        }
        javax.persistence.Query q;
        /*if (numberborrow == 0) {*/
        q = em.createNativeQuery("DELETE FROM public.invoice u WHERE u.id_book= :bookid and u.id_student = :userid ");
        q.setParameter("userid",idStudent);
        q.setParameter("bookid", idBook);
        if (q.executeUpdate()>0) {
            //messa="";
        }
        else
        {
            messa="Wrong";
            return messa;
        }
        return messa;
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean ChangePass(String nameUser, String passUser) {
        Long id = GetIdEmployee(nameUser);
        Employee e = em.find(Employee.class, id);
        Employee u = new Employee(id, nameUser, passUser, e.getRole());
        //try {
        em.merge(u);
        return true;
        /*} catch (Exception ex) {
            return false;
        }*/

//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Employee> AllEmploy() {
        return em.createNamedQuery("Employee.findAll").getResultList();
    }

    @Override
    public int getEmployeePass(String name) {
        List<Employee> employ = AllEmploy();
        for (Employee employee : employ) {
            if (employee.getName().equals(name)) {
                return employee.getPassword().hashCode();
            }
        }
        return -1;
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private long GetIdBook(String nameBook) {
        for (Book object : AllBook()) {
            if (object.getName().equals(nameBook)) {
                return object.getId();
            }
        }
        return -1;
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private long GetIdEmployee(String nameUser) {
        for (Employee object : AllEmploy()) {
            if (object.getName().equals(nameUser)) {
                return object.getId();
            }
        }
        return -1;
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String AllowBorrowBook(String nameBook, String nameUser, String nameStaff) {
        String mess="";
        long idBook = GetIdBook(nameBook);
        long idStudent = GetIdStudent(nameUser);
        long idStaff = GetIdEmployee(nameStaff);
        if (idBook==-1||idStudent==-1) {
            return "Please try again";
        }
        Book book = em.find(Book.class, idBook);
        if (!book.getAvailable()) {
            mess="We don't have this book";
            return mess;
        }
        String query = "select * from public.invoice i where i.id_book = :books and i.id_student =:student";
        javax.persistence.Query qBK = em.createNativeQuery(query);
        qBK.setParameter("books", idBook);
        qBK.setParameter("student", idStudent);
        int numberBorrowerBook = 0;
        int numberBook = 0;
        numberBorrowerBook = qBK.getResultList().size();
        javax.persistence.Query qNB = em.createNativeQuery("select * from public.invoice i where i.id_student =:student");
        qNB.setParameter("student", idStudent);
        numberBook = qNB.getResultList().size();
        if (numberBorrowerBook > 0) {
            mess="You are borrowing this book";
            return mess;
        }
        //if (numberBook >= 2) {
        //    mess="The student can borrow a maximum of two books";
        //    return mess;
        //}

        javax.persistence.Query qUpdateNumberBook = em.createNativeQuery("UPDATE public.book b SET  numbers =:number WHERE b.id=:id");
        qUpdateNumberBook.setParameter("number", book.getNumbers() - 1);
        qUpdateNumberBook.setParameter("id", book.getId());

        javax.persistence.Query q = em.createNativeQuery("insert into public.invoice(id_book,id_employee,id_student,\"begin\",\"end\",numbers) values(:bookid , :employid , :studentid ,:begin,:end,1)");
        //:userid,:username,:bookid, :bookname
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Calendar c = Calendar.getInstance();
        q.setParameter("studentid", idStudent);
        q.setParameter("bookid", book.getId());
        q.setParameter("employid",idStaff);
        q.setParameter("begin", c.getTime());
        c.setTime(new Date());
        c.add(Calendar.DATE, 7);
        q.setParameter("end", c.getTime());
        if (q.executeUpdate()>0&&qUpdateNumberBook.executeUpdate()>0) {
            mess="";
        }
        else{
            mess="Wrong";
            return mess;
        }
        return mess;
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean AddStudent(String nameStu) {
        String mess=""; 
        if (GetIdStudent(nameStu) > 0) {
            mess = "The name of your user already exists";
            return false;
        }
        String query = "insert into public.student (\"name\") values ( :Name )";
        javax.persistence.Query q = em.createNativeQuery(query);
        q.setParameter("Name", nameStu);
        return q.executeUpdate() > 0;
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
