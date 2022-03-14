package com.tribal.creditline.service.impl;

import com.tribal.creditline.model.CreditLineAcceptance;
import com.tribal.creditline.model.CreditLineRequest;
import com.tribal.creditline.model.persist.CreditLine;
import com.tribal.creditline.repository.CreditLineRepository;
import com.tribal.creditline.service.CreditLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CreditLineServiceImpl implements CreditLineService {
    @Autowired
    CreditLineRepository creditLineRepository;

    @Override
    public CreditLineAcceptance processCreditLineRequest(CreditLineRequest creditLineRequest) {
        CreditLineAcceptance creditLineAcceptance = new CreditLineAcceptance();

        CreditLine creditLine = creditLineRepository.findByUser(creditLineRequest.getUser());
        if (null == creditLine) {
            creditLine = new CreditLine();
            creditLine.setAcceptedCreditLine(0D);
            creditLine.setUser(creditLineRequest.getUser());
            creditLine.setTimesRejected(0);
        } else if (creditLine.getAccepted()) {
            creditLineAcceptance.setAcceptedCreditLine(creditLine.getAcceptedCreditLine());
            creditLineAcceptance.setAccepted(true);
            creditLineAcceptance.setTimesRejected(creditLine.getTimesRejected());
            return creditLineAcceptance;
        } else if (!creditLine.getAccepted() && creditLine.getTimesRejected() >= 3) {
            creditLineAcceptance.setAcceptedCreditLine(0D);
            creditLineAcceptance.setAccepted(false);
            creditLineAcceptance.setTimesRejected(creditLine.getTimesRejected());
            return creditLineAcceptance;
        }

        Double recommendedCreditLine = getRecommendedCreditLine(creditLineRequest);
        creditLine.setUser(creditLineRequest.getUser());
        if (recommendedCreditLine >= creditLineRequest.getRequestedCreditLine()) {  //Accept
            creditLineAcceptance.setAccepted(true);
            creditLineAcceptance.setAcceptedCreditLine(creditLineRequest.getRequestedCreditLine());
            creditLineAcceptance.setTimesRejected(creditLine.getTimesRejected());
            creditLine.setAcceptedCreditLine(creditLineRequest.getRequestedCreditLine());
            creditLine.setAccepted(true);

        } else {  //Reject
            creditLineAcceptance.setAccepted(false);
            creditLine.setAcceptedCreditLine(0D);
            creditLine.setAccepted(false);
            creditLine.setTimesRejected(creditLine.getTimesRejected()+1);
            creditLineAcceptance.setTimesRejected(creditLine.getTimesRejected());
        }

        creditLineRepository.save(creditLine);

        return creditLineAcceptance;
    }


    @Override
    public Double getRecommendedCreditLine(CreditLineRequest creditLineRequest) {
        Double recommendedCreditLine;
        switch(creditLineRequest.getFoundingType()) {
            case "SME":
                recommendedCreditLine = getCreditLineByMonthlyRevenue(creditLineRequest.getMonthlyRevenue());
                break;
            case "Startup":
                recommendedCreditLine = Math.max(getCreditLineByCashBalance(creditLineRequest.getCashBalance()),
                        getCreditLineByMonthlyRevenue(creditLineRequest.getMonthlyRevenue()));
                break;
            default:
                recommendedCreditLine = 0d;
        }

        return recommendedCreditLine;
    }

    @Override
    public Double getCreditLineByCashBalance(Double cashBalance) {
        return cashBalance/3.0d;
    }

    @Override
    public Double getCreditLineByMonthlyRevenue(Double monthlyRevenue) {
        return monthlyRevenue/5.0d;
    }
}
