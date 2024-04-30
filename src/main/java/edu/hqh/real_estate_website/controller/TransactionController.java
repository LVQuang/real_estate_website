package edu.hqh.real_estate_website.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/transaction")
@Controller
public class TransactionController {
    @GetMapping("/addTransaction")
    String getAddTransaction()
    {
        return "transaction/addTransaction";
    }
}
