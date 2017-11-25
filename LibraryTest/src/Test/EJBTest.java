/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import DAO.Book;
import DAO.SessionBean;
import DAO.SessionBeanRemote;
import DAO.User;
import DAO.UserBorrowBook;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javassist.compiler.TokenId;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Tran
 */
public class EJBTest {

    private Properties props;
    private InitialContext ctx;

    public EJBTest() {
        readJNDI();
    }

    private void readJNDI() {
        props = new Properties();

        try {
            props.load(new FileInputStream("jndi.properties"));
        } catch (IOException e) {
            System.err.println(e.toString());
        }

        try {
            ctx = new InitialContext(props);
            //ctx.close();
        } catch (NamingException ex) {
            ex.getMessage();
        }
    }

    private void StaffMenu()
    {
        System.out.println("Welcome to library");
        System.out.println("1/Allow user to borrow a book \n2/Allow user to return a book"
                + "\n3/Change Password\n4/Exit\nEnter choice:");

    }
    
    private void AdminMenu() {
        System.out.println("Welcome to library");
        System.out.println("1/Update a book \n2/Add a book"
                + "\n3/Delete a book\n4/Change Password\n5/Add user \n6/Get All Borrower\n7/Exit\nEnter choice:");

    }

    private void CustomerMenu() {
        System.out.println("Welcome to library");
        System.out.println("1/Borrow a book \n"
                + "2/Book Title Lists\n3/Change Password \n4/Search a book\n5/Exit\nEnter choice:");
    }

    private String getJNDI() {
        String appName = "";
        String moduleName = "EJBLibrary";
        String distinctName = "";
        String sessionBeanName = SessionBean.class.getSimpleName();
        String viewClassName = SessionBeanRemote.class.getName() + "?stateful";

        return "ejb:" + appName + "/" + moduleName + "/" + distinctName + "/" + sessionBeanName + "!" + viewClassName;
    }

    
    public void AllowToBorrowBook(SessionBeanRemote sessionBean) {
        
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter name of borrower : ");
        String name = sc.nextLine();
        
        //Scanner sc = new Scanner(System.in);
        System.out.print("Enter name of book : ");
        String nameBook = sc.nextLine();
        //sessionBean.BorrowBook(nameBook, nameUser);
        String message = sessionBean.AllowBorrowBook(nameBook, name) == true ? "Done!" : "Something wrong.Please try again";
        System.out.println(message);
    }
    
    public void BorrowBook(SessionBeanRemote sessionBean, String nameUser) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter name of book : ");
        String nameBook = sc.nextLine();
        //sessionBean.BorrowBook(nameBook, nameUser);
        String mess="";
        String message = sessionBean.BorrowBook(nameBook, nameUser,mess) == true ? "Done!" : "Something wrong.Please try again";
        System.out.println(mess+"\n");
        System.out.println(message);
    }

    public void BookTitleLists(SessionBeanRemote sessionBean) {
        for (Book object : sessionBean.AllBook()) {
            String mess = object.getAvailable() == true ? "Available" : "Non Circulating";
            System.out.println("Title : " + object.getName() + " Status : " + mess + " Numbers : " + object.getNumbers());
        }
    }

    public void SearchBook(SessionBeanRemote sessionBean) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter name of book : ");
        String nameBook = sc.nextLine();

        List<Book> book = sessionBean.SearchBook(nameBook);
        for (Book b : book) {
            System.out.println(b.getId() + "|" + b.getName());
        }
    }

    public void AddUser(SessionBeanRemote sessionBean) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter name of user : ");
        String nameUser = sc.nextLine();

        System.out.print("Enter pass of user : ");
        String passUser = sc.nextLine();

        System.out.print("Enter name role of user : ");
        String roleUser = sc.nextLine();
        
        String mess="";
        String message = sessionBean.AddUser(nameUser, passUser, roleUser,mess) == true ? "Done!" : "Something wrong.Please try again";

        System.out.println(mess+"\n");
        System.out.println(message);

    }

     public void AllowToReturnBook(SessionBeanRemote sessionBean) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter name of user : ");
        String nameUser = sc.nextLine();
        
        System.out.print("Enter name of book : ");
        String nameBook = sc.nextLine();
        Object mess=0;
        String message = sessionBean.ReturnBook(nameBook, nameUser,mess) == true ? "Done!" : "Something wrong.Please try again";
        
         System.out.println(mess);
        System.out.println(message);
    }
    /*
    public void ReturnBook(SessionBeanRemote sessionBean, String nameUser) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter name of book : ");
        String nameBook = sc.nextLine();
        String message = sessionBean.ReturnBook(nameBook, nameUser) == true ? "Done!" : "Something wrong.Please try again";

        System.out.println(message);
    }
*/
    public void ChangePass(SessionBeanRemote sessionBean, String nameUser) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter new pass : ");
        String userPass = sc.nextLine();
        String message = sessionBean.ChangePass(nameUser, userPass) == true ? "Done!" : "Something wrong.Please try again";
        System.out.println(message);
    }

    public  void AddBook(SessionBeanRemote sessionBean)
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter name of book : ");
        String nameBook = sc.nextLine();
        
        System.out.print("Enter number : ");
        String num = sc.nextLine();
        String message ;
        String regex = "[0-9]+";
        if (num.matches(regex)) {
            message="Wrong";
            System.out.println(message);
            return;
        }
        System.out.print("Enter status (1-true;0-false) : ");
        String status = sc.nextLine();
        if (status.matches(regex)) {
            message="Wrong";
            System.out.println(message);
            return;
        }
        boolean check=false;
        if (status.equals("1")) {
            check=true;
        } else if (status.equals("0")) {
            check=false;    
        }
        else
        {
            message="Wrong";
            System.out.println(message);
            return;
        }
        String mess="";
        message= sessionBean.AddBook(nameBook,check, Integer.parseInt(num),mess) == true ? "Done!" : "Something wrong.Please try again";
        System.out.println(mess+"\n");
        System.out.println(message);
    }
    
    public void UpdateBook(SessionBeanRemote sessionBean) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter name of book : ");
        String nameBook = sc.nextLine();
        String message ;
        System.out.print("Enter number : ");
        String num = sc.nextLine();
        String regex = "[0-9]+";
        if (num.matches(regex)) {
            message="Wrong";
            System.out.println(message);
            return;
        }
        System.out.print("Enter status (1-true;0-false) : ");
        String status = sc.nextLine();
        boolean check=false;
        if (status.matches(regex)) {
            message="Wrong";
            System.out.println(message);
            return;
        }
        if (status.equals("1")) {
            check=true;
        } else if (status.equals("0")) {
            check=false;    
        }
        else
        {
            message="Wrong";
            System.out.println(message);
            return;
        }
        message= sessionBean.UpdateBook(nameBook,check, Integer.parseInt(num)) == true ? "Done!" : "Something wrong.Please try again";
        System.out.println(message);
    }

    public  void AllBorrower(SessionBeanRemote sessionBean)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        List<UserBorrowBook> borrower=sessionBean.AllBorrower();
        for (UserBorrowBook b:borrower) {
            System.out.println("Name : "+b.getUserName()+"| Book : "+b.getBookName()+" | "+"Numbers : "+b.getNumbers()+" | Begin : "+sdf.format(b.getBegin())+" | End : "+sdf.format(b.getEnd()));
        }
    }
            
    
    public void DeleteBook(SessionBeanRemote sessionBean)
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter name of book : ");
        String nameBook = sc.nextLine();
        String message ;
        message=sessionBean.DeleteBook(nameBook)==true? "Done!" : "Something wrong.Please try again";
        System.out.println(message);
    }
    
    public void StaffPanel(Scanner sc, SessionBeanRemote sessionBean, int _choiceMainMenu, String _username)
    {
        
        String regex = "[0-9]+";
        int _choiceMenu = 0;
        while (_choiceMenu != 4) {
            StaffMenu();
            String input = sc.nextLine();
            if (input.matches(regex)) {

                _choiceMenu = Integer.parseInt(input);

            } else {
                System.out.println("Please try again !");
                continue;
            }
            //_choiceMainMenu=Integer.parseInt(sc.nextLine());
            switch (_choiceMenu) {
                case 1:
                    AllowToBorrowBook(sessionBean);
                    //UpdateBook(sessionBean);
                    //System.out.println(sessionBean.getIdBook("name1"));
                    //BorrowBook(sessionBean, _username);
                    break;
                case 2:
                    AllowToReturnBook(sessionBean);
                    //AddBook(sessionBean);
                    //ReturnBook(sessionBean, _username);
                    break;
                
                case 3:
                    ChangePass(sessionBean, _username);
                    break;
                
                //SearchBook(sessionBean);
                default:
                    break;
            }
        }
    }
    
    public void AdminPanel(Scanner sc, SessionBeanRemote sessionBean, int _choiceMainMenu, String _username) {
      
        String regex = "[0-9]+";
        int _choiceMenu = 0;
        while (_choiceMenu != 7) {
            AdminMenu();
            String input = sc.nextLine();
            if (input.matches(regex)) {

                _choiceMenu = Integer.parseInt(input);

            } else {
                System.out.println("Please try again !");
                continue;
            }
            //_choiceMainMenu=Integer.parseInt(sc.nextLine());
            switch (_choiceMenu) {
                case 1:
                    UpdateBook(sessionBean);
                    break;
                case 2:
                    AddBook(sessionBean);
                    //ReturnBook(sessionBean, _username);
                    break;
                case 3:
                    DeleteBook(sessionBean);
                    //BookTitleLists(sessionBean);
                    break;
                case 4:
                    ChangePass(sessionBean, _username);
                    break;
                case 5:
                    AddUser(sessionBean);
                    break;
                case 6:
                    AllBorrower(sessionBean);
                    break;
                //SearchBook(sessionBean);
                default:
                    break;
            }
        }
        //System.out.println("admin");
    }

    public void CustomerPanel(Scanner sc, SessionBeanRemote sessionBean, String _username) {
        String regex = "[0-9]+";
        int _choiceMenu = 0;
        while (_choiceMenu != 5) {
            CustomerMenu();
            String input = sc.nextLine();
            if (input.matches(regex)) {

                _choiceMenu = Integer.parseInt(input);

            } else {
                System.out.println("Please try again !");
                continue;
            }
            //_choiceMainMenu=Integer.parseInt(sc.nextLine());
            switch (_choiceMenu) {
                case 1:

                    BorrowBook(sessionBean, _username);
                    break;
                case 2:
                    BookTitleLists(sessionBean);
                    break;
                case 3:
                    ChangePass(sessionBean, _username);
                    break;
                case 4:
                    SearchBook(sessionBean);
                    break;
                default:
                    break;
            }
        }

    }

    public void Run() {
        try {

            SessionBeanRemote sessionBean = (SessionBeanRemote) ctx.lookup(getJNDI());

            Scanner sc = new Scanner(System.in);
            String _username, _password;
            System.out.println("Log-in to Library");
            System.out.print("Username: ");
            _username = sc.nextLine();
            System.out.print("Password: ");
            _password = sc.nextLine();
            if (_password.hashCode() != sessionBean.getUserPass(_username)) {
                System.err.println("Wrong username/password!");
                return;
            }
            String role = sessionBean.FindRoleUser(_username);
            if (role.equals("admin")) {
                AdminPanel(sc, sessionBean, 0, _username);
            } else if (role.equals("customer")) {
                CustomerPanel(sc, sessionBean, _username);
            }else if(role.equals("staff"))
            {
                StaffPanel(sc, sessionBean, 0, _username);
            }
            /*
            int _choiceMainMenu = 0;
            String regex = "[0-9]+";
            while (_choiceMainMenu != 5) {
                
                CustomerMenu();
                String input = sc.nextLine();
                if (input.matches(regex)) {

                    _choiceMainMenu = Integer.parseInt(input);

                } else {
                    System.out.println("Please try again !");
                    continue;
                }
                //_choiceMainMenu=Integer.parseInt(sc.nextLine());
                switch (_choiceMainMenu) {
                    case 1:
                        SearchBook(sessionBean, _username);
                        //BorrowBook(sessionBean, _username);
                        break;
                    case 2:
                        ReturnBook(sessionBean, _username);
                        break;
                    case 3:
                        BookTitleLists(sessionBean);
                        break;
                    case 4:
                        ChangePass(sessionBean, _username);
                    default:
                        break;
                }*/

            System.out.println("Exit");

            //sessionBean.ReturnBook("name1", "user3");
            /*List<User> user = sessionBean.AllUser();
            for (int i = 0; i < user.size(); i++) {
                System.out.println("User Name:" + i + user.get(i).getName().toString());
            }
            System.out.println("EndUser");
            System.out.println("Book");
            List<Book> book = sessionBean.AllBook();
            for (int i = 0; i < book.size(); i++) {
                System.out.println("Book name:" + book.get(i).getName() + book.get(i).getAvailable()+book.get(i).getNumbers());
            }
            System.out.println("EndBook");*/
        } catch (NamingException ex) {
            Logger.getLogger(EJBTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
