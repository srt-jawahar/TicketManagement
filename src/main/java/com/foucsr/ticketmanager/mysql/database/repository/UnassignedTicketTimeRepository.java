package com.foucsr.ticketmanager.mysql.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foucsr.ticketmanager.mysql.database.model.UnAssignedTicket;

@Repository
public interface UnassignedTicketTimeRepository extends JpaRepository<UnAssignedTicket, Long>
{

}
