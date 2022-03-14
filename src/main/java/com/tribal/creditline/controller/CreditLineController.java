package com.tribal.creditline.controller;

import com.tribal.creditline.model.CreditLineAcceptance;
import com.tribal.creditline.model.CreditLineRequest;
import com.tribal.creditline.service.CreditLineService;
import com.tribal.creditline.service.RequestBlockerService;
import com.tribal.creditline.service.RequestCounterService;
import com.tribal.creditline.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class CreditLineController {

    @Autowired
    CreditLineService creditLineService;

    @Autowired
    RequestCounterService requestCounterService;

    @Autowired
    RequestBlockerService requestBlockerService;


    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @PostMapping("/creditline")
    public ResponseEntity<Object> getCreditLine (@RequestHeader("Authorization") String authorization,
                                                 @Valid @RequestBody CreditLineRequest creditLineRequest) {
        ResponseEntity<Object> httpErrorResponse = requestBlockerService.filterRequest();
        if (null != httpErrorResponse) {
            return(httpErrorResponse);
        }

        String user = AuthUtil.getUserName(authorization);
        creditLineRequest.setUser(user);
        CreditLineAcceptance creditLineAcceptance = creditLineService.processCreditLineRequest(creditLineRequest);
        if (creditLineAcceptance.getTimesRejected() >= 3) {
            return ResponseEntity.ok("A sales agent will contact you.");
        }
        requestCounterService.processResponse(creditLineAcceptance);
        return (ResponseEntity.ok(creditLineAcceptance));
    }
}
