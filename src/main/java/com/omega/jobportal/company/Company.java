package com.omega.jobportal.company;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "company_name")
    private String companyName;

    @Column(nullable = false, length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "business_stream_id")
    private BusinessStream businessStreamId;

    @Column(nullable = false, name = "establishment_date")
    private LocalDateTime establishmentDate;

    @Column(name = "website_url")
    private String websiteUrl;
}
