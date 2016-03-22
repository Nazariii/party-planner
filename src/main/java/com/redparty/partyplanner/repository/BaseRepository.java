package com.redparty.partyplanner.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends Repository<T, ID> {

    <S extends T> Optional<S> save(S entity);

    Optional<T> findOne(ID id);

    boolean exists(ID id);

    void delete(ID id);

    List<T> findAll();
}
