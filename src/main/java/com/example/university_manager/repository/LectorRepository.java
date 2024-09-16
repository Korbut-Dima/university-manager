package com.example.university_manager.repository;

import com.example.university_manager.entity.Lector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectorRepository extends JpaRepository<Lector, Long> {

    @Query(value = "SELECT * FROM lectors l " +
            "WHERE LOWER(l.first_name) LIKE LOWER(CONCAT('%', :namePart, '%')) " +
            "OR LOWER(l.second_name) LIKE LOWER(CONCAT('%', :namePart, '%')) " +
            "OR LOWER(l.last_name) LIKE LOWER(CONCAT('%', :namePart, '%'))", nativeQuery = true)
    List<Lector> findLectorsByNamePart(@Param("namePart") String namePart);
}
