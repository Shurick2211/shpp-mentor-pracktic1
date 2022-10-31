package com.onimko;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The Main class for App.
 */
public class Main {
    /**The logger for App*/
    static final Logger log = LoggerFactory.getLogger(Main.class);
    /**The key for getting value*/
    static final String KEY_PROP = "username";
    /**The name of property-file*/
    static final String FILE_PROP = "hello.xml";

    /**
     * The start method.
     * @param args The command for App ("json" or "xml").
     */
    public static void main(String[] args) {
        //check push
        String conf = System.getProperty("file");
        if (conf == null){
            System.out.println("Try again! No system var for result "
                + "(-Dfile=xml or -Dfile=json)!");
            log.warn("No system var for result!");
            System.exit(1);
        }
        Message message = new Message("Привіт " + getProperty(KEY_PROP) + "!");
        try {
            System.out.println(getResultString(message, conf));
        } catch (JsonProcessingException e) {
            log.error("Can't create JSON");
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * The method for get property's value for an input key.
     * @param inProp - the input key.
     * @return the value.
     */
    public static String getProperty(String inProp) {
        InputStream rootPath = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(FILE_PROP);
        Properties xmlProps = new Properties();
        try {
            xmlProps.loadFromXML(rootPath);
        } catch (IOException e) {
            log.error("File " + FILE_PROP + " not found!");
            throw new RuntimeException(e);
        }
        String outProp = xmlProps.getProperty(inProp);
        log.debug("Property was reading: " + inProp + "=" + outProp);
        return outProp;
    }

    /**
     * The method for getting the serialized string with an input config.
     * @param msg the input object of POJO-class.
     * @param conf the input string config.
     * @return the result string.
     * @throws IOException the Exception for process of serialize
     * and creating the result's file.
     */
    public static String getResultString (Message msg, String conf) throws IOException {
        String result = "Try again! Wrong system var!";
        if (conf.equals("xml") || conf.equals("-x")) {
            log.info("Needs XML");
            XmlMapper xmlMapper = new XmlMapper();
            result = xmlMapper.writeValueAsString(msg);
            xmlMapper.writeValue(new File("output.xml"),msg);
        } else
        if (conf.equals("json") || conf.equals("-j")) {
            log.info("Needs Json");
            ObjectMapper mapper = new ObjectMapper();
            result = mapper.writeValueAsString(msg);
            mapper.writeValue(new File("output.json"),msg);
        } else log.warn("Wrong command!");
        return result;
    }
}
