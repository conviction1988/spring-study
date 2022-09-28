package com.spring.study.global.common.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@EntityListeners({AuditingEntityListener.class})
public class DateEntity extends BaseEntity implements Serializable {

    @CreatedDate
    @Column(name = "CREATED_AT", columnDefinition = "datetime", nullable = false, updatable = false)
    private LocalDateTime created;

    @LastModifiedDate
    @Column(name = "UPDATED_AT", columnDefinition = "datetime", nullable = false)
    private LocalDateTime updated;

}
