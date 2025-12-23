package com.light.invoices.repositories;

import com.light.invoices.entities.PayoutEntity;
import jakarta.persistence.LockModeType;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PayoutRepository extends JpaRepository<PayoutEntity, UUID> {

    Optional<PayoutEntity> findByIdAndStatus(UUID id, String status);

    @Lock(LockModeType.PESSIMISTIC_READ)
    Optional<PayoutEntity> findById(@NonNull UUID id);
}
