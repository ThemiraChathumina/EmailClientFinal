package com.company;

public class Official extends Recipient{
    String designation;

    public Official(String name,String email,String designation){
        super(name,email);
        this.designation = designation;
    }

    public String getDesignation() {
        return designation;
    }
}
