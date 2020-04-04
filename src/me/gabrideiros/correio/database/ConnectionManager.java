package me.gabrideiros.correio.database;


import org.bukkit.configuration.file.FileConfiguration;

import me.gabrideiros.correio.Main;
import me.gabrideiros.correio.database.type.MySQL;
import me.gabrideiros.correio.database.type.SQLite;

import java.sql.*;

public class ConnectionManager {

	public ConnectionBase principalConnection;

	public ConnectionBase getPrincipalConnection() {
		return principalConnection;
	}

	public ConnectionManager() {
		principalConnection = getMainConnection();
		criarTabela("correio", "uuid varchar(255) NOT NULL, name varchar(255) NOT NULL, data long NOT NULL, owner varchar(255) NOT NULL, items longtext NOT NULL");
	}

	private ConnectionBase getMainConnection() {
		FileConfiguration config = Main.getInstance().getConfig();
		String connectionType = config.getString("MySQL.Type");

		if (connectionType.equalsIgnoreCase("MYSQL")) {
			String host = config.getString("MySQL.Host");
			int port = config.getInt("MySQL.Port");
			String username = config.getString("MySQL.Username");
			String password = config.getString("MySQL.Password");
			String database = config.getString("MySQL.Database");

			return new MySQL(host, port, username, password, database);
		}
		if (connectionType.equalsIgnoreCase("SQLITE")) {
			return new SQLite();
		}

		return null;
	}

	private void criarTabela(String tabela, String colunas) {
		principalConnection.openConnection();
		try {
			Connection connection = principalConnection.getConnection();
			if ((principalConnection.getConnection() != null) && (!connection.isClosed())) {
				Statement stm = connection.createStatement();
				stm.executeUpdate("CREATE TABLE IF NOT EXISTS " + tabela + " (" + colunas + ");");
			}
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro ao salvar o MYSQL!");
		} finally {
			if (principalConnection.getConnection() != null)
				principalConnection.closeConnection();
		}
	}

}
