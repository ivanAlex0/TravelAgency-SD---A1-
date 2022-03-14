package model;

import lombok.Getter;
import lombok.Setter;
import model.enums.PACKAGE_STATUS;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Package implements Serializable, Updatable, Comparable<Package> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "package_generator")
    private Integer id;

    @Column(unique = true)
    private String name;

    @Column
    private Integer price;

    @Column
    private Date fromDate;

    @Column
    private Date toDate;

    @Column
    private String extraDetails;

    @Column
    private Integer availableSpots;

    @Column
    private PACKAGE_STATUS package_status;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "destination_id")
    private Destination destination;

    @ManyToMany(mappedBy = "bookedPackages")
    private List<User> users;

    public Package() {
    }

    public Package(String name, Integer price, Date from, Date to, String extraDetails, Integer availableSpots, Destination destination) {
        this.name = name;
        this.price = price;
        this.fromDate = from;
        this.toDate = to;
        this.extraDetails = extraDetails;
        this.availableSpots = availableSpots;
        this.destination = destination;
        this.package_status = PACKAGE_STATUS.NOT_BOOKED;
    }

    @Override
    public String toString() {
        return package_status + "->           " +
                name + " -- " +
                fromDate + " - " + toDate +
                " " + extraDetails +
                " Spots=" + availableSpots +
                " DESTINATION:" + destination.getName() +
                " PRICE: " + price;
    }

    public String toStringUserLevel() {
        return name + "[" + destination.getName() + "] " + "[" + fromDate + " - " + toDate + "]" +
                "[" + availableSpots + "]" +
                " - $$" + price +
                " -" + extraDetails;
    }


    @Override
    public void update(Object object) {
        if (object instanceof Package) {
            setName(((Package) object).getName());
            setPrice(((Package) object).getPrice());
            setFromDate(((Package) object).getFromDate());
            setToDate(((Package) object).getToDate());
            setExtraDetails(((Package) object).getExtraDetails());
            setAvailableSpots(((Package) object).getAvailableSpots());
            if (this.destination != ((Package) object).getDestination())
                setDestination(((Package) object).getDestination());
            setPackage_status(((Package) object).getPackage_status());
        }
    }

    @Override
    public int compareTo(Package o) {
        return o.getAvailableSpots() - this.getAvailableSpots();
    }
}

