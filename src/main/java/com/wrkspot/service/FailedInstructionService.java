package com.wrkspot.service;

import com.wrkspot.entity.FailedInstructions;
import com.wrkspot.repository.FailedInstructionRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.jbosslog.JBossLog;

@ApplicationScoped
@JBossLog
public class FailedInstructionService {

   @Inject
   FailedInstructionRepository repo;

    @Transactional
    public  void logFailedInstruction(String message, String topic, String messageType, Throwable e) {
        try{
        FailedInstructions failedInstructions = FailedInstructions.builder()
                .message(message)
                .topic(topic)
                .messageType(messageType)
                .reason(e.getMessage())
                .build();
        repo.persist(failedInstructions);
        } catch (Exception ex){
            log.errorf("failed to persist to failiure log %s , %s " ,ex.getMessage(), ex);
        }
    }
}
