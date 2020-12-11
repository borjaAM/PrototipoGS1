package es.ulpgc.gs1.model;

import java.util.Date;

public class Professional {
    private String name, username, password, email, phoneNumber;
    private final int idProfessional;
    private final Date birthdate;
    private final Role role;


    public Professional(String name, String username, String password, String email, Role role, int idProfessional, Date birthdate) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.idProfessional = idProfessional;
        this.birthdate = birthdate;
    }

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public int getIdProfessional() {
        return idProfessional;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public enum Role {
        Fisioterapeuta, TerapeutaOcupacional, Logopeda;
    }
}
