package com.onimko;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {
    static final Logger log = LoggerFactory.getLogger(Main.class);
    static final String KEY_PROP = "username";
    static final String FILE_PROP = "hello.xml";

    public static void main(String[] args) {
        String conf = "";
        if (args.length > 0) conf = args[0];
        Message message = new Message("Привіт " + getProperty(KEY_PROP) + "!");
        try {
            System.out.println(getResultString(message, conf));
        } catch (JsonProcessingException e) {
            log.error("Can't create JSON");
            throw new RuntimeException(e);
        }
    }


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

    public static String getResultString (Message msg, String conf) throws JsonProcessingException {
        String result = "Try again! Wrong args!";
        if (conf.equals("-xml")) {
            log.info("Needs XML");
            XmlMapper xmlMapper = new XmlMapper();
            result = xmlMapper.writeValueAsString(msg);
        }
        if (conf.equals("-json")) {
            log.info("Needs Json");
            ObjectMapper mapper = new ObjectMapper();
            result = mapper.writeValueAsString(msg);
        }
        return result;
    }
}
