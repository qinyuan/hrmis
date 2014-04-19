package qinyuan.lib.db;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class MysqlDs {
	private MysqlDs() {
	}

	private static ComboPooledDataSource ds;

	private static void initialize() {
		ds = new ComboPooledDataSource();
		ds.setUser("root");
		ds.setPassword("qinyuan");
		ds.setJdbcUrl("jdbc:mysql://localhost:3306");
		try {
			ds.setDriverClass("com.mysql.jdbc.Driver");
		} catch (PropertyVetoException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		ds.setInitialPoolSize(2);
		ds.setMinPoolSize(1);
		ds.setMaxPoolSize(10);
		ds.setMaxStatements(50);
		ds.setMaxIdleTime(60);
	}

	public static Connection getConnection() throws SQLException {
		if (ds == null) {
			initialize();
		}
		return ds.getConnection();
	}

	public static void main(String[] args) throws SQLException {
		Connection cnn = getConnection();
		cnn.close();
	}
}
