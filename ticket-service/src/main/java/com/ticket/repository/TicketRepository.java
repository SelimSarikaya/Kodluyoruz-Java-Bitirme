package com.ticket.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ticket.model.Ticket;
import com.ticket.model.enums.TransportationType;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

	Optional<Ticket> findByNo(int no);

	List<Ticket> findAllByDepartureLocation(String departureLocation);

	List<Ticket> findAllByArrivalLocation(String arrivalLocation);

	List<Ticket> findAllByTransportationType(TransportationType airplane);

	List<Ticket> findAllByDepartureTime(LocalDate departureTime);

	List<Ticket> findAllByArrivalTime(LocalDate arrivalTime);
	
	Optional<List<Ticket>> findByDepartureLocationAndArrivalLocationAndDepartureTimeAndArrivalTime(String departureLocation, String arrivalLocation, LocalDate departureTime, LocalDate arrivalTime);
	

}
