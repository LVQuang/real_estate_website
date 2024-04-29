package edu.hqh.real_estate_website.service;

import edu.hqh.real_estate_website.config.PaymentConfig;
import edu.hqh.real_estate_website.dto.request.PaymentRequest;
import edu.hqh.real_estate_website.dto.response.PaymentResponse;
import edu.hqh.real_estate_website.entity.Role;
import edu.hqh.real_estate_website.entity.User;
import edu.hqh.real_estate_website.enums.ErrorCode;
import edu.hqh.real_estate_website.enums.RoleName;
import edu.hqh.real_estate_website.exception.AppException;
import edu.hqh.real_estate_website.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
public class PaymentService {

    UserRepository userRepository;

    public String getPay(User user) throws UnsupportedEncodingException {

        String orderType = "other";
        String vnp_IpAddr = "127.0.0.1";
        long amount = 50000 * 100;
        String bankCode = "NCB";
        String vnp_TxnRef = PaymentConfig.getRandomNumber(8);
        String vnp_TmnCode = PaymentConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", PaymentConfig.vnp_Version);
        vnp_Params.put("vnp_Command", PaymentConfig.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", bankCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        vnp_Params.put("vnp_ReturnUrl", PaymentConfig.vnp_ReturnUrl);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }

        if (user != null && !user.getName().isEmpty()){
            String queryUrl = query.toString();
            String vnp_SecureHash = PaymentConfig.hmacSHA512(PaymentConfig.secretKey, hashData.toString());
            queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
            String paymentUrl = PaymentConfig.vnp_PayUrl + "?" + queryUrl;
            return paymentUrl;
        }
        return "";

    }

    public PaymentResponse payment(PaymentRequest request) throws UnsupportedEncodingException {
        var user = userRepository.findByName(request.getName()).orElseThrow(() -> new AppException(ErrorCode.ITEM_DONT_EXISTS));

        Set<Role> userRoles = user.getRoles();
        List<String> roleNames = userRoles.stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        if (!roleNames.contains(RoleName.USER.getName())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        var VNPayURL = getPay(user);
        return PaymentResponse.builder()
                .url(VNPayURL)
                .build();
    }
}
