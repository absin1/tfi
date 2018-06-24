package tfi.utils;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import tfi.exceptions.TfiException;
import tfi.exceptions.TfiExceptionMessages;

public class TfiConstants {
	private static Logger logger = Logger.getLogger(TfiConstants.class.getName());

	public static String getAnyProp(props props) throws TfiException {
		String path = "";
		Properties prop = new Properties();
		try {
			prop.load(TfiConstants.class.getClassLoader().getResourceAsStream("app.properties"));
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new TfiException(TfiExceptionMessages.LoadingPropertiesFailed);
		}
		path = prop.getProperty(props.toString());
		return path;
	}

	enum props {
		JDBC_DRIVER_PG, DB_URL_PG, USERPG, PASSPG
	}
}