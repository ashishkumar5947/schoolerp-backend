package com.schoolerp.mysaas.student.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.schoolerp.mysaas.common.dto.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
@ToString(exclude = "documents")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "students")
public class Student extends AuditableEntity {
    
    // 🔐 Multi-Tenant Support
    @Column(nullable = false)
    private Long schoolId;

    // 👨‍🎓 Core Identity
    @Column(nullable = false, unique = true)
    private String admissionNo;

    @Column(nullable = false)
    private String firstName;

    private String lastName;

    // 🏫 Academic Info
    @Column(nullable = false)
    private String className;

    private String section;
    private Integer rollNumber;
    private LocalDate admissionDate;
    private String previousSchoolName;

    // 👤 Personal Info
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "personal_info_id")
    private PersonalInfo personalInfo;

    // 📞 Contact Info
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_info_id")
    private ContactInfo contactInfo;

    // 👪 Guardian Info
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "guardian_info_id")
    private GuardianInfo guardianInfo;

    // 🏨 Hostel Info
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hostel_info_id")
    private HostelInfo hostelInfo;


    // 📄 Document Info (1:N)
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Document> documents = new ArrayList<>();

    // 📱 Mobile Integration
    private String parentPortalUserId;
    private String firebaseUid;

    // 📌 Status
    private Boolean isActive = true;
    private LocalDate exitDate;

    @Column(columnDefinition = "TEXT")
    private String remarks;
}
