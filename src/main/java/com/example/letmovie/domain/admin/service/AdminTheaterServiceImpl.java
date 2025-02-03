package com.example.letmovie.domain.admin.service;

import com.example.letmovie.domain.admin.repository.AdminTheaterRepository;
import com.example.letmovie.domain.movie.dto.TheaterDTO;
import com.example.letmovie.domain.movie.entity.Theater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminTheaterServiceImpl {
    @Autowired
    private AdminTheaterRepository adminTheaterRepository;

    // 영화관 목록 조회
    public List<Theater> findAllTheaters() {
        return adminTheaterRepository.findAll();
    }

    // ID로 특정 극장 조회
    public Theater findTheaterById(Long id) {
        return adminTheaterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("극장을 찾을 수 없습니다. ID: " + id));
    }

    // 극장 추가
    public void addTheater(Theater theater) {
        adminTheaterRepository.save(theater);
    }

    // 극장 수정
    public void updateTheater(TheaterDTO theaterDto) {
        Theater existingTheater = adminTheaterRepository.findById(theaterDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("극장을 찾을 수 없습니다. ID: " + theaterDto.getId()));
        existingTheater.setTheaterName(theaterDto.getTheaterName());
        adminTheaterRepository.save(existingTheater);
    }

    // 극장 삭제
    public void deleteTheaterById(Long id) {
        adminTheaterRepository.deleteById(id);
    }
}
