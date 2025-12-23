package com.light.invoices.entities;

import com.light.invoices.entities.enums.PayoutStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payouts")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class PayoutEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    @Column
    UUID vendorId;

    @Column
    BigDecimal amount;

    @Column(length = 3)
    String payoutCurrency;

    @Column
    @Enumerated(EnumType.STRING)
    PayoutStatus status;

    @CreatedDate
    @Column(updatable = false)
    Instant requestedAt;

    @LastModifiedDate
    @Column(insertable = false)
    Instant updateAt;

    @Column
    String processedBy;
}
