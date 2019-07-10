package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.BaseModel;
import com.ucx.training.shop.repository.BaseRepository;
import com.ucx.training.shop.type.RecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BaseService<T extends BaseModel<U>, U> {

    @Autowired
    private BaseRepository<T, U> baseRepository;

    public T save(T t){
        if (t == null) {
            throw new IllegalArgumentException("Invalid argument: "+t);
        }
        return baseRepository.save(t);
    }

    public List<T> findAll() {
        return baseRepository.findAll();
    }

    public T update(T t) {
        if (t == null) {
            throw new IllegalArgumentException(String.format("Invalid argument: %s", t));
        }
        T foundT = findById(t.getId());
        if (foundT == null) {
            throw new RuntimeException("Entity not found");
        }
        return foundT;
    }

    public void remove(U id) {
        if (id == null) {
            throw new IllegalArgumentException("Invalid argument" + id);
        }
        T t = findById(id);
        if (t == null) {
            throw new RuntimeException("Entity not found: "+t);
        }

        t.setRecordStatus(RecordStatus.INACTIVE);
    }

    public T findById(U id){
        if (id == null) {
            throw new IllegalArgumentException("Invalid argument: "+id);
        }
        Optional<T> optionalT = baseRepository.findById(id);
        return optionalT.orElse(null);

        }
    }
