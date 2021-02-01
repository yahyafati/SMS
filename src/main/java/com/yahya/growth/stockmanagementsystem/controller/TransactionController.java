package com.yahya.growth.stockmanagementsystem.controller;

import com.google.common.collect.Lists;
import com.yahya.growth.stockmanagementsystem.model.*;
import com.yahya.growth.stockmanagementsystem.service.*;
import com.yahya.growth.stockmanagementsystem.utilities.FlashMessage;
import com.yahya.growth.stockmanagementsystem.utilities.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/transaction")
public class TransactionController implements BasicControllerSkeleton<Transaction> {

    private final TransactionService transactionService;
    private final CustomerService customerService;
    private final ItemService itemService;
    private final UserService userService;

    @Autowired
    public TransactionController(TransactionService transactionService, CustomerService customerService, ItemService itemService, UserService userService) {
        this.transactionService = transactionService;
        this.customerService = customerService;
        this.itemService = itemService;
        this.userService = userService;
    }

    @ModelAttribute("title")
    public String getPageTitle() {
        return "Transaction";
    }

    @ModelAttribute("active")
    public List<String> getCurrentlyActive() {
        return Lists.newArrayList("transaction");
    }

    @ModelAttribute("transactionTypes")
    public TransactionType[] getTransactionTypes() {
        return TransactionType.values();
    }

    @ModelAttribute("customers")
    public List<Customer> getCustomers() {
        return customerService.findAll();
    }


    @Override
    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("active", Lists.newArrayList("detailTransaction", "transaction"));
        model.addAttribute("transactions", transactionService.findAll());
        model.addAttribute("pageName", "transaction/all");
        return "common/header";
    }

    @Override
    @GetMapping("/{id}")
    public String detail(@PathVariable int id, Model model) {
        Transaction transaction = transactionService.findById(id);

        model.addAttribute("transaction", transaction);
        model.addAttribute("pageName", "transaction/detail");
        return "common/header";
    }

    @Override
    @GetMapping("/new")
    @PreAuthorize("hasAuthority('transaction:write')")
    public String addNewItem(Model model) {
        throw new UnsupportedOperationException("This method is not supported.");
    }

    @GetMapping("/purchase")
    @PreAuthorize("hasAuthority('transaction:write')")
    public String purchaseItem(Model model) {
        model.addAttribute("active", Lists.newArrayList("purchaseTransaction", "transaction"));
        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.PURCHASE);
        model.addAttribute("allItems", itemService.findAll());
        model.addAttribute("transaction", transaction);
        model.addAttribute("paidAmount", 0.0);
        model.addAttribute("action", "new");
        model.addAttribute("pageName", "transaction/transaction");
        return "common/header";
    }

    @GetMapping("/sale")
    @PreAuthorize("hasAuthority('transaction:write')")
    public String saleItem(Model model) {
        model.addAttribute("active", Lists.newArrayList("saleTransaction", "transaction"));
        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.SALE);
        model.addAttribute("allItems", itemService.findAllAvailableItems());
        model.addAttribute("transaction", transaction);
        model.addAttribute("action", "new");
        model.addAttribute("pageName", "transaction/transaction");
        return "common/header";
    }

    @Override
    @PreAuthorize("hasAuthority('transaction:write')")
    public String addNewPOST(Transaction obj) {
        return null;
    }

    @Override
    public String edit(int id, Model model) {
        throw new UnsupportedOperationException("This method is not supported.");
    }

    @PostMapping("/new")
    @PreAuthorize("hasAuthority('transaction:write')")
    public String addNewPOST(Transaction transaction,
                             Double paidAmount, HttpServletRequest request,
                                Principal principal) throws TransactionException {
        transaction.setUser(userService.findByUsername(principal.getName()));
        String[] ids = request.getParameterValues("transactionID");
        String[] items = request.getParameterValues("item");
        String[] prices = request.getParameterValues("price");
        String[] quantities = request.getParameterValues("quantity");
        transaction = transactionService.save(transaction, ids, items, prices, quantities, paidAmount);
        return "redirect:/transaction/" + transaction.getId();
    }

    @GetMapping("/edit")
    @PreAuthorize("hasAuthority('transaction:write')")
    public RedirectView edit(RedirectAttributes redir) {
        RedirectView redirectView = new RedirectView("/transaction", true);
        redir.addFlashAttribute("error", "Transaction can't be edited.");
        return redirectView;
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('transaction:write')")
    public RedirectView editPOST(RedirectAttributes redir) throws TransactionException {
        RedirectView redirectView = new RedirectView("/transaction", true);
        redir.addFlashAttribute("error", "Transaction can't be edited.");
        return redirectView;
    }


    @Override
    @PreAuthorize("hasAuthority('transaction:write')")
    public String editPost(int id, Transaction obj) {
        return null;
    }

    @Override
    @Deprecated
    public String delete(int id) {
        throw new UnsupportedOperationException("This delete operation is unsupported.");
    }


    @GetMapping("/delete")
    @PreAuthorize("hasAuthority('transaction:write')")
    public RedirectView delete(@RequestParam int id, RedirectAttributes redir) {
        RedirectView redirectView = new RedirectView("/transaction", true);
        if (!transactionService.deleteById(id)) {
            FlashMessage flashMessage = new FlashMessage("You can not delete a necessary transaction. This is a necessary transaction.",
                    "", FlashMessage.Type.ERROR);
            redir.addFlashAttribute("dialogFlash", flashMessage);
        }
        return redirectView;
    }
}
