package com.ucx.training.shop.entity;

import com.ucx.training.shop.type.RecordStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class BaseEntity<T> {
    @Id //Indicates that this field is a Primary Key.
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Id generation strategy
    private T id;
    @Enumerated(EnumType.STRING)
    private RecordStatus recordStatus = RecordStatus.ACTIVE;
    @CreationTimestamp
    private LocalDateTime createDateTime;
    @UpdateTimestamp
    private LocalDateTime updateDateTime;

    private LocalDateTime deletedDateTime;

    @Column(length = 1000)
    private String description;

    @Version
    private Long version;

}
