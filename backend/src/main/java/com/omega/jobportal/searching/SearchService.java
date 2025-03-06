package com.omega.jobportal.searching;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.search.mapper.orm.Search;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final EntityManager entityManager;

    @Transactional
    public <T, R> Page<R> search(Class<T> entityClazz, String searchQuery, int page, int size, List<String> searchFields, Function<T, R> dtoMapper) {
        List<R> searchResults = Search.session(entityManager)
                .search(entityClazz)
                .where(field -> field.match()
                        .fields(searchFields.toArray(new String[0]))
                        .matching(searchQuery))
                .fetchHits(page * size, size)
                .stream().map(dtoMapper).toList();

        return new PageImpl<>(searchResults, PageRequest.of(page, size), searchResults.size());
    }
}
