package com.example.letmovie.domain.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SeatDTO {
    private Long id;
    private String seatLow;
    private int seatCol;

    public SeatDTO(Long id, int seatRow, int seatCol) {
        this.id = id;
        this.seatLow = convertSeatRowToLetter(seatRow);
        this.seatCol = seatCol;
    }

    private String convertSeatRowToLetter(int seatRow) {
        if (seatRow < 1 || seatRow > 26) {  // 최대 26행(A~Z)까지만 허용
            throw new IllegalArgumentException("좌석 행 번호는 1 ~ 26 사이의 값");
        }
        return String.valueOf((char) ('A' + seatRow - 1));
    }
}
