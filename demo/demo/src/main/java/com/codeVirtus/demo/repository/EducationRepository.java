package com.codeVirtus.demo.repository;

import com.codeVirtus.demo.models.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {
    Education findByEducation(String education);
}
