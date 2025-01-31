package com.omega.jobportal.company;


import com.omega.jobportal.company.businessStream.BusinessStream;
import com.omega.jobportal.company.data.CompanyRegistrationRequest;
import com.omega.jobportal.location.Location;
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
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "recruiter_id")
    private AppUser recruiter;

    @Column(nullable = false, name = "company_name")
    private String companyName;

    @Column(nullable = false, length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "business_stream_id")
    private BusinessStream businessStream;

    @Column(nullable = false, name = "establishment_date")
    private LocalDate establishmentDate;

    @ManyToOne
    @JoinColumn(name = "company_location")
    private Location companyLocation;

    @Column(name = "website_url")
    private String websiteUrl;


    public Company(CompanyRegistrationRequest request, Location location, BusinessStream businessStream, AppUser recruiter) {
        this.websiteUrl = request.websiteUrl();
        this.companyLocation = location;
        this.establishmentDate = request.establishmentDate();
        this.description = request.description();
        this.companyName = request.companyName();
        this.businessStream = businessStream;
        this.recruiter = recruiter;
    }
}
