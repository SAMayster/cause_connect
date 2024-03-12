package com.cause_connect.cause_c.model;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Donation {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int did;

    // @ManyToOne
    // @JoinColumn(name = "uid", referencedColumnName = "uid")
    // private User user;

    @ManyToOne
    @JoinColumn(name = "cid", referencedColumnName = "cid")
    private Cause cause;

    private double payableAmount;

    private double amountRaisedTillNow;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date donationTime = new Date();
}