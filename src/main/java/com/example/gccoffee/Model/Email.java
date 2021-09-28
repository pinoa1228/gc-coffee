package com.example.gccoffee.Model;

import org.springframework.util.Assert;

import java.util.Objects;
import java.util.regex.Pattern;


//이메일 validation 체크
public class Email {
    private final String address;

    public Email(String address) {
        Assert.notNull(address,"address should not be null");
        Assert.isTrue(address.length()>=4 && address.length()<=50,"address length must be between 4 and 50");
        Assert.isTrue(cheakAdress(address),"Invalid email address");
        this.address = address;
    }

    //이메일 형식에 맞는지 정규표현식으로 체크 해준다.
    private static boolean cheakAdress(String address) {
         return Pattern.matches("\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b", address);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(address, email.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }

    @Override
    public String toString() {
        return "Email{" +
                "address='" + address + '\'' +
                '}';
    }

    public String getAddress() {
        return address;
    }
}
