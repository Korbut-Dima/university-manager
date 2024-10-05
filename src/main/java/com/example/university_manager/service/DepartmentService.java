package com.example.university_manager.service;

import com.example.university_manager.entity.Department;
import com.example.university_manager.entity.Lector;
import com.example.university_manager.entity.Rank;
import com.example.university_manager.exception.DepartmentIsEmptyException;
import com.example.university_manager.exception.DepartmentNotFoundException;
import com.example.university_manager.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DepartmentService {

    private DepartmentRepository repository;

    public DepartmentService(DepartmentRepository repository) {
        this.repository = repository;
    }

    public String getHeadOfDepartmentName(String departmentName) {
        Department department = departmentExistCheck(departmentName);

        Lector head = department.getDepartmentHead();
        if (head != null) {
            return head.getSecondName() == null ? head.getFirstName() + " " + head.getLastName() :
                    head.getFirstName() + " " + head.getSecondName() + " " + head.getLastName();
        }
        throw new DepartmentIsEmptyException(departmentName +  " department does not have any head");
    }

    public Map<Rank, Integer> getStatistic(String departmentName ) {
        departmentExistCheck(departmentName);

        List<Object[]> results = repository.findDegreeCountsByDepartment(departmentName).orElseThrow(
                () -> new DepartmentIsEmptyException(departmentName +  " department does not have any member")
        );

        Map<Rank, Integer> degreeCounts = new HashMap<>();
        for (Object[] result : results) {
            String rankValue = (String) result[0];
            Rank rank = Rank.valueOf(rankValue);

            Long count = ((Number) result[1]).longValue();
            degreeCounts.put(rank, count.intValue());
        }

        for (Rank rank : Rank.values()) {
            degreeCounts.computeIfAbsent(rank, r -> 0);
        }

        /*for (Rank rank : Rank.values()) {
            if( !degreeCounts.keySet().contains(rank) ) {
                degreeCounts.put(rank, 0);
            }
        }*/

        return degreeCounts;
    }

    public BigDecimal getAverageSalary( String departmentName ) {
        departmentExistCheck(departmentName);
        return repository.findAverageSalaryByDepartmentName(departmentName).orElseThrow(
                () -> new DepartmentIsEmptyException(departmentName +  " department does not have any member")
        );
    }

    public Integer getCountOfLectors( String departmentName ) {
        departmentExistCheck(departmentName);
        return repository.findLectorCountByDepartmentName(departmentName).get();
    }

    private Department departmentExistCheck(String departmentName) {
        return repository.findByName(departmentName).orElseThrow(
                () -> new DepartmentNotFoundException("Department " + departmentName + " is not found" ) );
    }

}
