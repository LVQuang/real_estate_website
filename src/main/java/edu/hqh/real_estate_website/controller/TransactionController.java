package edu.hqh.real_estate_website.controller;

import edu.hqh.real_estate_website.dto.response.TransactionResponse;
import edu.hqh.real_estate_website.service.PostService;
import edu.hqh.real_estate_website.service.TransactionService;
import edu.hqh.real_estate_website.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/transaction")
@Controller
public class TransactionController {
    TransactionService transactionService;
    UserService userService;
    PostService postService;

    @GetMapping("/create/{postId}")
    String create(@PathVariable String postId, Model model) {

        var transaction = TransactionResponse.builder()
                .transactionDate(LocalDate.now())
                .sender(userService.getCurrentUser().getName())
                .receiver(postService.getById(postId).getUser().getName())
                .build();

        model.addAttribute("transaction", transaction);
        model.addAttribute("postId", postId);
//        transactionService.create();
        return "add/addTransaction";
    }

    @GetMapping("/renderTransaction/{transactionId}")
    String renderTransaction(Model model, @PathVariable String transactionId)
    {
        var transaction = transactionService.getById(transactionId);
        model.addAttribute("transaction", transaction);
        return "layout/renderTransaction";
    }

    @GetMapping("/accept/{transactionId}")
    String accept(@PathVariable String transactionId) {
        transactionService.accept(transactionId);
        return "redirect:/transaction/user/null?page=0";
    }

    @GetMapping("/cancel/{transactionId}")
    String cancel(@PathVariable String transactionId) {
        transactionService.cancel(transactionId);
        return "redirect:/transaction/user/null?page=0";
    }

    @GetMapping("/addTransaction")
    String getAddTransaction()
    {
        return "layout/addTransaction";
    }
    
    @GetMapping("/{pageNumber}")
    String getTransaction(Model model,
                   @RequestParam(name = "page",
                           required = false, defaultValue = "0") Integer pageNumber
    )
    {
        if (pageNumber != null && pageNumber > 0)
            pageNumber-=1;
        if(pageNumber == null)
            pageNumber =0;

        var result = transactionService.getAllContactsPage(pageNumber);
        var transactions = result.getContent();
        model.addAttribute("transactions", transactions);
        model.addAttribute("totalPages", result.getTotalPages() - 1);
        if(result.getTotalPages() == 0) {
            return "/layout/transactions";
        }
        if(result.getTotalPages() <= pageNumber)
            return "redirect:/transaction/null?page=0&outPage=true";
        return "/layout/transactions";
    }

    @GetMapping("/user/{pageNumber}")
    String getMyTransaction(Model model,
                      @RequestParam(name = "page",
                              required = false, defaultValue = "0") Integer pageNumber
    )
    {
        if (pageNumber != null && pageNumber > 0)
            pageNumber-=1;
        if(pageNumber == null)
            pageNumber =0;

        var result = transactionService.getAllContactsUserPage(pageNumber);
        var transactions = result.getContent();
        model.addAttribute("transactions", transactions);
        model.addAttribute("totalPages", result.getTotalPages());
        if(result.getTotalPages() == 0) {
            return "/layout/userTransactions";
        }
        if(result.getTotalPages() <= pageNumber)
            return "redirect:/transaction/user/null?page=0&outPage=true";
        return "layout/userTransactions";
    }
}
