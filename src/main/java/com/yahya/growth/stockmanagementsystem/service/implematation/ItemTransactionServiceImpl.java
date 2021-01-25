package com.yahya.growth.stockmanagementsystem.service.implematation;

import com.yahya.growth.stockmanagementsystem.dao.ItemTransactionDao;
import com.yahya.growth.stockmanagementsystem.model.Item;
import com.yahya.growth.stockmanagementsystem.model.ItemTransaction;
import com.yahya.growth.stockmanagementsystem.model.Transaction;
import com.yahya.growth.stockmanagementsystem.service.ItemTransactionService;
import lombok.SneakyThrows;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

@Service
public class ItemTransactionServiceImpl implements ItemTransactionService {

    private final ItemTransactionDao itemTransactionDao;

    @Autowired
    public ItemTransactionServiceImpl(ItemTransactionDao itemTransactionDao) {
        this.itemTransactionDao = itemTransactionDao;
    }

    /**
     * Finds and returns an ItemTransaction Entity with the given id, throws an exception if no ItemTransaction entity with the given id is found in the database.
     * @param id - id of the ItemTransaction Entity
     * @return ItemTransaction Entity with given id.
     */
    @Override
    public ItemTransaction findById(int id) {
        return itemTransactionDao.findById(id).orElseThrow();
    }

    /**
     * Saves an ItemTransaction entity into database
     * @param item the entity that's going to be saved
     * @return the same entity after being saved
     */
    @Override
    public ItemTransaction save(ItemTransaction item) {
        return itemTransactionDao.save(item);
    }

    /**
     * Saves an ItemTransaction entity into database. This is currently the same as ItemTransaction.save(ItemTransaction)
     * @param itemTransaction the entity that's going to be saved
     * @return the same entity after being saved
     */
    public ItemTransaction saveNewItemTransaction(ItemTransaction itemTransaction) {
//        itemTransaction.setRemaining(itemTransaction.getQuantity());
        return save(itemTransaction);
    }

    /**
     * Returns all ItemTransactions in the database.
     * @return all ItemTransaction in Database
     */
    @Override
    public List<ItemTransaction> findAll() {
        return itemTransactionDao.findAll();
    }

    /**
     * Deletes an ItemTrasaction with given id from database
     * @param id id of the ItemTransaction to be deleted
     */
    @Override
    public void deleteById(Integer id) {
        itemTransactionDao.deleteById(id);
    }

    /**
     * Not yet implemented.
     * @throws UnsupportedOperationException
     * @param transactionId id of the Transaction
     * @return find all ItemTransaction under a certain Transaction
     */
    @Override
    public List<ItemTransaction> findAllByTransactionId(int transactionId) {
        throw new UnsupportedOperationException("Operation not yet supported.");
    }

    /**
     * Find and return a list of ItemTransactions under a certain Transaction
     * @param transaction - the Transaction entity
     * @return list of ItemTransaction under certain Transaction
     */
    @Override
    public List<ItemTransaction> findAllByTransaction(Transaction transaction) {
        return itemTransactionDao.findAllByTransaction(transaction);
    }

    /**
     * Not yet implemented.
     * @throws UnsupportedOperationException
     * @param itemId id of the Transaction
     * @return find all ItemTransaction with certain itemId
     */
    @Override
    public List<ItemTransaction> findAllByItemId(int itemId) {
        throw new UnsupportedOperationException("Operation not yet supported.");
    }


    /**
     * Find and return a list of ItemTransactions with a certain Item
     * @param item - the Item entity
     * @return list of ItemTransaction with certain Item
     */
    @Override
    public List<ItemTransaction> findAllByItem(Item item) {
        return itemTransactionDao.findAllByItem(item);
    }

    /**
     * Find and return a list of ItemTransactions with a certain Item <b>sorted in ascending order by the id of entry</b>
     * @param item - the Item entity
     * @return list of ItemTransaction with certain Item
     */
    @Override
    public List<ItemTransaction> findAllByItemSorted(Item item) {
        return itemTransactionDao.findAllByItemOrderByIdAsc(item);
    }

    /**
     * Find and return a list of ItemTransactions with a certain Item <b>sorted in descending order by the id of entry</b>
     * @param item - the Item entity
     * @return list of ItemTransaction with certain Item
     */
    @Override
    public List<ItemTransaction> findAllByItemSortedDescending(Item item) {
        return itemTransactionDao.findAllByItemOrderByIdDesc(item);
    }

    /**
     * Find and return a list of ItemTransactions with a certain Item <b>sorted in ascending order by the id of entry</b>
     * @param itemId - the id of Item entity
     * @return list of ItemTransaction with certain Item
     * @throws UnsupportedOperationException
     */
    @Override
    public List<ItemTransaction> findAllByItemIdSorted(int itemId) {
        throw new UnsupportedOperationException("Operation not yet supported.");
    }

    /**
     * Calculates the amount of Item left in stock.
     * @param item the Item to calculate for
     * @return return the amount of Item left
     */
    @Override
    public int getQuantityOfItem(Item item) {
        Integer quantity =itemTransactionDao.getItemSum(item);
        return quantity != null ? quantity : 0;
    }

    /**
     * Calculates the amount of Item left in stock.
     * @param id the Item id to calculate for
     * @return return the amount of Item left
     */
    @Override
    public int getQuantityOfItem(int id) {
        Integer quantity = itemTransactionDao.getItemSum(id);
        return quantity != null ? quantity : 0;
    }

    // TODO Delete Sneaky Throw
    @SneakyThrows
    @Override
    public String generateReport() {
        List<ItemTransaction> itemTransactions = findAll();

        InputStream transactionStream = getClass().getResourceAsStream("/reports/TransactionReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(transactionStream);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(itemTransactions);


        JasperPrint print = JasperFillManager.fillReport(jasperReport, null, dataSource);
        String path = "C:\\Users\\yahya\\OneDrive\\Desktop\\File.pdf";
        JasperExportManager.exportReportToPdfFile(print, path);
        return path;
    }

    /**
     * Saves a collection of ItemTransactions into database
     * @param itemTransactions Collection of ItemTransactions
     * @return List of Saved Instances of ItemTransactions
     */
    @Override
    public List<ItemTransaction> saveAll(Collection<ItemTransaction> itemTransactions) {
        return itemTransactionDao.saveAll(itemTransactions);
    }
}
