package com.tribal.creditline.service.impl;

import com.tribal.creditline.bean.RequestCounter;
import com.tribal.creditline.model.CreditLineAcceptance;
import com.tribal.creditline.service.RequestCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestCounterServiceImpl implements RequestCounterService {
    @Autowired
    RequestCounter requestCounter;

    @Override
    public void processResponse(CreditLineAcceptance creditLineAcceptance) {
        if (!creditLineAcceptance.getAccepted()) {
            requestCounter.setTimestampCreditRejected();
        }
        requestCounter.addTimestampCreditRequest();
    }
}
