package com.tribal.creditline.repository;

import java.util.List;

import com.tribal.creditline.model.persist.CreditLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditLineRepository extends JpaRepository<CreditLine, Long>{
    CreditLine findByUser(String user);
    CreditLine save(CreditLine person);
    List<CreditLine> findAll();
}
