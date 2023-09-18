package com.company;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

public class Email implements java.io.Serializable {

    private String to;
    private String subject;
    private String body;
    private String timeSent;

    // details for the email sending protocol.
    private static final String host = "smtp.gmail.com";
    private static final String port = "587";
    private static final String user = "chathuminathemira@gmail.com";
    private static final String password = "ixoplcoxxvxuoidw";

    public Email(String to, String subject, String body) {
        this.to = to;
        this.subject = subject;
        this.body = body;
    }

    // getters and setters.
    public String getFrom() {
        return user;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTimeSent(){
        return this.timeSent;
    }

    // send the email.
    public void send() {
        Properties email_properties = new Properties();
        email_properties.put("mail.smtp.auth", "true");
        email_properties.put("mail.smtp.starttls.enable", "true");
        email_properties.put("mail.smtp.host", Email.host);
        email_properties.put("mail.smtp.port", port);
        Session newSession = Session.getInstance(email_properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });
        try {
            Message new_message = new MimeMessage(newSession);
            new_message.setFrom(new InternetAddress(user));
            new_message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            new_message.setSubject(subject);
            new_message.setText(body);
            Transport.send(new_message);
            this.timeSent = new SimpleDateFormat("HH_mm_ss").format(Calendar.getInstance().getTime());
            // serialize automatically after sending the email.
            this.serialize(this.timeSent);
        } catch (MessagingException messagingException) {
            throw new RuntimeException(messagingException);
        }
    }

    // serialize each email object with name set to the time it delivered.
    // saves each email object in a folder for the day it sent. folder name: yyyy_MM_dd, File name: HH_mm_ss
    // accepts time in HH_mm_ss format.
    private void serialize(String current_time) {
        String current_date = new SimpleDateFormat("yyyy_MM_dd").format(Calendar.getInstance().getTime());
        String path = System.getProperty("user.dir") + "\\emails\\" + current_date + "\\";
        String pathName = path + current_time + ".ser";

        // create a folder with current date if not exists.
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdir();
        }
        FileOutputStream output_file = null;
        ObjectOutputStream output = null;
        try {
            // serialization
            output_file = new FileOutputStream(pathName);
            output = new ObjectOutputStream(output_file);
            output.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // close streams.
            if (output != null) {
                try {
                    output.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (output_file != null) {
                try {
                    output_file.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
