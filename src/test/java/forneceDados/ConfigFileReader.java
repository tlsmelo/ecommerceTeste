package forneceDados;

import com.google.common.base.Strings;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigFileReader {

    private static final String PROPERTY_FILE_PATH = "configs"+ File.separator + "Configuration.properties";

    private final Properties configProperties;

    private static ConfigFileReader instance;

    public static ConfigFileReader getInstance(){
        return instance == null ? instance = new ConfigFileReader() : instance;
    }

    private ConfigFileReader(){
        try{
            try (BufferedReader reader = Files.newBufferedReader(Paths.get(PROPERTY_FILE_PATH))){
                configProperties = new Properties();
                configProperties.load(reader);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Arquivo de configuração não encontrado no " + PROPERTY_FILE_PATH);
        }
    }

    private String readProperty(String property){
        String propertyValue= configProperties.getProperty(property);
        if(Strings.isNullOrEmpty(propertyValue)){
            throw new RuntimeException(property + "não especificado no arquivo de configuração");
        }
        else{
            return propertyValue;
        }
    }

    public String getEletrumUrl() {
        return readProperty("eletrum_url");
    }

    public String getCliente1(){ return readProperty("cliente1");}

    public String getCliente2(){ return readProperty("cliente2");}

}
