import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class Servlet1 implements Servlet{

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public ServletConfig getServletConfig() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getServletInfo() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void init(ServletConfig arg0) throws ServletException {
        System.out.println("Hello Server");
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        String fname,lname,email,contact,password,gender,country;
        fname = req.getParameter("fname");
        lname = req.getParameter("lname");
        email = req.getParameter("mail");
        contact = req.getParameter("contact");
        password = req.getParameter("userpass");
        gender = req.getParameter("gender");
        country = req.getParameter("Country");

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/My_Storage","Santanu","9315");
            PreparedStatement ps = conn.prepareStatement("insert into signup values(?,?,?,?,?,?,?)");
            ps.setString(1, fname);
            ps.setString(2, lname);
            ps.setString(3, email);
            ps.setString(4, contact);
            ps.setString(5, password);
            ps.setString(6, gender);
            ps.setString(7, country);

            int execute = ps.executeUpdate();
            if(execute > 0){
                RequestDispatcher dispatcher = req.getRequestDispatcher("Login.html");
                dispatcher.include(req, res);
            }
        }catch(Exception ex){
            out.println(Arrays.toString(ex.getStackTrace()));
        }

        
    }
    
}