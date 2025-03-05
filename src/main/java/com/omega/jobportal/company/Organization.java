package com.omega.jobportal.company;


import com.omega.jobportal.company.businessStream.BusinessStream;
import com.omega.jobportal.company.data.OrganizationRegistrationRequest;
import com.omega.jobportal.user.AppUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "organizations")
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "recruiter_id")
    private AppUser recruiter;

    @Column(nullable = false, name = "organization_name")
    private String organizationName;

    @Column(nullable = false, length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "business_stream_id")
    private BusinessStream businessStream;

    @Column(nullable = false, name = "establishment_date")
    private LocalDate establishmentDate;

    @Column(nullable = false, name = "organization_location")
    private String location;

    @Column(name = "website_url")
    private String websiteUrl;


    public Organization(OrganizationRegistrationRequest request, BusinessStream businessStream, AppUser recruiter) {
        this.websiteUrl = request.websiteUrl();
        this.location = request.companyLocation();
        this.establishmentDate = request.establishmentDate();
        this.description = request.description();
        this.organizationName = request.companyName();
        this.businessStream = businessStream;
        this.recruiter = recruiter;
    }
}
