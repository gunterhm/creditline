package com.tribal.creditline.service;

import org.springframework.http.ResponseEntity;

public interface RequestBlockerService {
    public ResponseEntity<Object> filterRequest();
}
