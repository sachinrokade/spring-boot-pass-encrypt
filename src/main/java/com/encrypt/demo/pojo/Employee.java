package com.encrypt.demo.pojo;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {

        @Id
        @NonNull
        int id;

        @NotEmpty(message = "model' field was empty")
        String name;

        @NotEmpty(message = "model' field was empty")
        String dept;

        @NonNull
        @DecimalMin(value = "1000", message = "salery should be getter then 1000")
        int salery;
}
