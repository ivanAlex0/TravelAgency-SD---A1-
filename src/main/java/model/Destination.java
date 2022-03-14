package model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Destination implements Updatable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "destination_generator")
    private Integer id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "destination")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Package> packages;

    public Destination() {
        this.packages = new ArrayList<>();
    }

    public Destination(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void update(Object object) {
        if (object instanceof Destination) {
            this.name = ((Destination) object).getName();
        }
    }
}
