package com.example.university_manager.service;


import com.example.university_manager.entity.Department;
import com.example.university_manager.entity.Lector;
import com.example.university_manager.entity.Rank;
import com.example.university_manager.exception.DepartmentIsEmptyException;
import com.example.university_manager.exception.DepartmentNotFoundException;
import com.example.university_manager.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class DepartmentServiceMockTest {
    // unit testing without spring context
    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    private Department department;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Lector head = new Lector();
        head.setFirstName("John");
        head.setSecondName("D.");
        head.setLastName("Doe");
        head.setDegree(Rank.PROFESSOR);
        head.setSalary(BigDecimal.valueOf(20000));

        department = new Department();
        department.setName("Computer Science");
        department.setDepartmentHead(head);
    }

    @Test
    void departmentIsNotExists() {
        when(departmentRepository.findByName(any())).thenReturn(Optional.empty());
        DepartmentNotFoundException exception = assertThrows(DepartmentNotFoundException.class,
                () -> departmentService.getHeadOfDepartmentName("unexisting depName"));
        assertEquals("Department unexisting depName is not found", exception.getMessage());
    }

    @Test
    void departmentDoesNotHaveHead() {
        department.setDepartmentHead(null);
        when(departmentRepository.findByName(any())).thenReturn(Optional.of(department));

        DepartmentIsEmptyException exception = assertThrows(DepartmentIsEmptyException.class,
                () -> departmentService.getHeadOfDepartmentName("Computer Science"));
        assertEquals("Computer Science department does not have any head", exception.getMessage());
    }

    @Test
    void headFullNameTest() {
        Lector head = new Lector();
        head.setFirstName("Andriy");
        head.setLastName("Shchuk");
        head.setSecondName("Ivanovich");
        department.setDepartmentHead(head);

        when(departmentRepository.findByName("Computer Science")).thenReturn(Optional.of(department));

        String headName = departmentService.getHeadOfDepartmentName("Computer Science");
        assertEquals("Andriy Ivanovich Shchuk", headName);
    }

    @Test
    void headWithoutSecondName() {
        Lector head = new Lector();
        head.setFirstName("Andriy");
        head.setLastName("Shchuk");
        department.setDepartmentHead(head);

        when(departmentRepository.findByName("Computer Science")).thenReturn(Optional.of(department));

        String headName = departmentService.getHeadOfDepartmentName("Computer Science");
        assertEquals("Andriy Shchuk", headName);
    }

    @Test
    void getStatisticFromEmptyDep() {
        when(departmentRepository.findByName("Computer Science")).thenReturn(Optional.of(department));
        when(departmentRepository.findDegreeCountsByDepartment(any())).thenReturn(Optional.empty());

        DepartmentIsEmptyException exception = assertThrows(DepartmentIsEmptyException.class,
                () -> departmentService.getStatistic("Computer Science"));

        assertEquals("Computer Science department does not have any member", exception.getMessage());
    }

    @Test
    void statisticTest() {
        List<Object[]> statistic = Arrays.asList(
                new Object[]{"ASSISTANT", 3}, new Object[]{"ASSOCIATE", 2}, new Object[]{"PROFESSOR", 1}
        );

        when(departmentRepository.findByName("Computer Science")).thenReturn(Optional.of(department));
        when(departmentRepository.findDegreeCountsByDepartment(any())).thenReturn(Optional.of(statistic));

        Map<Rank, Integer> resultMap = departmentService.getStatistic("Computer Science");
        //using Map.of since java 9
        Map<Rank, Integer> expectedMap = Map.of(
                Rank.ASSISTANT, 3,
                Rank.ASSOCIATE, 2,
                Rank.PROFESSOR, 1
        );

        assertEquals(expectedMap, resultMap);
    }

    @Test
    void partStatisticTest() {
        List<Object[]> statistic = Arrays.asList(
               new Object[]{"ASSOCIATE", 2}, new Object[]{"PROFESSOR", 1}
        );

        when(departmentRepository.findByName("Computer Science")).thenReturn(Optional.of(department));
        when(departmentRepository.findDegreeCountsByDepartment(any())).thenReturn(Optional.of(statistic));

        Map<Rank, Integer> resultMap = departmentService.getStatistic("Computer Science");
        //using Map.of since java 9
        Map<Rank, Integer> expectedMap = Map.of(
                Rank.ASSISTANT, 0,
                Rank.ASSOCIATE, 2,
                Rank.PROFESSOR, 1
        );

        assertEquals(expectedMap, resultMap);
    }

}
