package com.example.university_manager.repository;

import com.example.university_manager.entity.Department;
import com.example.university_manager.entity.Lector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findByName(String departmentName);

    @Query(value = "SELECT l.degree, COUNT(l.id) " +
            "FROM lectors l " +
            "JOIN department_lectors dl ON l.id = dl.lector_id " +
            "JOIN departments d ON d.id = dl.department_id " +
            "WHERE LOWER(d.name) = LOWER(:departmentName) " +
            "GROUP BY l.degree", nativeQuery = true)
    Optional<List<Object[]>> findDegreeCountsByDepartment(@Param("departmentName") String departmentName);

    @Query(value = "SELECT ROUND(AVG(l.salary), 2) " +
            "FROM lectors l " +
            "JOIN department_lectors dl ON l.id = dl.lector_id " +
            "JOIN departments d ON d.id = dl.department_id " +
            "WHERE LOWER(d.name) = LOWER(:departmentName)", nativeQuery = true)
    Optional<BigDecimal> findAverageSalaryByDepartmentName(@Param("departmentName") String departmentName);

    @Query(value = "SELECT COUNT(l.id) " +
            "FROM lectors l " +
            "JOIN department_lectors dl ON l.id = dl.lector_id " +
            "JOIN departments d ON d.id = dl.department_id " +
            "WHERE LOWER(d.name) = LOWER(:departmentName)", nativeQuery = true)
    Optional<Integer> findLectorCountByDepartmentName(@Param("departmentName") String departmentName);

}
