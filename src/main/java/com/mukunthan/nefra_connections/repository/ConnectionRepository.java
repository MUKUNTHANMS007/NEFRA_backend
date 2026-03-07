package com.mukunthan.nefra_connections.repository;

import com.mukunthan.nefra_connections.entity.Connection;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ConnectionRepository extends JpaRepository<Connection, Connection.ConnectionId> {
    List<Connection> findByInvestorId(Long investorId);
    List<Connection> findByEntrepreneurId(Long entrepreneurId);
}