package com.cs21.csp;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = {@UniqueConstraint(name = "target_resource_policy", columnNames = {"target", "resource_name"})})
public class CS_CSP_RESOURCES {

    @Id
    private Long id;
    @Column(name = "target", length = 255, nullable = false)
    private String target;
    @Column(name = "resource_name", length = 255, nullable = false)
    private String resourceName;
}