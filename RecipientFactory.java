package com.company;

public class RecipientFactory {

    // Create Recipient objects for the given description.
    // Example description: Official: nimal,nimal@gmail.com,ceo.
    public Recipient getRecipient(String details){
        if (details == null){
            return null;
        }
        String[] components = details.split(": |,");
        switch (components[0]){
            case "Official" -> {
                return new Official(components[1], components[2], components[3]);
            }
            case "Office_friend" -> {
                return new Office_friend(components[1], components[2], components[3], components[4]);
            }
            case "Personal" -> {
                return new Personal(components[1], components[2], components[3], components[4]);
            }
        }
        return null;
    }
}
