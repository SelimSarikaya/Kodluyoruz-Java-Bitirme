package com.ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticket.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

	Order findByUserId(int id);

}
