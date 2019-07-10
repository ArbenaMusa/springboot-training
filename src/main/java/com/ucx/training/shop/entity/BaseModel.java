package com.ucx.training.shop.entity;

import com.ucx.training.shop.type.RecordStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Base Entity from which all other Entities inherit.
 * @param <T>
 */
@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseModel<T> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private T id;
    @Enumerated(EnumType.STRING)
    private RecordStatus recordStatus = RecordStatus.ACTIVE;
}
