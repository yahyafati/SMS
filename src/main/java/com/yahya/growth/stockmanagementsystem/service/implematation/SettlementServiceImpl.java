package com.yahya.growth.stockmanagementsystem.service.implematation;

import com.yahya.growth.stockmanagementsystem.dao.SettlementDao;
import com.yahya.growth.stockmanagementsystem.model.Credit;
import com.yahya.growth.stockmanagementsystem.model.Settlement;
import com.yahya.growth.stockmanagementsystem.service.CreditService;
import com.yahya.growth.stockmanagementsystem.service.SettlementService;
import com.yahya.growth.stockmanagementsystem.utilities.SettlementException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SettlementServiceImpl implements SettlementService {

    private final SettlementDao settlementDao;
    private final CreditService creditService;

    @Autowired
    public SettlementServiceImpl(SettlementDao settlementDao, CreditService creditService) {
        this.settlementDao = settlementDao;
        this.creditService = creditService;
    }

    @Override
    public Settlement findById(int id) {
        return settlementDao.findById(id).orElseThrow();
    }

    @Override
    public Settlement save(Settlement item) {
        return settlementDao.save(item);
    }

    @Override
    public List<Settlement> findAll() {
        return settlementDao.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        settlementDao.deleteById(id);
    }

    // TODO Remove Sneaky Throws
    @SneakyThrows
    @Override
    public Settlement saveNewSettlement(Settlement settlement) {
        List<Credit> credits = creditService.findByCustomer(settlement.getCustomer())
                .stream()
                // TODO Make the filters one line
                .filter(credit -> credit.getType() == settlement.getType().getCreditType())
                .filter(credit -> credit.getRemainingAmount() > 0)
                .sorted(Comparator.comparing(Credit::getCreditedDate))
                .collect(Collectors.toList());
        double unsettledTotal = credits.stream()
                .reduce(0.0, (subtotal, credit) -> subtotal + credit.getRemainingAmount(), Double::sum);
        double amount = settlement.getAmount();
        System.out.printf("Unsettled: %f\nAmount: %f\n", unsettledTotal, amount);
        if (amount > unsettledTotal) {
            throw new SettlementException(String.format("Settlement Amount (%f) can't be greater than Total Unsettled Amount (%.2f)", amount, unsettledTotal));
        }
        for (Credit credit: credits) {
            credit.addSettlement(settlement);
            if (credit.getRemainingAmount() > amount) {
                credit.setRemainingAmount(credit.getRemainingAmount() - amount);
                amount = 0;
            } else {
                amount -= credit.getRemainingAmount();
                credit.setRemainingAmount(0);
            }
            if (amount == 0) {
                break;
            }
        }
        return save(settlement);
    }
}
