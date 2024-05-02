package edu.hqh.real_estate_website.service;

import edu.hqh.real_estate_website.dto.request.TransactionRequest;
import edu.hqh.real_estate_website.dto.response.TransactionResponse;
import edu.hqh.real_estate_website.entity.Contact;
import edu.hqh.real_estate_website.entity.Transaction;
import edu.hqh.real_estate_website.enums.ErrorCode;
import edu.hqh.real_estate_website.exception.AppException;
import edu.hqh.real_estate_website.mapper.TransactionMapper;
import edu.hqh.real_estate_website.repository.PostRepository;
import edu.hqh.real_estate_website.repository.TransactionRepository;
import edu.hqh.real_estate_website.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
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
    PostRepository postRepository;
    UserRepository userRepository;
    TransactionMapper transactionMapper;
    UserService userService;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public TransactionResponse create(TransactionRequest request, String postId) {
        var transaction = transactionMapper.convertEntity(request);

        var senderName = SecurityContextHolder.getContext().getAuthentication().getName();
        var sender = userRepository.findByName(senderName).orElseThrow(()
                -> new AppException(ErrorCode.ITEM_DONT_EXISTS));

        var post = postRepository.findById(postId).orElseThrow(()
                -> new AppException(ErrorCode.ITEM_DONT_EXISTS));
        var receiver = post.getUser();

        transaction.setTransactionDate(LocalDate.now());
        transaction.setState("Solving");
        transaction.setSender(sender.getName());
        transaction.setReceiver(receiver.getName());
        transaction.setPost(post);

        transactionRepository.save(transaction);

        sender.getTransactions().add(transaction);
        receiver.getTransactions().add(transaction);

        userRepository.saveAll(List.of(sender, receiver));

        post.getTransactions().add(transaction);
        postRepository.save(post);

        return transactionMapper.toResponse(transaction);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public void accept(String transactionId) {
        var transaction = getById(transactionId);
        transaction.setState("Accept");
        transactionRepository.save(transaction);
    }

    public void cancel(String transactionId) {
        var transaction = getById(transactionId);
        transaction.setState("cancel");
        transactionRepository.save(transaction);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<TransactionResponse> getAll() {
        var transactions = transactionRepository.findAll();
        return transactions.stream()
                .map(transactionMapper::toResponse)
                .toList();
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<Transaction> getMyTransactions() {
        var sender = userService.getCurrentUser().getName();
        return transactionRepository.findBySender(sender);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Page<Transaction> getAllContactsPage(int page) {
        var result = transactionRepository.findAll();
        return getAllTransactionsPageImpl(page, result);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Transaction getById(String transactionId) {
        var transaction = transactionRepository.
                findById(transactionId).orElseThrow(() -> new AppException(ErrorCode.ITEM_DONT_EXISTS));
        return transaction;
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Page<Transaction> getAllContactsUserPage(int page) {
        var sender = userService.getCurrentUser().getName();
        var result = transactionRepository.findBySender(sender);
        return getAllTransactionsPageImpl(page, result);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    private Page<Transaction> getAllTransactionsPageImpl(int page, List<Transaction> result) {
        int pageSize = 10;

        if(result.size() < pageSize)
            pageSize = result.size() ;

        Pageable pageable = PageRequest.of(page, pageSize);

        int start =(int) pageable.getOffset();
        int end = Math.min( (start + pageable.getPageSize()) , result.size());

        var content = result.subList(start, end);
        return new PageImpl<>(content, pageable, result.size());
    }
}