package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection dBConnection;
    private Connection connection;

    private DBConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade","root","1111");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
    public static DBConnection getInstance(){
        if(dBConnection==null){
            dBConnection=new DBConnection();
        }
        return dBConnection;
    }

    public Connection getConnection(){
        return connection;
    }

}
