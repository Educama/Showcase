package org.educama.customer.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.educama.customer.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * JPA Repository for accessing Customer entities
 */
@Transactional
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Page<Customer> findByNameFirstnameAndNameLastname(String firstname, String lastname, Pageable pageable);
    
    Customer findByUuid(String uuid);

    List<Customer> findByAddress(@Param("idAddress") Long idAddress);

    @Query("SELECT p FROM Customer p WHERE lower(p.name.firstname) like lower(concat(:name, '%')) OR lower(p.name.lastname) like lower(concat(:name, '%'))")
    Page<Customer> findSuggestionByName(@Param("name") String name, Pageable pageable);
}
