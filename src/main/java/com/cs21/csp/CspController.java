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
            final CsCspResources.Json json = new CsCspResources.Json();
            resources.stream()
                    .map(CsCspResources::convertMap)
                    .collect(Collectors.groupingBy(map -> map.get("target")))
                    .forEach((key, value) -> json.put(key, value.stream().map(map -> map.get("url")).collect(Collectors.toList())));

            return new ObjectMapper()
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(json);
        }
    }

    @PostMapping("/_Admin/csp-json")
    void registerCspList(@RequestBody String resourcesString) throws JsonProcessingException {
        final CsCspResources.Json map = new ObjectMapper().readValue(resourcesString, CsCspResources.Json.class);
        List<CsCspResources> cspResourcesList = map.entrySet().stream().flatMap(entry -> entry.getValue().stream().map(url -> CsCspResources.builder().target(entry.getKey()).resourceUrl(url).build())).collect(Collectors.toList());
        CspResponseFilter.getService().register(cspResourcesList);
    }
}