package edu.hqh.real_estate_website.api;

import edu.hqh.real_estate_website.dto.request.TransactionRequest;
import edu.hqh.real_estate_website.dto.response.ApiResponse;
import edu.hqh.real_estate_website.dto.response.TransactionResponse;
import edu.hqh.real_estate_website.service.TransactionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionAPI {
    TransactionService transactionService;

    @GetMapping
    ApiResponse<List<TransactionResponse>> getAll() {
        return ApiResponse.<List<TransactionResponse>>builder()
                .result(transactionService.getAll())
                .build();
    }

    @PostMapping("/{receiveId}")
    ApiResponse<TransactionResponse> create(@RequestBody TransactionRequest request, @PathVariable String receiveId) {
        return ApiResponse.<TransactionResponse>builder()
                .result(transactionService.create(request, receiveId))
                .build();
    }
}
