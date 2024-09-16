package com.example.university_manager.service;

import com.example.university_manager.entity.Lector;
import com.example.university_manager.exception.LectorNotFoundException;
import com.example.university_manager.repository.LectorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LectorService {

    private LectorRepository repository;

    public LectorService(LectorRepository repository) {
        this.repository = repository;
    }

    public String findLectorsByNamePart(String namePart) {
        List<Lector> lectors = repository.findLectorsByNamePart(namePart);

        if (!lectors.isEmpty()) {
            return lectors.stream()
                    .map(lector -> {
                        String fullName = lector.getFirstName() + " " +
                                (lector.getSecondName() != null ? lector.getSecondName() + " " : "") +
                                lector.getLastName();
                        return fullName;
                    })
                    .collect(Collectors.joining(", "));
        }
        throw new LectorNotFoundException("Lector with name part: " + namePart + " is not found");
    }
}
