package com.tribal.creditline.service.impl;

import com.tribal.creditline.bean.RequestCounter;
import com.tribal.creditline.service.RequestBlockerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class RequestBlockerServiceImpl implements RequestBlockerService {
    @Autowired
    RequestCounter requestCounter;

    @Value("${requests.allowed.any.withinmillis}")
    Integer requestsAllowedAnyWithinMillis;

    @Value("${requests.allowed.rejected.withinmillis}")
    Integer requestsAllowedRejectedWithinMillis;

    @Override
    public ResponseEntity<Object> filterRequest() {
        long currentTimestamp = Calendar.getInstance().getTimeInMillis();
        Long timestampCreditRejected = requestCounter.getTimestampCreditRejected();
        if (null != timestampCreditRejected && currentTimestamp - requestCounter.getTimestampCreditRejected() < requestsAllowedRejectedWithinMillis){
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Too many requests!!");
        }
        else {
            Long oldestRequestTimestamp = requestCounter.getOldestRequestTimestamp();
            if (null != oldestRequestTimestamp && currentTimestamp - oldestRequestTimestamp < requestsAllowedAnyWithinMillis) {
                return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Too many requests!!");
            }
        }
        return null;
    }
}
