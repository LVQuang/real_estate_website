package edu.hqh.real_estate_website.api;

import edu.hqh.real_estate_website.dto.request.ForgotPasswordRequest;
import edu.hqh.real_estate_website.dto.request.PaymentRequest;
import edu.hqh.real_estate_website.dto.response.ApiResponse;
import edu.hqh.real_estate_website.dto.response.ForgotPasswordResponse;
import edu.hqh.real_estate_website.dto.response.PaymentResponse;
import edu.hqh.real_estate_website.service.PaymentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentAPI {

    PaymentService paymentService;

    @GetMapping("/payment")
    ApiResponse<PaymentResponse> pay(@RequestBody PaymentRequest request) throws UnsupportedEncodingException {
        var result = paymentService.payment(request);
        return ApiResponse.<PaymentResponse>builder()
                .result(result)
                .build();
    }
}
