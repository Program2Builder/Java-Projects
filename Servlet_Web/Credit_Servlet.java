
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Credit_Servlet extends HttpServlet{
    
    ResultSet rs,rs1;
	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		
		String account = req.getParameter("account");
		String amount = req.getParameter("rs");
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/My_Storage","Santanu","9315");
			Statement stm = con.createStatement();
			rs = stm.executeQuery("select Balance from accountdata where Account_No = '"+account+"'");
			while(rs.next()) {
				long amount1 = Long.parseLong(amount);
				long amount2 = Long.parseLong(rs.getString(1));
                
				long result = amount2 + amount1;
				PreparedStatement ps = con.prepareStatement("update accountdata set Balance = '"+result+"' where Account_No = '"+account+"'");
				ps.executeUpdate();
				
				rs1 = stm.executeQuery("select name,Balance from accountdata where Account_No = '"+account+"'");
				while(rs1.next()) {
					SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd");  
					Date date = new Date();
					String date1 = formatter.format(date);
					PreparedStatement ps1 = con.prepareStatement("insert into creditdetails values(?,?,?,?,?)");
					String name = rs1.getString(1);
					String balance = rs1.getString(2);
					ps1.setString(1, name);
					ps1.setString(2, account);
					ps1.setString(3, amount);
					ps1.setString(4, balance);
					ps1.setString(5, date1);
					int action = ps1.executeUpdate();
				
				    if(action > 0) {
				    	out.println("Total Balance In Your Account Is: "+result+"Rs.");
				    	RequestDispatcher disp = req.getRequestDispatcher("After_Home.html");
				    	disp.include(req, res);
				    }else {
				    	out.println("Something Went Wrong");
				    	RequestDispatcher disp = req.getRequestDispatcher("Credit.html");
				    	disp.include(req, res);
				    }
                }
			}
        
		}catch(Exception e) {
			System.out.println(e);
		}
	}
}
