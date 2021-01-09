package com.codegym.webmusic.service;


import com.codegym.webmusic.model.Singer;

import java.util.Optional;

public interface IService<E> {
    Iterable<E> findAll();
//    E findById(Long id);
    Optional<Singer> findById(Long id);
    E save(E e);
    E remove(Long id);
}
