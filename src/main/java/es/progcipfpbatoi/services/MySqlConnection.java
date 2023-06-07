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


    /**
     * Constructor de la clase, obligatorio ponerlo, ya que si no se pone nos da warning
     * Tenemos 4 parámetros:
     * ip -> de la conexión
     * database -> el nombre de la base de datos
     * username -> el nombre de usuario con el que acceder a la base de datos
     * password -> contraseña del user que accede a la base de datos
     */
    public MySqlConnection(String ip, String database, String userName, String password) {
        this.ip = ip;
        this.database = database;
        this.userName = userName;
        this.password = password;
    }

    /**
     * Si la conexión es nula, lo que hace es crear una conexión con los atributos establecidos
     * en el constructor. Nos muestra mensaje de que la conexión ha sido válida y nos devuelve
     * el atributo connection
     *
     * @return Connection
     */
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

    /**
     * Se encarga de cerrar la conexión sobre la base de datos
     * Siempre y cuando la conexion sea diferente de null
     *
     * @return void
     * @throws SQLException
     */
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

