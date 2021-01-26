package com.geekbrains.client;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileLog {
    private static String pathName = "logs/";
    private static String fileName;
    private  Path path = Paths.get(pathName + fileName);
    private File file = new File(pathName + fileName);


    static public void receiveLogin(String login) {
        fileName = "history_" + login + ".txt";
    }

    public void writeLogs(String str) {
        if (!file.getAbsolutePath().isEmpty()) {
            file.getParentFile().mkdirs();
            try {
                Files.write(path, (str + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

