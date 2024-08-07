package com.rtrnonato.library_management.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rtrnonato.library_management.entities.LoanItem;

public interface LoanItemRepository extends JpaRepository<LoanItem, Integer>{
}