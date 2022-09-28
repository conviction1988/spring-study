package com.spring.study.global.common.entity;

import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@MappedSuperclass
public class BaseEntity implements Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
