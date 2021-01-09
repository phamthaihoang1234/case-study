package com.codegym.webmusic.service.singer;


import com.codegym.webmusic.model.Singer;
import com.codegym.webmusic.repositories.SingerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class SingerServiceImpl implements SingerService {
    @Autowired
    SingerRepository singerRepository;

    @Override
    public Iterable<Singer> findAll() {
        return singerRepository.findAll();
    }

    @Override
    public Optional<Singer> findById(Long id) {
        return singerRepository.findById(id);
    }

    @Override
    public Singer save(Singer product) {
        return singerRepository.save(product);
    }

    @Override
    public Singer remove(Long id) {
        Singer singer = singerRepository.findById(id).get();
        if (singer != null) singerRepository.deleteById(id);
        return singer;
    }

    @Override
    public Page<Singer> findAllByNameContaining(String name, Pageable pageable) {
        return singerRepository.findAllByNameContaining(name,pageable);
    }
}
