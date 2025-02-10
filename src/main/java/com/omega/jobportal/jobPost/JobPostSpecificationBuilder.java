package com.omega.jobportal.jobPost;

import com.omega.jobportal.jobPost.data.JobPostFilterQuery;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JobPostSpecificationBuilder {
    public static Specification<JobPost> filterJobs(JobPostFilterQuery filterQuery) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Optional.ofNullable(filterQuery.jobType())
                    .ifPresent(jobType -> predicates.add(criteriaBuilder.equal(root.get("jobType"), jobType)));

            Optional.ofNullable(filterQuery.locationId())
                    .ifPresent(locationId -> predicates.add(criteriaBuilder.equal(root.get("locationId"), locationId)));

            Optional.ofNullable(filterQuery.workMode())
                    .ifPresent(workMode -> predicates.add(criteriaBuilder.equal(root.get("workMode"), workMode)));

            Optional.ofNullable(filterQuery.experienceLevel())
                    .ifPresent(experienceLevel -> predicates.add(criteriaBuilder.equal(root.get("experienceLevel"), experienceLevel)));

            Optional.ofNullable(filterQuery.datePosted())
                    .ifPresent(datePosted -> predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), datePosted)));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
