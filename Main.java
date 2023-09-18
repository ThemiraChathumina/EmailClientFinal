package com.company;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) throws IOException {
        String date = "2022_08_12";
        String path = System.getProperty("user.dir") + "\\emails\\" + date + ".ser";

        FileOutputStream output_file = output_file = new FileOutputStream(path);
        ObjectOutputStream output = output = new ObjectOutputStream(output_file);
        output.writeObject(new Email("akf","b","c"));
        output.writeObject(new Email("aawdfakawdwf","bqwdqwdqwd","c"));
        output.writeObject(new Email("akfqwsdaawdq","b","cqwd"));

        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            ArrayList<Email> emails = new ArrayList<>();
            fileInputStream = new FileInputStream(path);
            objectInputStream = new ObjectInputStream(fileInputStream);
            boolean exist = true;
            while (exist) {
                if (fileInputStream.available() != 0) {
                    Email email = (Email) objectInputStream.readObject();
                    System.out.println(email);
                    emails.add(email);
                } else {
                    exist = false;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}