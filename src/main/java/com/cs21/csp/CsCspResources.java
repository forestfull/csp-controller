package com.cs21.csp;

import lombok.*;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CS_CSP_RESOURCES", uniqueConstraints = {@UniqueConstraint(name = "target_resource_policy", columnNames = {"target", "resource_url"})})
public class CsCspResources {

    @Id
    private Long id;
    @Column(name = "target", length = 255, nullable = false)
    private String target;
    @Column(name = "resource_url", length = 255, nullable = false)
    private String resourceUrl;

    public static CsCspResources of(Map<String, String> map) {
        return CsCspResources.builder().target(map.get("target")).resourceUrl(Objects.isNull(map.get("resource_url")) ? map.get("resourceUrl") : map.get("resource_url")).build();
    }

    public Map<String, String> convertMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("target", this.getTarget());
        map.put("resource_url", this.getResourceUrl());
        return map;
    }
}