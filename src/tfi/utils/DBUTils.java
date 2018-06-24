package tfi.utils;

/**
* 
*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tfi.exceptions.TfiException;
import tfi.exceptions.TfiExceptionMessages;

/**
 * @author absin
 *
 */
public class DBUTils {
	private final static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(DBUTils.class);
	// JDBC driver name and database URL
	private Connection conn = null;

	public void executeUpdate(String sql) throws TfiException {
		if (conn != null) {
			Statement stmt = null;
			try {
				log.info("Creating statement...");
				stmt = conn.createStatement();
				stmt.executeUpdate(sql);
			} catch (SQLException se) {
				// Handle errors for JDBC
				se.printStackTrace();
				throw new TfiException(TfiExceptionMessages.ExecuteUpdateFailed);
			} catch (Exception e) {
				// Handle errors for Class.forName
				e.printStackTrace();
				throw new TfiException(TfiExceptionMessages.ExecuteUpdateFailed);
			} finally {
				// finally block used to close resources
				try {
					if (stmt != null)
						stmt.close();
				} catch (SQLException se) {
				} // do nothing

			} // end try
		}
	}

	public void finalize() {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} // end finally try
	}

	public DBUTils() throws TfiException {

		// STEP 2: Register JDBC driver
		try {
			Class.forName(TfiConstants.getAnyProp(TfiConstants.props.JDBC_DRIVER_PG));
			if (conn == null) {
				openConnection();
			} else if (conn.isClosed()) {
				openConnection();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new TfiException(e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new TfiException(e.getMessage());
		}

	}

	private void openConnection() throws SQLException, TfiException {
		// STEP 3: Open a connection
		log.info("Connecting to a selected database...");
		conn = DriverManager.getConnection(TfiConstants.getAnyProp(TfiConstants.props.DB_URL_PG),
				TfiConstants.getAnyProp(TfiConstants.props.USERPG), TfiConstants.getAnyProp(TfiConstants.props.PASSPG));
		log.info("Connected database successfully...");
	}

	public List<HashMap<String, Object>> executeQuery(String sql) throws TfiException {
		List<HashMap<String, Object>> results = new ArrayList<>();
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver
			log.info("Creating statement...");
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			List<String> columnNames = new ArrayList<>();
			for (int i = 1; i <= rsmd.getColumnCount(); i++)
				columnNames.add(rsmd.getColumnName(i));

			while (rs.next()) {
				HashMap<String, Object> result = new HashMap<>();
				for (String columnName : columnNames) {
					result.put(columnName, rs.getObject(columnName));
				}
				results.add(result);
			}
			rs.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
			throw new TfiException(se.getMessage());

		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
			throw new TfiException(e.getMessage());
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
				throw new TfiException(se.getMessage());
			} // end finally try
		}
		return results;
	}

}