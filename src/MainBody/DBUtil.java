package MainBody;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class DBUtil {
    static Connection con;
    public static Statement st;
    static PreparedStatement ps;
    public static ResultSet rs;
    static String url="jdbc:mysql://8.134.205.162:3306/stu";
    static String name="root";
    static String pwd="135246";
    public static void initst() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection(url,name,pwd);
            st=con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void initps(String sql) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection(url,name,pwd);
            ps=con.prepareStatement(sql);
            rs= ps.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void closeDB() {
        try {
            if (rs!=null) {
                rs.close();
            }
            if (ps!=null) {
                ps.close();
            }
            if (st!=null) {
                st.close();
            }
            if (con!=null) {
                con.close();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}





















