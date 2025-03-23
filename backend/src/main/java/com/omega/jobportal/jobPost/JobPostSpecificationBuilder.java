package com.omega.jobportal.jobPost;

import com.omega.jobportal.jobPost.data.JobPostFilterQuery;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JobPostSpecificationBuilder {
    public static Specification<JobPost> filterJobs(JobPostFilterQuery filterQuery) {
        return (root, _, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Optional.ofNullable(filterQuery.jobType())
                    .ifPresent(jobType -> predicates.add(criteriaBuilder.equal(root.get("jobType"), jobType)));

            Optional.ofNullable(filterQuery.location())
                    .ifPresent(location -> predicates.add(criteriaBuilder.equal(root.get("location"), location)));

            Optional.ofNullable(filterQuery.workMode())
                    .ifPresent(workMode -> predicates.add(criteriaBuilder.equal(root.get("workMode"), workMode)));

            Optional.ofNullable(filterQuery.experienceLevel())
                    .ifPresent(experienceLevel -> predicates.add(criteriaBuilder.equal(root.get("experienceLevel"), experienceLevel)));

            Optional.ofNullable(filterQuery.minSalary())
                    .ifPresent(minSalary -> predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("salary"), minSalary)));

            Optional.ofNullable(filterQuery.maxSalary())
                    .ifPresent(maxSalary -> predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("salary"), maxSalary)));

            Optional.ofNullable(filterQuery.datePosted())
                    .ifPresent(datePosted -> predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), datePosted)));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
