package AI.Bot.model;

import java.sql.*;

public class DBConnection {

    public Connection getConnection() throws SQLException {
//        String path = getClass().getClassLoader().getResource("database.db").toString().replaceAll("file:/","");
        String connect = "jdbc:sqlite:./database.shafiq";
        return DriverManager.getConnection(connect, "", "");
    }

    public static String resp(String question) {
        try {
            Connection con = new DBConnection().getConnection();
            String SQL = "SELECT answer FROM storage WHERE question='"+question+"'";
            PreparedStatement stmt = con.prepareStatement(SQL);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                return rs.getString("answer");
            }
            con.close();
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "";
    }
    public static void insertWord(String question , String answer){
        try {
            Connection con = new DBConnection().getConnection();
            String SQL = "INSERT INTO storage (id,question,answer) VALUES(null,?,?)";
            PreparedStatement stmt = con.prepareStatement(SQL);
            stmt.setString(1,question);
            stmt.setString(2,answer);
            stmt.execute();
            System.out.println("Saved!!!");
            con.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void createDB(){
        String connect = "jdbc:sqlite:./database.shafiq";
        try {
            DriverManager.getConnection(connect, "", "");
            new DBConnection().createTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    private void createTable(){
        String sql = "CREATE TABLE IF NOT EXISTS storage (\n"
                + "	id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "	question text NOT NULL,\n"
                + "	answer text NOT NULL\n"
                + ");";
        try(Connection con = new DBConnection().getConnection()){
            PreparedStatement statement = con.prepareStatement(sql);
            statement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
