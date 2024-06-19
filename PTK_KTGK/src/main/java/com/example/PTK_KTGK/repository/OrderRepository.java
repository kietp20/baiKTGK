package com.example.PTK_KTGK.repository;

import com.example.PTK_KTGK.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository

public interface OrderRepository  extends JpaRepository<Order, Long>  {
}
