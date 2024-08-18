package com.pandi.projects.customer_service.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("customer")
@Data
@AllArgsConstructor
public class Customer {

    @Id
    private Integer id;
    private String name;
    private Integer balance;

}
