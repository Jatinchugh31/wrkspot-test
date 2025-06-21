package com.wrkspot.repository;

import com.wrkspot.entity.FailedInstructions;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class FailedInstructionRepository implements PanacheRepositoryBase<FailedInstructions, UUID> {
}
