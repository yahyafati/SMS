package com.yahya.growth.stockmanagementsystem.dao.security;

import com.yahya.growth.stockmanagementsystem.model.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorityDao extends JpaRepository<Authority, Integer> {

    Optional<Authority> findAuthorityByName(String name);
}
