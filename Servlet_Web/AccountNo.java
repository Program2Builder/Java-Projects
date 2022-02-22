import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.util.Arrays;
import java.util.Random;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccountNo extends HttpServlet{
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException,IOException{
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        String name = req.getParameter("name");
        String fname = req.getParameter("fname");
        String adhaar = req.getParameter("adhaar");
        String email = req.getParameter("mail");
        String contact = req.getParameter("contact");
        String balance = req.getParameter("dipbalance");
        String gender = req.getParameter("gender");
        String actype = req.getParameter("actype");
        String update = "";

        Random rnd = new Random();
        String s = "1234567890";
        char[] otp = new char[11];
        for(int i = 0; i < 11; i++){
            otp[i] = s.charAt(rnd.nextInt(s.length()));
        }
        String[] strarray = new String[otp.length];
        for(int i = 0; i < otp.length; i++){
            strarray[i] = String.valueOf(otp[i]);
        }
        String s1 = Arrays.toString(strarray);
        String res1 = "";
        for(String str: strarray){
            res1+=str;
        }
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/My_Storage","Santanu","9315");
            PreparedStatement pstm = con.prepareStatement("INSERT INTO accountdata values(?,?,?,?,?,?,?,?,?,?)");
            pstm.setString(1,name);
            pstm.setString(2,res1);
            pstm.setString(3,adhaar);
            pstm.setString(4,contact);
            pstm.setString(5,email);
            pstm.setString(6,fname);
            pstm.setString(7,actype);
            pstm.setString(8,balance);
            pstm.setString(9,gender);
            pstm.setString(10,update);
            pstm.executeUpdate();
            
            PreparedStatement ps1 = con.prepareStatement("SELECT * FROM accountdata where Account_No = '"+res1+"'");
            ResultSet rs = ps1.executeQuery();
            while(rs.next()){
                out.println("<br> name = "+rs.getString(1));
                out.println("<br> Account No. = "+rs.getString(2));
                out.println("<br> Adhaar No. = "+rs.getString(3));
                out.println("<br> Contact No. = "+rs.getString(4));
                out.println("<br> Email = "+rs.getString(5));
                out.println("<br> Father's Name = "+rs.getString(6));
                out.println("<br> Account Type = "+rs.getString(7));
                out.println("<br> Available Balance = "+rs.getString(8));
                out.println("<br> Gender = "+rs.getString(9));
            }
            RequestDispatcher disp = req.getRequestDispatcher("After_Home.html");
            disp.include(req,res);
            out.println("Account Created Successfully :)");
            
        }catch(Exception ex){
            out.println(ex);
        }
    }
    
}
