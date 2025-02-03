package com.example.letmovie.domain.admin.service;

import com.example.letmovie.domain.admin.repository.AdminScreenRepository;
import com.example.letmovie.domain.admin.repository.AdminTheaterRepository;
import com.example.letmovie.domain.movie.entity.Theater;
import com.example.letmovie.domain.reservation.dto.request.ScreenDTO;
import com.example.letmovie.domain.reservation.entity.Screen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminScreenServiceImpl {
    @Autowired
    private AdminTheaterRepository adminTheaterRepository;

    @Autowired
    private AdminScreenRepository adminScreenRepository;

    // 상영관 목록조회(영화관별 정렬)
    public List<Screen> findAllScreensSorted(){
        return adminScreenRepository.findAllByOrderByTheaterIdAscScreenNameAsc();
    }

    // 극장 목록 조회
    public List<Theater> findAllTheaters() {
        return adminTheaterRepository.findAll();
    }

    // ID조회
    public Screen findScreenById(Long screenId) {
        return adminScreenRepository.findById(screenId)
                .orElseThrow(() -> new IllegalArgumentException("상영관을 찾을 수 없습니다. ID: " + screenId));
    }

    // 상영관 추가
    public void addScreen(ScreenDTO screenDTO) {
        Theater theater = adminTheaterRepository.findById(screenDTO.getTheaterId())
                .orElseThrow(() -> new IllegalArgumentException("영화관을 찾을 수 없습니다. ID: " + screenDTO.getTheaterId()));

        Screen screen = Screen.builder()
                .screenName(screenDTO.getScreenName())
                .theater(theater)
                .build();

        adminScreenRepository.save(screen);
    }

    // 상영관 수정
    public void updateScreen(ScreenDTO screenDTO) {
        if (screenDTO.getId() == null) {
            throw new IllegalArgumentException("Screen ID는 필수입니다.");
        }

        Screen existingScreen = adminScreenRepository.findById(screenDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("상영관을 찾을 수 없습니다. ID: " + screenDTO.getId()));

        existingScreen.setScreenName(screenDTO.getScreenName());

        adminScreenRepository.save(existingScreen);
    }

    // 상영관 삭제
    public void deleteScreen(Long screenId) {
        if (!adminScreenRepository.existsById(screenId)) {
            throw new IllegalArgumentException("상영관을 찾을 수 없습니다. ID: " + screenId);
        }
        adminScreenRepository.deleteById(screenId);
    }
}
