package com.yahya.growth.stockmanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class Settlement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Timestamp addedTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate settledDate;
    private int amount;
    @ManyToOne
    private Customer customer;
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JsonIgnore @EqualsAndHashCode.Exclude @ToString.Exclude
    private Set<Credit> settledCredits = new HashSet<>();

    public void addCredit(Credit credit) {
        credit.getSettlements().add(this);
        this.settledCredits.add(credit);
    }

    public void removeCredit(Credit credit) {
        credit.getSettlements().remove(this);
        this.settledCredits.remove(credit);
    }

    @PrePersist
    public void initSavedTime() {
        this.addedTime = new Timestamp(System.currentTimeMillis());
    }

}
