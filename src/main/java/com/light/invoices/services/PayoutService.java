package com.light.invoices.services;

import com.light.invoices.controllers.request.CreatePayoutRequest;
import com.light.invoices.entities.PayoutEntity;

import java.util.UUID;

public interface PayoutService {

    PayoutEntity markAsProcessing(UUID id, String processedBy);
    PayoutEntity createPayout(CreatePayoutRequest payout);
    PayoutEntity findById(UUID id);

}
