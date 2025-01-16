package com.omega.jobportal.recruiter;

import jakarta.persistence.*;

@Entity
@Table(name = "business_streams")
public class BusinessStream {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "business_name")
    private String businessName;
}
