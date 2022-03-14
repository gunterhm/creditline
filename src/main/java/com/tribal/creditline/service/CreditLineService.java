package com.tribal.creditline.service;

import com.tribal.creditline.model.CreditLineAcceptance;
import com.tribal.creditline.model.CreditLineRequest;

public interface CreditLineService {
    public CreditLineAcceptance processCreditLineRequest(CreditLineRequest creditLineRequest);
    public Double getRecommendedCreditLine(CreditLineRequest cashBalance);
    public Double getCreditLineByCashBalance(Double cashBalance);
    public Double getCreditLineByMonthlyRevenue(Double monthlyRevenue);
}
