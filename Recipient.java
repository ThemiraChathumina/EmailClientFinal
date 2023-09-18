package com.company;

public abstract class Recipient {

    private final String name;
    private final String email;

    protected Recipient(String name,String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

}
