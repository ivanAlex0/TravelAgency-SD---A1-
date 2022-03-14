package model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "user_generator")
    private Integer id;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_package",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "package_id"))
    private List<Package> bookedPackages;

    public User() {
        this.bookedPackages = new ArrayList<>();
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                '}';
    }
}
