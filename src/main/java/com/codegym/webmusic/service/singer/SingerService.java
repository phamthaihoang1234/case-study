package com.codegym.webmusic.service.singer;


import com.codegym.webmusic.model.Singer;
import com.codegym.webmusic.service.IService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SingerService extends IService<Singer> {
    Page<Singer> findAllByNameContains(String name, Pageable pageable);

    Page<Singer> findAll(Pageable pageable);
}
