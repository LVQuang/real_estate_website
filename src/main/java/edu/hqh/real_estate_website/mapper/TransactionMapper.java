package edu.hqh.real_estate_website.mapper;

import edu.hqh.real_estate_website.dto.request.TransactionRequest;
import edu.hqh.real_estate_website.dto.response.TransactionResponse;
import edu.hqh.real_estate_website.entity.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    Transaction convertEntity(TransactionRequest request);
    TransactionResponse toResponse(Transaction transaction);
}
