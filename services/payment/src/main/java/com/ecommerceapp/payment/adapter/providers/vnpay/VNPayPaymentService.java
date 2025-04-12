package com.ecommerceapp.payment.adapter.providers.vnpay;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import com.ecommerceapp.payment.core.domain.enums.PaymentProvider;
import com.ecommerceapp.payment.core.models.PaymentInfo;
import com.ecommerceapp.payment.core.port.outbound.services.PaymentProviderService;

import lombok.RequiredArgsConstructor;

@Service
@EnableConfigurationProperties(VNPayProperties.class)
@RequiredArgsConstructor
public class VNPayPaymentService implements PaymentProviderService {
    private final VNPayProperties vnPayProperties;

    public String hmacSHA512(final String key, final String data) {
        try {

            if (key == null || data == null) {
                throw new NullPointerException();
            }
            final Mac hmac512 = Mac.getInstance("HmacSHA512");
            byte[] hmacKeyBytes = key.getBytes();
            final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKey);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();

        } catch (Exception ex) {
            return "";
        }
    }

    @Override
    public String generatePaymentUrl(PaymentInfo paymentInfo) {
        String vnp_Version = vnPayProperties.getVersion();
        String vnp_Command = "pay";
        String vnp_OrderInfo = paymentInfo.orderInfo();
        String orderType = paymentInfo.orderType();
        String vnp_TxnRef = paymentInfo.paymentId();
        String vnp_IpAddr = paymentInfo.ipAdrr();
        String vnp_TmnCode = vnPayProperties.getMerchantCode();

        int amount = paymentInfo.amount() * 100;
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", orderType);

        vnp_Params.put("vnp_Locale", "vn");

        vnp_Params.put("vnp_ReturnUrl", vnPayProperties.getReturnUrl());
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());

        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        // Add Params of 2.1.0 Version
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        // Billing
        // vnp_Params.put("vnp_Bill_Mobile", paymentInfo.phoneNumber());
        // vnp_Params.put("vnp_Bill_Email", paymentInfo.email());
        // vnp_Params.put("vnp_Bill_FirstName", paymentInfo.firstName());
        // vnp_Params.put("vnp_Bill_LastName", paymentInfo.lastName());

        // vnp_Params.put("vnp_Bill_Address", req.getParameter("txt_inv_addr1"));
        // vnp_Params.put("vnp_Bill_City", req.getParameter("txt_bill_city"));
        // vnp_Params.put("vnp_Bill_Country", req.getParameter("txt_bill_country"));
        // if (req.getParameter("txt_bill_state") != null &&
        // !req.getParameter("txt_bill_state").isEmpty()) {
        // vnp_Params.put("vnp_Bill_State", req.getParameter("txt_bill_state"));
        // }
        // Invoice
        // vnp_Params.put("vnp_Inv_Phone", req.getParameter("txt_inv_mobile"));
        // vnp_Params.put("vnp_Inv_Email", req.getParameter("txt_inv_email"));
        // vnp_Params.put("vnp_Inv_Customer", req.getParameter("txt_inv_customer"));
        // vnp_Params.put("vnp_Inv_Address", req.getParameter("txt_inv_addr1"));
        // vnp_Params.put("vnp_Inv_Company", req.getParameter("txt_inv_company"));
        // vnp_Params.put("vnp_Inv_Taxcode", req.getParameter("txt_inv_taxcode"));
        // vnp_Params.put("vnp_Inv_Type", req.getParameter("cbo_inv_type"));
        // Build data to hash and querystring
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                // Build hash data
                try {
                    hashData.append(fieldName);
                    hashData.append('=');
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    // Build query
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage(), e.getCause());
                }

                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = hmacSHA512(vnPayProperties.getSecureHash(), hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = vnPayProperties.getVnpayUrl() + "?" + queryUrl;
        return paymentUrl;
    }

    @Override
    public PaymentProvider getPaymentProvider() {
        return PaymentProvider.VNPAY;
    }

}
