package sample;
import java.sql.*;

public class Database {

    public String username;
    public String password;
    public String dbName;
    public String dbUrl;
    public String port;

    public Database(String username, String password, String dbName, String dbUrl, String port) {
        this.username = username;
        this.password = password;
        this.dbName = dbName;
        this.dbUrl = dbUrl;
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String url) {
        this.dbUrl = dbUrl;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Connection dbConnection(String username, String password, String dbName, String dbUrl, String port) throws ClassNotFoundException, SQLException {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + dbUrl + ":"+ port + "/" + dbName, username, password);
            return con;
    }

  /*  public String dbQuery(Connection con, String statement) throws SQLException {

        Statement stmt=con.createStatement();
        ResultSet rs=stmt.executeQuery(statement);
        while(rs.next())
            System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));


        return result;
    }*/

}
