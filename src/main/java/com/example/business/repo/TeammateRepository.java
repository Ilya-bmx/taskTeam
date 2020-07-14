package com.example.business.repo;

import com.example.business.entity.Team;
import com.example.business.entity.Teammate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeammateRepository extends JpaRepository<Teammate, Long> {
    List<Teammate> findAllByTeam(Team team);
}
