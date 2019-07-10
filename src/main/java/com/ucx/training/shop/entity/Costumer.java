package com.ucx.training.shop.entity;

import com.ucx.training.shop.type.RecordStatus;
import lombok.*;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Costumer extends BaseModel<Integer> {
    private String name;

    public Costumer(Integer id, String name, RecordStatus recordStatus) {
        super(id, recordStatus);
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("%d, %s", 1, this.getName());
    }
}
