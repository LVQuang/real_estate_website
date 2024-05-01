package edu.hqh.real_estate_website.controller;

import edu.hqh.real_estate_website.dto.request.PaymentRequest;
import edu.hqh.real_estate_website.dto.response.PaymentResponse;
import edu.hqh.real_estate_website.service.PaymentService;
import edu.hqh.real_estate_website.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/payment")
@Controller
public class PaymentController {
    PaymentService paymentService;
    UserService userService;

    @GetMapping("/VNPay")
    String getPay()
            throws UnsupportedEncodingException {
        var name = userService.getCurrentUser().getName();

        var request = PaymentRequest.builder()
                .name(name)
                .build();

        var response = paymentService.payment(request);

        log.info(response.getUrl());
        return "redirect:" + response.getUrl();
    }

    @GetMapping("/checkPay")
    String checkPay(){
        String link = "https://sandbox.vnpayment.vn/merchantv2/Transaction/PaymentSearch.htm";
        return "redirect:" + link;
    }
}