package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.*;

// container class for Recipient objects.
public class RecipientsContainer implements Container {

    // following hash table stores recipient objects for the given key.
    // there are 3 kinds of keys.
    // first key is "birthdays". It is a list of recipients to whom a birthday greeting should be sent on current day.
    // second key is "other". It contains recipient objects without birthdays. (Official)
    // 3rd type stores recipients who have birthdays on the given date.That date is given as the key.format: "yyyy/MM/dd"
    private static final HashMap<String, ArrayList<Recipient>> recipients = new HashMap<>();
    private static int recipient_count;
    private static final RecipientsContainer recipientContainer = new RecipientsContainer();

    static {
        recipients.put("other",new ArrayList<Recipient>());
        recipients.put("birthdays",new ArrayList<Recipient>());
        recipient_count = 0;
    }

    // singleton pattern
    private RecipientsContainer(){}

    public static RecipientsContainer getRecipientContainer(){
        return recipientContainer;
    }

    // get the total number of recipient objects currently in the application.
    public static int getRecipientCount(){
        return recipient_count;
    }

    // adds a recipient to the relevant list of the hash table.
    // if it is a BirthdayRecipient, add to the list that has the birthday as the key.
    // if that recipient has birthday on current day, also add to the "birthdays" list in hash table.
    // else add to the "others" list in the hash table.
    public void addRecipient(Recipient recipient){
        String current_date = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
        if (recipient instanceof BirthdayRecipient){
            String birthday = ((BirthdayRecipient) recipient).getBirthday();
            // if there is a list for the birthday, add to it, otherwise create a new one and add to it.
            if (recipients.containsKey(birthday)){
                recipients.get(birthday).add(recipient);
            }else{
                ArrayList<Recipient> temp = new ArrayList<>();
                temp.add(recipient);
                recipients.put(birthday,temp);
            }
            String path = System.getProperty("user.dir") + "\\emails\\" + current_date.replace('/','_') + "\\";
            // if current date is equal to the birthday and if already a wish has not been sent, then add to "birthdays" list.
            if (current_date.substring(5).equals(birthday.substring(5)) && !(new File(path).exists())){
                recipients.get("birthdays").add(recipient);
            }
        }else {
            recipients.get("other").add(recipient);
        }
        recipient_count++;
    }

    // reading the clients.txt file and add its content to the container.
    public void readRecipientList(String path, RecipientFactory recipient_factory) throws FileNotFoundException {
            File Recipients_list = new File(path);
            Scanner scanner = new Scanner(Recipients_list);
            String recipient_line;
            while(scanner.hasNextLine()) {
                recipient_line = scanner.nextLine();
                addRecipient(recipient_factory.getRecipient(recipient_line));
            }
    }

    // provide an iterator to iterate through the lists in the hashMap.
    @Override
    public Iterator getIterator(String key){
        return new RecipientIterator(key);
    }

    // inner class for iteration in iterator pattern.
    public static class RecipientIterator implements Iterator{

        private final String key;
        private int index;

        public RecipientIterator(String key) {
            this.key = key;
        }

        @Override
        public boolean hasNext() {
            return index < recipients.get(key).size();
        }

        @Override
        public Object next() {
            if (this.hasNext()){
                return recipients.get(key).get(index++);
            }
            return null;
        }
    }

}
