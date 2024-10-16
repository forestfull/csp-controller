package com.cs21.csp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CspController {

    private final String indexContents;

    public CspController() {
        final StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
            ClassPathResource classPathResource = new ClassPathResource("index.html");
            reader = new BufferedReader(new InputStreamReader(classPathResource.getInputStream()));
            reader.lines().forEach(sb::append);

        } catch (IOException ignored) {
        } finally {
            try {
                reader.close();
            } catch (Exception ignored) {
            }
        }

        this.indexContents = sb.toString();
    }

    @GetMapping("/_Admin/csp-json")
    String goCspJson() {
        return indexContents;
    }

    @GetMapping("/_Admin/csp-json/list")
    String getCspJson() throws JsonProcessingException {
        final List<CsCspResources> resources = CspResponseFilter.getService().getAll();
        if (resources == null) {
            return "";
        } else {
            return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(resources.stream().map(CsCspResources::convertMap).collect(Collectors.groupingBy(map -> map.get("target"))));
        }
    }

    @PostMapping("/_Admin/csp-json")
    String registerCspList(@RequestBody String resourcesString) {
        System.out.println(resourcesString);
        return resourcesString;
    }
}