package com.geekbrains.server;

import java.sql.*;

public class DBconnection implements AutoCloseable {
    private static DBconnection instance;
    private static Connection connection;
    private static PreparedStatement findByLoginAndPass;
    private static PreparedStatement changeNick;
    private DBconnection(){}

    public static DBconnection getInstance(){
        if (instance == null){
            loadDriverAndOpenConnection();
            createPreparedStatements();
            instance = new DBconnection();
        }
        return instance;
    }

    private static void loadDriverAndOpenConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:chat.db");
        }
        catch (ClassNotFoundException | SQLException e){
            System.err.println("Ошибка открытия соеденения с бд");
            e.printStackTrace();
        }
    }

    private static void createPreparedStatements(){
        try {
            findByLoginAndPass = connection.prepareStatement("SELECT * FROM participants WHERE LOWER(login)=LOWER(?) AND password = ?");
            changeNick = connection.prepareStatement("UPDATE  participants SET nickname = ? WHERE nickname = ?");
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public String findByLoginAndPass(String login, String pass){
        ResultSet resultSet = null;
        try {
            findByLoginAndPass.setString(1, login);
            findByLoginAndPass.setString(2, pass);
            resultSet = findByLoginAndPass.executeQuery();
            if (resultSet.next()){
                return resultSet.getString("nickname");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            closeResultSet(resultSet);
        }
        return null;
    }

    private void closeResultSet(ResultSet resultSet){
        if (resultSet != null){
            try {
                resultSet.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public int updateNickname(String oldNickname, String newNickname){
        try {
            changeNick.setString(1,newNickname);
            changeNick.setString(2, oldNickname);
            return changeNick.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
    @Override
    public void close() throws Exception {
        try {
            findByLoginAndPass.close();
            changeNick.close();
            connection.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
