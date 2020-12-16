package es.ulpgc.gs1.model;

public class Address{
    private String street, city;
    private int number, zipcode;

    public Address(){

    }

    public Address(String street, String city, int number, int zipcode) {
        this.street = street;
        this.city = city;
        this.number = number;
        this.zipcode = zipcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", number=" + number +
                ", zipcode=" + zipcode +
                '}';
    }
}
