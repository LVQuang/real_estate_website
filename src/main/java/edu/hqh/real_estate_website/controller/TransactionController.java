package edu.hqh.real_estate_website.controller;

import edu.hqh.real_estate_website.service.TransactionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/transaction")
@Controller
public class TransactionController {
    TransactionService transactionService;
    
    @GetMapping("/addTransaction")
    String getAddTransaction()
    {
        return "transaction/addTransaction";
    }
    
    @GetMapping("/{pageNumber}")
    String getTransaction(Model model,
                   @RequestParam(name = "page",
                           required = false, defaultValue = "1") Integer pageNumber
    )
    {
        if (pageNumber == null)
            pageNumber = 1;
        var result = transactionService.getAllContactsPage(pageNumber);
        var transactions = result.getContent();
        model.addAttribute("transactions", transactions);
        model.addAttribute("totalPages", result.getTotalPages() - 1);
        if(result.getTotalPages() == 0) {
            return "/layout/transactions";
        }
        if(result.getTotalPages() <= pageNumber)
            return "redirect:/transaction/1?outPage";
        return "/layout/transactions";
    }

    @GetMapping("/user/{pageNumber}")
    String getMyTransaction(Model model,
                      @RequestParam(name = "page",
                              required = false, defaultValue = "1") Integer pageNumber
    )
    {
        if (pageNumber == null)
            pageNumber = 1;
        var result = transactionService.getAllContactsUserPage(pageNumber);
        var transactions = result.getContent();
        model.addAttribute("transactions", transactions);
        model.addAttribute("totalPages", result.getTotalPages() - 1);
        if(result.getTotalPages() == 0) {
            return "/layout/userTransactions";
        }
        if(result.getTotalPages() <= pageNumber)
            return "redirect:/transaction/user/1?outPage";
        return "layout/userTransactions";
    }
}