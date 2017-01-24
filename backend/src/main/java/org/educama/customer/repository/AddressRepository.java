package org.educama.customer.repository;

import javax.transaction.Transactional;

import org.educama.customer.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA Repository for accessing AddressDS entities
 */
@Transactional
public interface AddressRepository extends JpaRepository<Address, Long> {
}
