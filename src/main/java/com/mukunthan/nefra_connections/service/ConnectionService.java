package com.mukunthan.nefra_connections.service;

import com.mukunthan.nefra_connections.dto.ConnectionRequestDTO;
import com.mukunthan.nefra_connections.dto.ConnectionResponseDTO;
import com.mukunthan.nefra_connections.entity.Connection;
import com.mukunthan.nefra_connections.entity.User;
import com.mukunthan.nefra_connections.repository.ConnectionRepository;
import com.mukunthan.nefra_connections.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConnectionService {

    private final ConnectionRepository connectionRepository;
    private final UserRepository userRepository;

    @Transactional
    public ConnectionResponseDTO initiateConnection(ConnectionRequestDTO request) {
        User investor = userRepository.findById(request.investorId())
                .orElseThrow(() -> new RuntimeException("Investor not found in database."));

        User entrepreneur = userRepository.findById(request.entrepreneurId())
                .orElseThrow(() -> new RuntimeException("Entrepreneur not found in database."));

        Connection connection = new Connection();
        connection.setInvestor(investor);
        connection.setEntrepreneur(entrepreneur);

        Connection savedConnection = connectionRepository.save(connection);

        return new ConnectionResponseDTO(
                savedConnection.getInvestor().getId(),
                savedConnection.getInvestor().getFullName(),
                savedConnection.getEntrepreneur().getId(),
                savedConnection.getEntrepreneur().getFullName(),
                savedConnection.getStatus().name()
        );
    }
}