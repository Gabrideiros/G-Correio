package me.gabrideiros.correio.database;

import java.sql.Connection;

public abstract class ConnectionBase {

    public Connection connection;

    abstract public void openConnection();

    abstract public void closeConnection();

    public Connection getConnection() {
        return connection;
    }
}
