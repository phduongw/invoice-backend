package com.light.invoices.services.impl;

import com.light.invoices.controllers.request.CreatePayoutRequest;
import com.light.invoices.entities.PayoutEntity;
import com.light.invoices.entities.enums.PayoutStatus;
import com.light.invoices.exception.BusinessException;
import com.light.invoices.exception.NotFoundException;
import com.light.invoices.repositories.PayoutRepository;
import com.light.invoices.services.PayoutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayoutServiceImpl implements PayoutService {

    private final PayoutRepository payoutRepository;

    @Override
    @Transactional
    public PayoutEntity markAsProcessing(UUID id, String processedBy) {
        if (Objects.isNull(id)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(), "Id cannot be null");
        }

        var payout = findById(id);
        if (!PayoutStatus.PENDING.equals(payout.getStatus())) {
            throw new BusinessException(HttpStatus.CONFLICT.value(), "Payout is not pending");
        }

        try {
            payout.setStatus(PayoutStatus.PROCESSING);
            payout.setProcessedBy(processedBy);

            Thread.sleep(4000);
            payoutRepository.save(payout);

            return payout;
        } catch (Exception e) {
            log.error("Error while marking payout as processing", e);
            throw new IllegalArgumentException("Error while marking payout as processing");
        }
    }

    @Override
    public PayoutEntity createPayout(CreatePayoutRequest payout) {
        var payoutEntity = new PayoutEntity();
        payoutEntity.setAmount(payout.getAmount());
        payoutEntity.setPayoutCurrency(payout.getCurrency());
        payoutEntity.setStatus(PayoutStatus.PENDING);

        return payoutRepository.save(payoutEntity);
    }

    @Override
    public PayoutEntity findById(UUID id) {
        if (Objects.isNull(id)) throw new IllegalArgumentException("Id cannot be null");

        return payoutRepository.findById(id).orElseThrow(() -> new NotFoundException("Payout not found"));
    }

    private Boolean isExistById(UUID id) {
        return payoutRepository.existsById(id);
    }
}
