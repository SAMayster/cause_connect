package com.cause_connect.cause_c.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;



@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(Include.NON_NULL)
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int uid;
    private String password;
    private String email;
    private String firstname;
    private String lastname;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    @JsonIgnoreProperties("user") // Ignore 'user' property in Donation entity
    private List<Donation> donations;   

    public String getFullName() {
     return firstname + " " + lastname;
}
}
/*learn about @jsonbackref and @jsonmanageref */