package com.sermaluc.retotecnico.h2;

import com.sermaluc.retotecnico.h2.model.ClientData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<ClientData, String> {
    ClientData findByEmail(String email);
}