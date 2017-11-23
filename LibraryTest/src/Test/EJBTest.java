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
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private void MainMenu() {
        System.out.println("Welcome to library");
        System.out.println("1/Borrow a book \n2/Return a book"
                + "\n3/Book Title Lists\n4/Change Password \n5/Exit\nEnter choice:");
    }

    private String getJNDI() {
        String appName = "";
        String moduleName = "Library";
        String distinctName = "";
        String sessionBeanName = SessionBean.class.getSimpleName();
        String viewClassName = SessionBeanRemote.class.getName() + "?stateful";

        return "ejb:" + appName + "/" + moduleName + "/" + distinctName + "/" + sessionBeanName + "!" + viewClassName;
    }

    public void BorrowBook(SessionBeanRemote sessionBean, String nameUser) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter name of book : ");
        String nameBook = sc.nextLine();
        //sessionBean.BorrowBook(nameBook, nameUser);
        String message = sessionBean.BorrowBook(nameBook, nameUser) == true ? "Done!" : "Something wrong.Please try again";
        System.out.println(message);
    }

    public void BookTitleLists(SessionBeanRemote sessionBean) {
        for (Book object : sessionBean.AllBook()) {
            String mess = object.getAvailable() == true ? "Available" : "Non Circulating";
            System.out.println("Title : " + object.getName() + " Status : " + mess+" Numbers : "+object.getNumbers());
        }
    }

    public void ReturnBook(SessionBeanRemote sessionBean, String nameUser) {
        

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter name of book : ");
        String nameBook = sc.nextLine();
        String message = sessionBean.ReturnBook(nameBook, nameUser) == true ? "Done!" : "Something wrong.Please try again";

        System.out.println(message);
    }

    public  void ChangePass(SessionBeanRemote sessionBean,String nameUser)
    {
        
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter new pass : ");
        String userPass = sc.nextLine();
        String message = sessionBean.ChangePass(nameUser, userPass) == true ? "Done!" : "Something wrong.Please try again";
        System.out.println(message);
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

            int _choiceMainMenu = 0;
            String regex = "[0-9]+";
            while (_choiceMainMenu != 5) {

                MainMenu();
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
                        BorrowBook(sessionBean, _username);
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
                }
            }

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


