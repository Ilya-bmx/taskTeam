package com.example.business.repo;

import com.example.business.entity.Teammate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeammateRepository extends JpaRepository<Teammate, Long> {
}
