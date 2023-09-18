package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class Email_Client {
    public static void main(String[] args) throws IOException {

        // get the current working directory and create 'emails' folder if not exists.
        String folderPath = System.getProperty("user.dir")+"\\emails";
        Files.createDirectories(Paths.get(folderPath));

        // create clients.txt file for storing recipient information if not exists.
        String filePath = folderPath+"\\clientList.txt";
        File clientList = new File(filePath);
        if(!clientList.exists()) {
            FileOutputStream clientListFile = new FileOutputStream(filePath);
            clientListFile.close();
        }

        // get factory for creating recipients, containers for storing recipients and emails.
        RecipientFactory recipient_factory = new RecipientFactory();
        Container emailContainer = EmailContainer.getEmailContainer();
        RecipientsContainer recipientsContainer = RecipientsContainer.getRecipientContainer();

        // read through the clients text file and add recipients for its information.
        recipientsContainer.readRecipientList(filePath,recipient_factory);

        // send birthday wishes accordingly.
        for (Iterator itr = recipientsContainer.getIterator("birthdays");itr.hasNext();){
            Recipient recipient = (Recipient) itr.next();
            String subject = "Birthday wishes";
            String myName = "Themira";
            String body = null;
            if (recipient instanceof Office_friend){
                body = "Wish you a Happy Birthday. " + myName;
            }else if (recipient instanceof Personal){
                body = "hugs and love on your birthday. " + myName;
            }
            new Email(recipient.getEmail(),subject,body).send();
        }


        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter option type: \n"
                + "1 - Adding a new recipient\n"
                + "2 - Sending an email\n"
                + "3 - Printing out all the recipients who have birthdays\n"
                + "4 - Printing out details of all the emails sent\n"
                + "5 - Printing out the number of recipient objects in the application");

        String option = scanner.nextLine();

        switch (option) {
            case "1" -> {
                // input format - Official: nimal,nimal@gmail.com,ceo
                // Use a single input to get all the details of a recipient
                // code to add a new recipient
                // store details in clientList.txt file
                // Hint: use methods for reading and writing files
                String details = scanner.nextLine();
                Recipient recipient = recipient_factory.getRecipient(details);
                recipientsContainer.addRecipient(recipient);
                Path path = Paths.get(filePath);
                Files.write(path, (details + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
            }
            case "2" -> {
                // input format - email, subject, content
                // code to send an email
                String email_details = scanner.nextLine();
                String[] email_components = email_details.split(", ");
                Email newEmail = new Email(email_components[0], email_components[1], email_components[2]);
                newEmail.send();
            }
            case "3" -> {
                // input format - yyyy/MM/dd (ex: 2018/09/17)
                // code to print recipients who have birthdays on the given date
                String birthdate = scanner.nextLine();
                Iterator itr = recipientsContainer.getIterator(birthdate);
                while (itr.hasNext()){
                    Recipient recipient = (Recipient) itr.next();
                    System.out.println(recipient.getName());
                }
            }
            case "4" -> {
                // input format - yyyy/MM/dd (ex: 2018/09/17)
                // code to print the details of all the emails sent on the input date
                // printing time sent, to, subject and body.
                String date = scanner.nextLine();
                Iterator itr = emailContainer.getIterator(date);
                while (itr.hasNext()){
                    Email email = (Email) itr.next();
                    System.out.println("time: " + email.getTimeSent().replace('_', ':'));
                    System.out.println("to: " + email.getTo());
                    System.out.println("Subject: " + email.getSubject());
                    System.out.println("Body: " + email.getBody() + "\n");
                }

            }
            case "5" ->
                    // code to print the number of recipient objects in the application
                    System.out.println(RecipientsContainer.getRecipientCount());
        }

        // start email client
        // code to create objects for each recipient in clientList.txt
        // use necessary variables, methods and classes

    }
}
