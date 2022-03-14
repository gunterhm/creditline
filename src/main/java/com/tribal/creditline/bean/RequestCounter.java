package com.tribal.creditline.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;

@Component
@Scope("singleton")
public class RequestCounter {

    private final Long[] timestampCreditRequests;
    private Long lastTimestampCreditRejected;

    @Autowired
    public RequestCounter(@Value("${requests.allowed.any.number}") Integer requestsAllowedAnyNumber) {
        timestampCreditRequests = new Long[requestsAllowedAnyNumber];
        lastTimestampCreditRejected = 0L;
    }

    public void  addTimestampCreditRequest(){
        long timestampCreditRejected = Calendar.getInstance().getTimeInMillis();
        synchronized(timestampCreditRequests) {
            Collections.rotate(Arrays.asList(timestampCreditRequests), 1);
            timestampCreditRequests[0] = timestampCreditRejected;
        }
    }

    public synchronized void setTimestampCreditRejected(){
            lastTimestampCreditRejected = Calendar.getInstance().getTimeInMillis();
    }

    public synchronized Long getTimestampCreditRejected() {
        return lastTimestampCreditRejected;
    }

    public Long getOldestRequestTimestamp() {
        synchronized (timestampCreditRequests) {
            return timestampCreditRequests[timestampCreditRequests.length - 1];
        }
    }
}
