package com.company;

public class Personal extends Recipient implements BirthdayRecipient {
    String nickname;
    String birthday;

    public Personal(String name,String nickname,String email,String birthday){
        super(name, email);
        this.nickname = nickname;
        this.birthday = birthday;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public String getBirthday() {
        return birthday;
    }
}
