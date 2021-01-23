package com.yahya.growth.stockmanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credit_id")
    private Integer id;
    @Column(name = "credit_addedTime")
    private Timestamp addedTime; // for logging purposes
    // TODO Don't Forget to change it to STRING
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "credit_transaction_id")
    private Transaction transaction;
    @Enumerated(EnumType.STRING)
    @Column(name = "credit_type")
    private CreditType type;
    @Column(name = "credit_creditedDate")
    private LocalDate creditedDate;
    @Column(name = "credit_initialAmount")
    private double initialAmount;
    @Column(name = "credit_remainingAmount")
    private double remainingAmount;

    @ManyToMany(mappedBy = "settledCredits")
    @JsonIgnore
    @EqualsAndHashCode.Exclude @ToString.Exclude
    private Set<Settlement> settlements = new HashSet<>();

    public Credit(CreditType type, Transaction transaction, double initialAmount) {
        this.type = type;
        this.transaction = transaction;
        this.creditedDate = transaction.getTransactionDate();
        setInitialAmount(initialAmount);
    }

    public double getInitialAmount() {
        return this.initialAmount;
    }

    public void setInitialAmount(double initialAmount) {
        assert initialAmount >= 0 : "Credit has gone negative";
        this.initialAmount = initialAmount;
        this.remainingAmount = initialAmount;
    }

//    public double getRemaining() {
//        return getInitialAmount() - getSettlements()
//                .stream()
//                .mapToDouble(Settlement::getAmount)
//                .sum();
//    }

    @PrePersist
    public void initSavedTime() {
        this.addedTime = new Timestamp(System.currentTimeMillis());
    }

    public void addSettlement(Settlement settlement) {
        settlement.getSettledCredits().add(this);
        this.settlements.add(settlement);
    }

    public void removeSettlement(Settlement settlement) {
        settlement.getSettledCredits().remove(this);
        this.settlements.remove(settlement);
    }


}
