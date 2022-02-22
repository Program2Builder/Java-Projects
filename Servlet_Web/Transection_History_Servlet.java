import java.io.PrintWriter;
import java.sql.*;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Transection_History_Servlet extends HttpServlet {
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		out.println("<html>"
				+ "<head>"
				+ "<style type = 'text/css'>"
				+ ".tabd{"
				+ "	border-color: blue;"
				+ "background-color: green;"
				+ "color: white;"
				+ "}"
				+ "</style>"
				+ "</head>"
				+ "<body>");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/My_Storage","Santanu","9315");
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery("select * from transacion");
			out.println("<table border='3' align='center' cellpadding='2' class='tabd'>");
			out.println("<tr align='center'>"
					+ "<td colspan='5'>Trancfer History</td>"
					+ "</tr>");
			out.println("<tr align='center'>"
					+ "<td>Name</td>"
					+ "<td>Sender</td>"
					+ "<td>Reciver</td>"
					+ "<td>Amount</td>"
					+ "<td>Date</td>"
					+ "</tr>");
			while(rs.next()) {
				out.println("<tr>"
						+ "<td>"+rs.getString(1)+"</td>"
						+ "<td>"+rs.getString(2)+"</td>"
						+ "<td>"+rs.getString(3)+"</td>"
						+ "<td>"+rs.getString(4)+"</td>"
						+ "<td>"+rs.getString(5)+"</td>"
						+ "</tr>");
			}
			out.println("</table>");
			out.println("<br><br><br>");
			ResultSet rs1 = stm.executeQuery("select * from CreditDetails");
			out.println("<table border='3' align='center' cellpadding='2' class='tabd'>");
			out.println("<tr align='center'>"
					+ "<td colspan='5'>Credit Details</td>"
					+ "</tr>");
			out.println("<tr align='center'>"
					+ "<td>Name</td>"
					+ "<td>Account No.</td>"
					+ "<td>Amount</td>"
					+ "<td>Total Balance</td>"
					+ "<td>Date</td>"
					+ "</tr>");
			while(rs1.next()) {
				out.println("<tr>"
						+ "<td>"+rs1.getString(1)+"</td>"
						+ "<td>"+rs1.getString(2)+"</td>"
						+ "<td>"+rs1.getString(3)+"</td>"
						+ "<td>"+rs1.getString(4)+"</td>"
						+ "<td>"+rs1.getString(5)+"</td>"
						+ "</tr>");
			}
			out.println("</table>");
			out.println("<br><br><br>");
			ResultSet rs2 = stm.executeQuery("select * from DebitDetails");
			out.println("<table border='3' align='center' cellpadding='2' class='tabd'>");
			out.println("<tr align='center'>"
					+ "<td colspan='5'>Debit Details</td>"
					+ "</tr>");
			out.println("<tr align='center'>"
					+ "<td>Name</td>"
					+ "<td>Account No.</td>"
					+ "<td>Amount</td>"
					+ "<td>Total Balance</td>"
					+ "<td>Date</td>"
					+ "</tr>");
			while(rs2.next()) {
				out.println("<tr>"
						+ "<td>"+rs2.getString(1)+"</td>"
						+ "<td>"+rs2.getString(2)+"</td>"
						+ "<td>"+rs2.getString(3)+"</td>"
						+ "<td>"+rs2.getString(4)+"</td>"
						+ "<td>"+rs2.getString(5)+"</td>"
						+ "</tr>");
			}
			out.println("</table>");
		}catch(Exception e) {
			out.println(e);
		}
		out.println("</body></html>");
	}
}
