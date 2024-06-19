package com.example.PTK_KTGK.repository;

import com.example.PTK_KTGK.Model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository  extends JpaRepository<OrderDetail, Long> {
}
