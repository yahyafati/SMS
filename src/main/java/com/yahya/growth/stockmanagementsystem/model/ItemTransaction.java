package com.yahya.growth.stockmanagementsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "it_generator")
    @SequenceGenerator(name = "it_generator", sequenceName = "itemTrans_seq", allocationSize = 1, initialValue = 1)
    private int id;

    @ManyToOne
    private Item item;
    @ManyToOne
    private Transaction transaction;
    private double unitPrice;
    private int initialQuantity;

    private int remaining;

    public int getAmountSold() {
        assert this.transaction.getType() == TransactionType.PURCHASE : "Can't be calculated for TransactionType.SALE Transaction. Item sold can only be calculated for TransactionType.PURCHASE Transaction.";
        return initialQuantity - remaining;
    }

}
