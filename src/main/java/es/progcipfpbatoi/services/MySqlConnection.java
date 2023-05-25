package es.progcipfpbatoi.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnection {

    private static Connection connection;
    private String ip;
    private String database;
    private String userName;
    private String password;


    public MySqlConnection(String ip, String database, String userName, String password) {
        this.ip = ip;
        this.database = database;
        this.userName = userName;
        this.password = password;
    }
    public Connection getConnection() {

        if (connection == null){
            try {
                String dbURL = "jdbc:mysql://" + ip + "/" + database
                        + "?serverTimezone=UTC&allowPublicKeyRetrieval=true";
                Connection connection = DriverManager.getConnection(dbURL,userName,password);
                this.connection = connection;
                System.out.println("Conexión valida: " + connection.isValid(20));
            } catch (SQLException exception) {
                throw new RuntimeException(exception.getMessage());
            }
        }
        return this.connection;
    }

    public void closeConnection() {
        if (connection!= null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

