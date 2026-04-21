package file_utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class FileUtility {
public String getDataFromPropertiesFile(String key) throws IOException {
	// Remove the project name and the leading backslash
	FileInputStream fis = new FileInputStream("src/test/resources/configurations/ConfigEnvData.properties");
	Properties prop=new Properties();
	prop.load(fis);
	String data=prop.getProperty(key);
	return data;
			
}
}
