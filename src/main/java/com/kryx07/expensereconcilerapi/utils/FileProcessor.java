package com.kryx07.expensereconcilerapi.utils;

import org.apache.log4j.Logger;

import java.io.*;

/**
 * Created by wd40 on 15.04.17.
 */
public class FileProcessor<T>{

    private String fileName;

    public FileProcessor(String fileName) {
        this.fileName = fileName;
    }

    final static Logger logger = Logger.getLogger(FileProcessor.class);


    public void save(T object) {

        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);) {
            objectOutputStream.writeObject(object);
        } catch (IOException e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
        }

        logger.info("Serialized data is saved in " + fileName );
        System.out.printf("Serialized data is saved in " + fileName + System.lineSeparator());
    }

    public T readAll() {

        T object = null;
        try (FileInputStream fileIn = new FileInputStream(fileName);
             ObjectInputStream in = new ObjectInputStream(fileIn);) {
            object = (T) in.readObject();
        } catch (FileNotFoundException | NullPointerException e) {
            logger.debug(e.getMessage() + "The fileName doesn't exist yet - no source to readAll from.");
        } catch (IOException i) {
            logger.debug(i.getMessage());
        } catch (ClassNotFoundException c) {
            logger.debug(c.getMessage() + "Books class not found");
        }
        return object;
    }
}
