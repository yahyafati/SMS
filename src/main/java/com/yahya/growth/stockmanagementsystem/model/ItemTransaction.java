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
    @Column(name = "itemTransaction_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "itemTransaction_item_id")
    private Item item;
    @ManyToOne
    @JoinColumn(name = "itemTransaction_transaction_id")
    private Transaction transaction;
    @Column(name = "itemTransaction_unitPrice")
    private double unitPrice;
    @Column(name = "itemTransaction_initialQuantity")
    private int initialQuantity;
    @Column(name = "itemTransaction_remainingQuantity")
    private int remainingQuantity;

    public int getAmountSold() {
        assert this.transaction.getType() == TransactionType.PURCHASE : "Can't be calculated for TransactionType.SALE Transaction. Item sold can only be calculated for TransactionType.PURCHASE Transaction.";
        return initialQuantity - remainingQuantity;
    }

    public Double getTotalPrice() {
        return unitPrice * initialQuantity;
    }

}
