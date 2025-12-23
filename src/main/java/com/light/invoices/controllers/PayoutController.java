package com.light.invoices.controllers;

import com.light.invoices.controllers.request.CreatePayoutRequest;
import com.light.invoices.controllers.request.MarkProcessingRequest;
import com.light.invoices.controllers.response.ResponseApi;
import com.light.invoices.entities.PayoutEntity;
import com.light.invoices.services.PayoutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/payouts")
public class PayoutController {

    private final PayoutService payoutService;

    @PostMapping
    public ResponseApi<PayoutEntity> createPayout(@RequestBody CreatePayoutRequest payout) {
        return new ResponseApi<PayoutEntity>().ok(payoutService.createPayout(payout));
    }

    @PostMapping("/{id}/mark-processing")
    public ResponseApi<PayoutEntity> markProcessing(@PathVariable UUID id, @RequestBody MarkProcessingRequest request) {
        return new ResponseApi<PayoutEntity>().ok(payoutService.markAsProcessing(id, request.getProcessedBy()));
    }
}
