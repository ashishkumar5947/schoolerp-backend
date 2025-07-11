package com.schoolerp.mysaas.student.util;

import com.schoolerp.mysaas.student.dto.*;
import com.schoolerp.mysaas.student.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class StudentMapperImpl implements StudentMapper {

    private static final Logger logger = LoggerFactory.getLogger(StudentMapperImpl.class);

    @Override
    public Student toEntity(StudentCreateRequest request) {
        if (request == null) return null;

        logger.debug("Starting to map StudentCreateRequest to Student entity...");

        Student student = new Student();

        // 🎯 Core Identity
        logger.debug("Mapping Core Identity...");
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setClassName(request.getClassName());
        student.setSection(request.getSection());
        student.setRollNumber(request.getRollNumber());
        student.setAdmissionDate(request.getAdmissionDate());
        student.setPreviousSchoolName(request.getPreviousSchoolName());

        // 👤 Personal Info
        logger.debug("Mapping Personal Info...");
        Optional.ofNullable(request.getPersonalInfo()).ifPresent(pi -> {
            PersonalInfo personalInfo = PersonalInfo.builder()
                    .dob(pi.getDob())
                    .gender(pi.getGender())
                    .bloodGroup(pi.getBloodGroup())
                    .nationality(pi.getNationality())
                    .religion(pi.getReligion())
                    .birthCertificateNumber(pi.getBirthCertificateNumber())
                    .citizenshipNumber(pi.getCitizenshipNumber())
                    .passportNumber(pi.getPassportNumber())
                    .build();
            student.setPersonalInfo(personalInfo);
        });

        // 📞 Contact Info
        logger.debug("Mapping Contact Info...");
        Optional.ofNullable(request.getContactInfo()).ifPresent(ci -> {
            ContactInfo contactInfo = ContactInfo.builder()
                    .email(ci.getEmail())
                    .phone(ci.getPhone())
                    .build();
            student.setContactInfo(contactInfo);
        });

        // 👪 Guardian Info
        logger.debug("Mapping Guardian Info...");
        Optional.ofNullable(request.getGuardianInfo()).ifPresent(gi -> {
            GuardianInfo guardianInfo = GuardianInfo.builder()
                    .guardianName(gi.getGuardianName())
                    .guardianRelation(gi.getGuardianRelation())
                    .guardianEmail(gi.getGuardianEmail())
                    .guardianPhone(gi.getGuardianPhone())
                    .build();
            student.setGuardianInfo(guardianInfo);
        });

        // 🏠 Address Info
        logger.debug("Mapping Address Info...");
        Optional.ofNullable(request.getAddress()).ifPresent(ad -> {
            AddressInfo addressInfo = new AddressInfo();
            addressInfo.setProvince(ad.getProvince());
            addressInfo.setDistrict(ad.getDistrict());
            addressInfo.setMunicipality(ad.getMunicipality());
            addressInfo.setWardNumber(ad.getWardNumber());
            addressInfo.setTole(ad.getTole());
            addressInfo.setHouseNumber(ad.getHouseNumber());
            addressInfo.setAdditionalInfo(ad.getAdditionalInfo());
            student.getGuardianInfo().setAddressInfo(addressInfo);
        });

        // 🛏 Hostel Info
        logger.debug("Mapping Hostel Info...");
        Optional.ofNullable(request.getHostelInfo()).ifPresent(hi -> {
            HostelInfo hostelInfo = HostelInfo.builder()
                    .isHostelStudent(hi.getIsHostelStudent())
                    .hostelName(hi.getHostelName())
                    .roomNumber(hi.getRoomNumber())
                    .bedNumber(hi.getBedNumber())
                    .floor(hi.getFloor())
                    .wardenName(hi.getWardenName())
                    .wardenPhone(hi.getWardenPhone())
                    .hostelJoinDate(hi.getHostelJoinDate())
                    .hostelLeaveDate(hi.getHostelLeaveDate())
                    .hasKeyCard(hi.getHasKeyCard())
                    .isMealOpted(hi.getIsMealOpted())
                    .emergencyContactName(hi.getEmergencyContactName())
                    .emergencyContactPhone(hi.getEmergencyContactPhone())
                    .specialNotes(hi.getSpecialNotes())
                    .build();
            student.setHostelInfo(hostelInfo);
        });

        // 📄 Document Info
        logger.debug("Mapping Document Info...");
        Optional.ofNullable(request.getDocumentInfoDTO()).ifPresent(docs -> {
            List<Document> documentEntities = docs.stream()
                    .map(doc -> Document.builder()
                            .type(doc.getType())
                            .fileUrl(doc.getFileUrl())
                            .uploadedAt(doc.getUploadedAt())
                            .uploadedBy(doc.getUploadedBy())
                            .build())
                    .toList();

            // Back-reference lagana zaroori hai
            documentEntities.forEach(doc -> doc.setStudent(student));

            student.setDocuments(documentEntities);
        });

        // 🔐 App Integration
        logger.debug("Mapping App Integration...");
        student.setParentPortalUserId(request.getParentPortalUserId());
        student.setFirebaseUid(request.getFirebaseUid());

        // 📌 Status
        logger.debug("Mapping Status Info...");
        student.setIsActive(request.getIsActive());
        student.setExitDate(request.getExitDate());
        student.setRemarks(request.getRemarks());

        logger.debug("✅ Finished mapping StudentCreateRequest to Student");
        return student;
    }

    @Override
    public StudentCreateResponse toResponse(Student student) {
        if (student == null) return null;

        return StudentCreateResponse.builder()
                .studentId(student.getId())
                .admissionNo(student.getAdmissionNo())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .className(student.getClassName())
                .section(student.getSection())
                .rollNumber(student.getRollNumber())
                .admissionDate(student.getAdmissionDate())
                .previousSchoolName(student.getPreviousSchoolName())

                // 👤 Personal Info (null-safe)
                .personalInfo(Optional.ofNullable(student.getPersonalInfo()).map(pi -> PersonalInfoDTO.builder()
                        .dob(pi.getDob())
                        .gender(pi.getGender())
                        .bloodGroup(pi.getBloodGroup())
                        .nationality(pi.getNationality())
                        .religion(pi.getReligion())
                        .birthCertificateNumber(pi.getBirthCertificateNumber())
                        .citizenshipNumber(pi.getCitizenshipNumber())
                        .passportNumber(pi.getPassportNumber())
                        .build()).orElse(null))

                // 📞 Contact Info
                .contactInfo(Optional.ofNullable(student.getContactInfo()).map(ci -> ContactInfoDTO.builder()
                        .email(ci.getEmail())
                        .phone(ci.getPhone())
                        .build()).orElse(null))

                // 👪 Guardian Info
                .guardianInfo(Optional.ofNullable(student.getGuardianInfo()).map(gi -> GuardianInfoDTO.builder()
                        .guardianName(gi.getGuardianName())
                        .guardianRelation(gi.getGuardianRelation())
                        .guardianEmail(gi.getGuardianEmail())
                        .guardianPhone(gi.getGuardianPhone())
                        .build()).orElse(null))

                // 🏠 Address Info
                .address(Optional.ofNullable(student.getGuardianInfo().getAddressInfo()).map(ai -> AddressDTO.builder()
                        .province(ai.getProvince())
                        .district(ai.getDistrict())
                        .municipality(ai.getMunicipality())
                        .wardNumber(ai.getWardNumber())
                        .tole(ai.getTole())
                        .houseNumber(ai.getHouseNumber())
                        .additionalInfo(ai.getAdditionalInfo())
                        .build()).orElse(null))

                // 🛏 Hostel Info
                .hostelInfo(Optional.ofNullable(student.getHostelInfo()).map(hi -> HostelInfoDTO.builder()
                        .isHostelStudent(hi.getIsHostelStudent())
                        .hostelName(hi.getHostelName())
                        .roomNumber(hi.getRoomNumber())
                        .bedNumber(hi.getBedNumber())
                        .floor(hi.getFloor())
                        .wardenName(hi.getWardenName())
                        .wardenPhone(hi.getWardenPhone())
                        .hostelJoinDate(hi.getHostelJoinDate())
                        .hostelLeaveDate(hi.getHostelLeaveDate())
                        .hasKeyCard(hi.getHasKeyCard())
                        .isMealOpted(hi.getIsMealOpted())
                        .emergencyContactName(hi.getEmergencyContactName())
                        .emergencyContactPhone(hi.getEmergencyContactPhone())
                        .specialNotes(hi.getSpecialNotes())
                        .build()).orElse(null))

                // 🔐 App Integration
                .parentPortalUserId(student.getParentPortalUserId())
                .firebaseUid(student.getFirebaseUid())

                // 📌 Status
                .isActive(student.getIsActive())
                .exitDate(student.getExitDate())
                .remarks(student.getRemarks())

                // 🕒 Audit Info
                .createdBy(student.getCreatedBy())
                .createdAt(Optional.ofNullable(student.getCreatedAt()).map(Object::toString).orElse(null))

                .build();
    }

}
