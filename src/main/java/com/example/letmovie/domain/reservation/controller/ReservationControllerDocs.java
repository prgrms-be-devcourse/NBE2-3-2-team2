package com.example.letmovie.domain.reservation.controller;

import com.example.letmovie.domain.reservation.dto.request.DateRequestDTO;
import com.example.letmovie.domain.reservation.dto.request.ReserveSeatsRequestDTO;
import com.example.letmovie.domain.reservation.dto.request.ShowTimeRequestDTO;
import com.example.letmovie.domain.reservation.dto.request.TheaterRequestDTO;
import com.example.letmovie.domain.reservation.dto.response.MovieNamesResponseDTO;
import com.example.letmovie.domain.reservation.dto.response.ReservationResponseDTO;
import com.example.letmovie.domain.reservation.dto.response.ShowTimeResponseDTO;
import com.example.letmovie.domain.reservation.dto.response.TheaterResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "예약 API")
public interface ReservationControllerDocs {

    @ResponseBody
    @PostMapping("/api/dates")
    @Operation(summary = "날짜 선택 시 영화 리스트 찾기.", description = "필요 파라미터 : 날짜(String) ex)2024-12-18")
    MovieNamesResponseDTO selectDate(@RequestBody DateRequestDTO selectDateDTO);


    @ResponseBody
    @PostMapping("/api/movies")
    @Operation(summary = "날짜,영화 선택 시 극장 리스트 찾기.", description = "필요 파라미터 : 날짜(String) ex)2024-12-18, 영화이름(String)")
    List<TheaterResponseDTO> selectTheater(@RequestBody TheaterRequestDTO theaterRequestDTO);


    @ResponseBody
    @PostMapping("/api/showtimes")
    @Operation(summary = "날짜,영화,극장 선택 시 ShowTime 리스트 찾기",
            description = "필요 파라미터 : 날짜(String) ex)2024-12-18, 영화이름(String), 극장이름(String)")
    List<ShowTimeResponseDTO> selectShowTimes(@RequestBody ShowTimeRequestDTO showTimeRequestDTO);


    @Operation(summary = "좌석 선택 페이지", description = "필요 파라미터 : showtimeId(Long)")
    public String seatSelection(@RequestParam("showtimeId") Long showtimeId, Model model);


    @ResponseBody
    @PostMapping("/reserve-seats")
    @Operation(summary = "좌석 선택 후 요청", description = "필요 파라미터 : seats(list) ex)'1-1','1-2' , showtimeId(Long) ")
    ResponseEntity<ReservationResponseDTO> reserveSeats(@RequestBody ReserveSeatsRequestDTO requestDTO) throws InterruptedException;

    }