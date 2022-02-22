import java.sql.Connection;
import java.sql.DriverManager;

public class SQLConnection {
    public static Connection getConnection(){
        Connection con = null;
        if(con == null){
            try{
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost/My_Storage","Santanu","9315");
            }catch(Exception e){}
        }
        return con;
    }
}
