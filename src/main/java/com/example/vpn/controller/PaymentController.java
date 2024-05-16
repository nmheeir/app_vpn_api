package com.example.vpn.controller;

import com.example.vpn.entities.PaymentRecord;
import com.example.vpn.entities.User;
import com.example.vpn.repositories.PaymentRepository;
import com.example.vpn.repositories.UserRepository;
import com.example.vpn.responses.DataResponse;
import com.example.vpn.services.PaymentService;
import com.example.vpn.utils.Constants;
import com.example.vpn.utils.JwtUtils;
import com.example.vpn.utils.VnpayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private JwtUtils jwtUtils;

    private String _token;
    private String _txtRef;

    @PostMapping("create_payment")
    public ResponseEntity<?> createPayment(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam(name = "ipAddress") String ipAddress,
            @RequestParam(name = "amount") String amount
    ) {
        String vnp_TxnRef = VnpayConfig.getRandomNumber(8);

        int _amount = Integer.parseInt(amount) * 100;
        amount = Integer.toString(_amount);

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", VnpayConfig.vnp_Version);
        vnp_Params.put("vnp_Command", VnpayConfig.vnp_Command);
        vnp_Params.put("vnp_TmnCode", VnpayConfig.vnp_TmnCode);
        vnp_Params.put("vnp_Amount", amount);
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang: " + vnp_TxnRef);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_OrderType", VnpayConfig.orderType);
        vnp_Params.put("vnp_IpAddr", ipAddress);
        vnp_Params.put("vnp_ReturnUrl", VnpayConfig.vnp_ReturnUrl);

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
            if ((fieldValue != null) && (!fieldValue.isEmpty())) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VnpayConfig.hmacSHA512(VnpayConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VnpayConfig.vnp_PayUrl + "?" + queryUrl;

        Map<String, Object> payment = new HashMap<>();
        payment.put("paymentUrl", paymentUrl);

        PaymentRecord paymentRecord = new PaymentRecord(vnp_TxnRef, jwtUtils.extractUsername(token), vnp_CreateDate);

        paymentRepository.save(paymentRecord);

        return DataResponse.dataResponseBuilder(true, "Create payment successfully", HttpStatus.OK, payment);
    }

    @GetMapping("payment_info")
    public String transaction(
            @RequestParam(value = "vnp_TxnRef") String txnRef,
            @RequestParam(value = "vnp_Amount") String amount
    ) {
        try {
            Integer time = 0;
            switch (amount) {
                case "2300000": {
                    time = Constants.ONE_MONTH;
                    break;
                }
                case "10900000": {
                    time = Constants.SIX_MONTH;
                    break;
                }
                case "20900000": {
                    time = Constants.ONE_YEAR;
                    break;
                }
                case "35900000": {
                    time = Constants.TWO_YEAR;
                    break;
                }
                default:
                    time = 0;
            }

            PaymentRecord paymentRecord = paymentRepository.findByTxnRef(txnRef);

            User user = userRepository.findUserByUsername(paymentRecord.getUsername());
            String premiumKey = paymentService.getPremiumKey(user, time);
            user.setPremiumKey(premiumKey);
            paymentRepository.delete(paymentRecord);

            String url = "https://example.com";
            return restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
