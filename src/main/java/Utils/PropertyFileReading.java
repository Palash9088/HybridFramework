package Utils;//import java.util.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyFileReading {
    private Properties prop;
    //constructor parameter
    public PropertyFileReading(String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream fileInputStream =  new FileInputStream(file);

        prop = new Properties();
        prop.load(fileInputStream);
    }
    public String getPropertyValue(String key){
        String value =  prop.getProperty(key);
        if(value == null)
        {
            throw new NullPointerException();
        }
        return value;
    }

}
