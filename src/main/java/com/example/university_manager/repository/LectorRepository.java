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

    @Query(value = "SELECT DISTINCT l2.* FROM lectors l2 " +
            "JOIN department_lectors dl2 ON l2.id = dl2.lector_id " +
            "WHERE dl2.department_id IN (" +
            "  SELECT dl1.department_id FROM department_lectors dl1 " +
            "  JOIN lectors l1 ON l1.id = dl1.lector_id " +
            "  WHERE LOWER(l1.first_name) LIKE LOWER(CONCAT('%', :namePart, '%')) " +
            "  OR LOWER(l1.second_name) LIKE LOWER(CONCAT('%', :namePart, '%')) " +
            "  OR LOWER(l1.last_name) LIKE LOWER(CONCAT('%', :namePart, '%')) " +
            ") " +
            "AND l2.id NOT IN (" +
            "  SELECT l1.id FROM lectors l1 " +
            "  WHERE LOWER(l1.first_name) LIKE LOWER(CONCAT('%', :namePart, '%')) " +
            "  OR LOWER(l1.second_name) LIKE LOWER(CONCAT('%', :namePart, '%')) " +
            "  OR LOWER(l1.last_name) LIKE LOWER(CONCAT('%', :namePart, '%')) " +
            ")", nativeQuery = true)
    List<Lector> findGroupmatesByLectorNamePart(@Param("namePart") String namePart);
}
