package com.schoolerp.mysaas.common.fieldValidation.repository;

import com.schoolerp.mysaas.common.fieldValidation.FieldValidationRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FieldValidationRuleRepository extends JpaRepository<FieldValidationRule, UUID> {
    List<FieldValidationRule> findByModuleAndIsActiveTrue(String entityName);
}
