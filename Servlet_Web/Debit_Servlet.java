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


public class Debit_Servlet extends HttpServlet{
    Connection con;
	Statement stm,stm1;
	ResultSet rs,rs1;
	PreparedStatement ps;
	long amount;
	long totalbalance;
	long prebalance;
	String balancedebit,balancetotal;
	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		out.println("<html><body>");
		
		String account = req.getParameter("account");
		amount = Long.parseLong(req.getParameter("rs"));
		
		Connection con;
		Statement stm;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/My_Storage","Santanu","9315");
			stm = con.createStatement();
			ResultSet rs = stm.executeQuery("select Balance from accountdata where Account_No = '"+account+"'");
			while(rs.next()) {
				prebalance = Long.parseLong(rs.getString(1));
				if(amount <= prebalance) {
					totalbalance = prebalance - amount;
				
					stm1 = con.createStatement();
					stm1.executeUpdate("update accountdata set Balance = '"+totalbalance+"' where Account_No = '"+account+"'");
				
					out.println("Total Balance In Your Account is: "+totalbalance+"Rs.");
					RequestDispatcher disp = req.getRequestDispatcher("After_Home.html");
					disp.include(req, res);
					
					rs1 = stm.executeQuery("select name,Balance from accountdata where Account_No = '"+account+"'");
					while(rs1.next()) {
						balancedebit = String.valueOf(amount);
						SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd");  
						Date date = new Date();
						String date1 = formatter.format(date);
						ps = con.prepareStatement("insert into debitdetails values(?,?,?,?,?)");
						ps.setString(1, rs1.getString(1));
						ps.setString(2, account);
						ps.setString(3, balancedebit);
						ps.setString(4, rs1.getString(2));
						ps.setString(5, date1);
						ps.execute();
					}
				}else {
					out.println("You Have Not Suffecient Balance");
					RequestDispatcher disp = req.getRequestDispatcher("After_Home.html");
					disp.include(req, res);
				}
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		out.println("</body></html>");
	}
	
}
