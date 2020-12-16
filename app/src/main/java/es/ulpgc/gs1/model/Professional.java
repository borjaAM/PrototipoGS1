package es.ulpgc.gs1.model;

import java.util.Date;

public class Professional {
    private String name, username, password, email, phone;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() { return password; }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIdProfessional() {
        return idProfessional;
    }

    public Date getBirthdate() {
        return birthdate;
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

    public Role getRole() {
        return role;
    }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }
}
