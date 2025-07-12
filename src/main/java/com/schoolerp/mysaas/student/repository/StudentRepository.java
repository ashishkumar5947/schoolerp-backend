package com.schoolerp.mysaas.student.repository;

import com.schoolerp.mysaas.student.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<Student, String> {

    @Query("""
        SELECT s FROM Student s
        WHERE (:className IS NULL OR s.className = :className)
          AND (:section IS NULL OR s.section = :section)
          AND (:rollNumber IS NULL OR s.rollNumber = :rollNumber)
          AND (:search IS NULL OR LOWER(s.firstName) LIKE LOWER(CONCAT('%', :search, '%')))
    """)
    Page<Student> findFilteredStudents(
            @Param("className") String className,
            @Param("section") String section,
            @Param("rollNumber") Integer rollNumber,
            @Param("search") String search,
            Pageable pageable
    );
}
