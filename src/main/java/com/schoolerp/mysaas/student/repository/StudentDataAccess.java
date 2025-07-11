package com.schoolerp.mysaas.student.repository;

import com.schoolerp.mysaas.db.DatabaseUtil;
import com.schoolerp.mysaas.student.entity.Student;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentDataAccess {
    private static final Logger logger = LoggerFactory.getLogger(StudentDataAccess.class);
    private final DatabaseUtil databaseUtil;

    @Autowired
    public StudentDataAccess(DatabaseUtil databaseUtil) {
        this.databaseUtil = databaseUtil;
    }

    /**
     * Saves a student entity with tenant and channel context.
     * This is a transactional operation.
     *
     * @param tenantCode   The tenant identifier (e.g. SCHOOL_ABC)
     * @param channelCode  The channel identifier (e.g. WEB, MOBILE)
     * @param student      The student entity to be saved
     */
    public void saveStudent(String tenantCode, String channelCode, Student student) {
        try {
            logger.info("📥 Saving student | tenant: [{}], channel: [{}], name: {} {}",
                    tenantCode, channelCode, student.getFirstName(), student.getLastName());

            databaseUtil.save(tenantCode, channelCode, student);

            logger.info("✅ Student saved successfully | tenant: [{}], studentId: {}", tenantCode, student.getId());
        } catch (Exception ex) {
            logger.error("❌ Failed to save student | tenant: [{}], reason: {}", tenantCode, ex.getMessage(), ex);
            throw new RuntimeException("Unable to save student. Please contact support.");
        }
    }

    public List<Object[]> fetchRowsWithPagination(String sql, int page, int size, Object... params) {
        try {
            return databaseUtil.fetchRowsWithPagination(sql, page, size, params);
        } catch (Exception ex) {
            logger.error("❌ Error in fetchRowsWithPagination: {}", ex.getMessage(), ex);
            throw new RuntimeException("Unable to fetch student list");
        }
    }

    public Long fetchCount(String sql, Object... params) {
        try {
            return databaseUtil.fetchCount(sql, params);
        } catch (Exception ex) {
            logger.error("❌ Error in fetchCount: {}", ex.getMessage(), ex);
            throw new RuntimeException("Unable to count student list");
        }
    }

}
