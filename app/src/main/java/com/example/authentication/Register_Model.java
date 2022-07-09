package com.example.authentication;

public class Register_Model {
    String name,email,adress,phone,password,sex,blood;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public Register_Model(String name, String email, String adress, String phone, String password, String sex, String blood) {
        this.name = name;
        this.email = email;
        this.adress = adress;
        this.phone = phone;
        this.password = password;
        this.sex = sex;
        this.blood = blood;
    }
}
