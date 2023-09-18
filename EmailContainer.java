package com.company;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

// contains deserialized email objects for the given date.
public class EmailContainer implements Container{

    private static final ArrayList<Email> emails = new ArrayList<>();
    private static final EmailContainer emailContainer = new EmailContainer();

    // singleton pattern.
    private EmailContainer(){}

    public static EmailContainer getEmailContainer(){
        return emailContainer;
    }

    // deserialize and add Email objects to the list.
    private void addEmails(String date){
        // get the path of the folder for the given date.
        String path = System.getProperty("user.dir") + "\\emails\\" + date.replace('/','_') + "\\";
        File emailSet = new File(path);

        try {
            // if that folder exists and that folder contains any files deserialize.
            if (emailSet.exists() && Files.list(Paths.get(path)).findAny().isPresent()) {
                // get the list of files in the folder.
                String[] fileNames = emailSet.list();
                for (String fileName : fileNames) {
                    FileInputStream fileInputStream = null;
                    ObjectInputStream objectInputStream = null;
                    try {
                        // deserialization and adding email objects to an arraylist.
                        fileInputStream = new FileInputStream(path + fileName);
                        objectInputStream = new ObjectInputStream(fileInputStream);
                        Email recipient = (Email) objectInputStream.readObject();
                        emails.add(recipient);
                    } finally {
                        // close streams.
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if (objectInputStream != null) {
                            try {
                                objectInputStream.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    // provide an iterator to iterate through the list containing Recipient objects.
    @Override
    public Iterator getIterator(String date){
        addEmails(date);
        return new EmailIterator();
    }

    // inner class for iteration in iterator pattern.
    public static class EmailIterator implements Iterator{

        private int index;

        @Override
        public boolean hasNext() {
            return index < emails.size();
        }

        @Override
        public Object next() {
            if (this.hasNext()){
                return emails.get(index++);
            }
            return null;
        }
    }

}
