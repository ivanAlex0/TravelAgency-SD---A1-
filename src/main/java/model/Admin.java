package model;

import javax.persistence.Entity;

@Entity
public class Admin extends User {

    public Admin() {
        super.setEmail("admin");
        super.setPassword("admin");
    }
}
