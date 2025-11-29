package com.flightapp.flightservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("airline")
public class Airline {

    @Id
    private Long id;

    private String name;
    private String logo;
}
