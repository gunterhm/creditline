package com.tribal.creditline.service;

import com.tribal.creditline.model.CreditLineAcceptance;

public interface RequestCounterService {
    public void processResponse(CreditLineAcceptance creditLineAcceptance);
}
