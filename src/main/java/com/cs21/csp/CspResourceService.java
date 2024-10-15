package com.cs21.csp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class CspResourceService {

    private final CspResourceRepository repository;

    public List<CsCspResources> getAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public void save(String target, String resource) {
        repository.save(CsCspResources.builder().target(target).resourceUrl(resource).build());
    }

    public void save(CsCspResources resource) {
        repository.save(resource);
    }

    public void saveAll(List<Map<String, String>> resources) {
        repository.saveAll(resources.stream().map(CsCspResources::of).collect(Collectors.toList()));
    }

    public void saveAll(Iterable<CsCspResources> resources) {
        repository.saveAll(resources);
    }

    public void update(Long id, String target, String resource) {
        repository.findById(id).ifPresent(res -> {
            res.setTarget(target);
            res.setResourceUrl(resource);
        });
    }

    public void update(CsCspResources resource) {
        repository.findById(resource.getId()).ifPresent(res -> {
            res.setTarget(resource.getTarget());
            res.setResourceUrl(resource.getResourceUrl());
        });
    }
    public void delete(Long id) {
        repository.deleteById(id);
    }
}