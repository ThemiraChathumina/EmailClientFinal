package com.company;

public class Office_friend extends Official implements BirthdayRecipient{
    String birthday;

    public Office_friend(String name, String email, String designation,String birthday) {
        super(name, email, designation);
        this.birthday = birthday;
    }

    @Override
    public String getBirthday() {
        return this.birthday;
    }
}
