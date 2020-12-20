package es.ulpgc.gs1.model;

import java.util.Date;

public class Patient {
    private String name, email, telephone;
    private Date Birthdate;
    private Address address;

    public Patient(){
    }

    public Patient(String name, String email, String telephone, Date birthdate, Address address) {
        this.name = name;
        this.email = email;
        this.telephone = telephone;
        Birthdate = birthdate;
        this.address = address;
    }

    public void setBirthdate(Date birthdate) {
        Birthdate = birthdate;
    }

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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Date getBirthdate() {
        return Birthdate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + telephone + '\'' +
                ", Birthdate=" + Birthdate +
                ", address=" + address.toString() +
                '}';
    }
}
