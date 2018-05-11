package com.starwars_batch.repository;

import com.starwars_batch.model.People;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleRepository extends JpaRepository<People,Long> {}
