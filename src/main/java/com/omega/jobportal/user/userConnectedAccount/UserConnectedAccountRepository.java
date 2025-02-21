package com.omega.jobportal.user.userConnectedAccount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserConnectedAccountRepository extends JpaRepository<UserConnectedAccount, Long> {

    @Query("""
                        SELECT uca
                        FROM UserConnectedAccount uca
                        where uca.providerId = :providerId
                        AND uca.providerName = :provider
            """)
    Optional<UserConnectedAccount> findByProviderIdAndProvider(String providerId, String provider);
}
