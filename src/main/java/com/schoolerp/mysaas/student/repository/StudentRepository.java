package com.schoolerp.mysaas.student.repository;

import com.schoolerp.mysaas.student.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<Student, String> {

    @Query(value = """
    SELECT * FROM students
    WHERE (:className IS NULL OR class_name = :className)
      AND (:section IS NULL OR section = :section)
      AND (:rollNumber IS NULL OR roll_number = :rollNumber)
      AND (:search IS NULL OR LOWER(first_name) LIKE LOWER(CONCAT('%', :search, '%')))
    """,
            countQuery = """
    SELECT COUNT(*) FROM students
    WHERE (:className IS NULL OR class_name = :className)
      AND (:section IS NULL OR section = :section)
      AND (:rollNumber IS NULL OR roll_number = :rollNumber)
      AND (:search IS NULL OR LOWER(first_name) LIKE LOWER(CONCAT('%', :search, '%')))
    """,
            nativeQuery = true
    )
    Page<Student> findFilteredStudents(
            @Param("className") String className,
            @Param("section") String section,
            @Param("rollNumber") Integer rollNumber,
            @Param("search") String search,
            Pageable pageable
    );

}
