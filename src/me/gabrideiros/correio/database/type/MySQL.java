package me.gabrideiros.correio.database.type;


import java.sql.DriverManager;
import java.sql.SQLException;

import me.gabrideiros.correio.database.ConnectionBase;

public class MySQL extends ConnectionBase {

    private String host;
    private int port;
    private String username;
    private String password;
    private String database;
    private int query;


    public MySQL(String host, int port, String username, String password, String database) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.database = database;
        this.query = 0;
    }

    @Override
    public void openConnection() {
        try {
            query++;
            if ((connection != null) && (!connection.isClosed()))
                return;

            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false", username, password);
        } catch (ClassNotFoundException | SQLException e) {
            query--;
            e.getStackTrace();
            System.out.println(
                    "Ocorreu um erro ao abrir a conexão!");
        }
    }

    @Override
    public void closeConnection() {
        query--;
        if (query <= 0) {
            try {
                if (connection != null && !connection.isClosed())
                    connection.close();
            } catch (Exception e) {
                System.out.println(
                        "Houve um erro ao fechar a conexão!");
            }
        }
    }
}
