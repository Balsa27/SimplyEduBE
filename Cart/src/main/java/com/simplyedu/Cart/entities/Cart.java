package com.simplyedu.Cart.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private Long userId;

    @Column
    @ElementCollection(targetClass=Long.class) //for jpa to know that this needs to be put into an intermidate table
    private Set<Long> courseIds;    
}
