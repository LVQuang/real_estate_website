package edu.hqh.real_estate_website.service;

import edu.hqh.real_estate_website.dto.request.TransactionRequest;
import edu.hqh.real_estate_website.dto.response.TransactionResponse;
import edu.hqh.real_estate_website.enums.ErrorCode;
import edu.hqh.real_estate_website.exception.AppException;
import edu.hqh.real_estate_website.mapper.TransactionMapper;
import edu.hqh.real_estate_website.repository.TransactionRepository;
import edu.hqh.real_estate_website.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
@Service
public class TransactionService {
    TransactionRepository transactionRepository;
    UserRepository userRepository;
    TransactionMapper transactionMapper;

    public TransactionResponse create(TransactionRequest request, String receiverId) {
        var transaction = transactionMapper.convertEntity(request);

        var senderName = SecurityContextHolder.getContext().getAuthentication().getName();
        var sender = userRepository.findByName(senderName).orElseThrow(()
                -> new AppException(ErrorCode.ITEM_DONT_EXISTS));

        var receiver = userRepository.findById(receiverId).orElseThrow(()
                -> new AppException(ErrorCode.ITEM_DONT_EXISTS));

        transaction.setTransactionDate(LocalDate.now());
        transaction.setSender(sender.getId());
        transaction.setReceiver(receiverId);

        transactionRepository.save(transaction);

        sender.getTransactions().add(transaction);
        receiver.getTransactions().add(transaction);

        userRepository.saveAll(List.of(sender, receiver));

        return transactionMapper.toResponse(transaction);
    }

    public List<TransactionResponse> getAll() {
        var transactions = transactionRepository.findAll();
        return transactions.stream()
                .map(transactionMapper::toResponse)
                .toList();
    }

}