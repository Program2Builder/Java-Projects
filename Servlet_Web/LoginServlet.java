
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet { 
    //private boolean b;

    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        String type = req.getParameter("typeu");
        //String type = "User";
        String user = req.getParameter("mail");
        String password = req.getParameter("userpass");
        HttpSession sion = req.getSession();
        sion.setAttribute("mail", user);
        
        
        try{
            int flag = 0;
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/My_Storage","Santanu","9315");
            Statement stmt = con.createStatement();
            /*String name = "Santanu";
            String pass = "sant123";*/
            if(type.equals("User")){
                ResultSet rs = stmt.executeQuery("select Email,Password,Fist_Name from signup where Email = '"+user+"'");
                
                while(rs.next()){
                	
                    if(rs.getString(1).equals(user) && rs.getString(2).equals(password)){
                        out.println("Welcome "+rs.getString(3));
                        RequestDispatcher disp = req.getRequestDispatcher("/After_Home.html");
                        disp.include(req,res);
                        flag = 1;
                    }else if(flag == 0){
                        out.println("<html><body>"+
                        "<h3><font color='red'>Incorrect Username or Password</font></h3>"+
                        "</body></html>");
                        RequestDispatcher disp = req.getRequestDispatcher("/Login.html");
                        disp.include(req,res);

                    }
                }
            }else{
                if(user.equals("Santanu123@gmail.com") && password.equals("admSAN123")){
                    RequestDispatcher disp = req.getRequestDispatcher("/Transection_History.html");
                    disp.include(req, res);
                }else{
                    out.println("<html><body>"+
                        "<h3><font color='red'>Incorrect Username or Password</font></h3>"+
                        "</body></html>");
                    RequestDispatcher disp = req.getRequestDispatcher("/Login.html");
                    disp.include(req,res);
                }
            }
            con.close();
        }catch(Exception e){
            out.println(e);
        }
    

        //sion.setAttribute("Name", );
    }
}