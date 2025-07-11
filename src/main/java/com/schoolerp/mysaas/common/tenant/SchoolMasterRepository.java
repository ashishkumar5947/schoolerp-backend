package com.schoolerp.mysaas.common.tenant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SchoolMasterRepository extends JpaRepository<SchoolMaster, Long> {
    Optional<SchoolMaster> findByTenantCode(String tenantCode);
    boolean existsByTenantCode(String tenantCode);
}
