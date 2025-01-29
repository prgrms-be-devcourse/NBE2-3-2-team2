package com.example.letmovie.domain.admin.controller;

import com.example.letmovie.domain.admin.service.AdminMovieServiceImpl;
import com.example.letmovie.domain.movie.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminMovieController {
    @Autowired
    private AdminMovieServiceImpl adminService;
    //private AdminServiceImpl adminService;


    // /admin/movie : 영화목록
    /*@GetMapping("/movie")
    public String movie(Model model) {
        List<Movie> movies = adminService.findAllMovies();
        //for(Movie movie : movies) {
        //    System.out.println(movie);
        //}
        model.addAttribute("movies", movies);
        return "admin_movie";
    }*/
    @GetMapping("/movie")
    public String movie(
            @RequestParam(defaultValue = "1") int page, // 현재 페이지 (0부터 시작)
            @RequestParam(defaultValue = "10") int size, // 페이지 크기
            Model model) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<Movie> movies = adminService.findAllMovieswithPage(pageable);

        model.addAttribute("movies", movies.getContent()); // 현재 페이지의 영화 목록
        model.addAttribute("currentPage", page); // 현재 페이지 번호
        model.addAttribute("totalPages", movies.getTotalPages()); // 총 페이지 수

        return "admin_movie";
    }

    // /admin/movie/post : 영화 추가 첫화면
    @GetMapping("/movie/post")
    public String moviePost(Model model) {
        return "admin_movie_post";
    }

    // /admin/movie/post/search : 영화 추가 검색 이후 화면
    @GetMapping("/movie/post/search")
    public String moviePostSearch(@RequestParam("putmovieNm") String movieNm, Model model) {

        try {
            String movieCd = adminService.getMovieCodeByName(movieNm); // 영화 코드 가져오기
            if (movieCd != null) {
                model.addAttribute("movieDetails", adminService.getMovieInfoByCode(movieCd)); // 상세 정보 가져오기
            } else {
                model.addAttribute("movieDetails", null); // 검색 결과가 없는 경우
                model.addAttribute("error", "검색 결과가 없습니다. 영화 제목을 정확히 기입해주세요.");
            }
        } catch (Exception e) {
            model.addAttribute("movieDetails", null); // 오류 시 기본값 처리
            model.addAttribute("error", "오류가 발생했습니다. 다시 시도해주세요.");
        }


        return "admin_movie_post_search";
    }

    // /admin/movie/post/add : 영화 추가 처리용
    @PostMapping("/movie/post/add")
    public String addMovie(@ModelAttribute Movie movie, Model model) {
        try {
            adminService.addMovie(movie); // 서비스 계층에서 데이터 저장 처리
            model.addAttribute("success", "영화가 성공적으로 추가되었습니다!");
        } catch (Exception e) {
            model.addAttribute("error", "영화 추가 중 문제가 발생했습니다: " + e.getMessage());
        }
        return "redirect:/admin/movie"; // 영화 목록으로 리다이렉트
    }

    // /admin/movie/modify : 영화 수정 첫화면
    @GetMapping("/movie/modify")
    public String modifyMovie(Model model) {
        return "admin_movie_modify";
    }

    // /admin/movie/modify/idsearch : 영화 수정 첫화면
    @GetMapping("/movie/modify/idsearch")
    public String modifyMovieIdSearch(@RequestParam("movieNm") String movieNm, Model model) {
        try {
            List<Movie> movies = adminService.findMovieByName(movieNm);
            if (movies != null) {
                model.addAttribute("movies", movies);
            } else {
                model.addAttribute("error", "해당 이름에 해당하는 영화가 없습니다.");
            }
        } catch (Exception e) {
            model.addAttribute("error", "오류가 발생했습니다. 다시 시도해주세요.");
        }
        return "admin_movie_modify_idsearch";
    }

    // /admin/movie/modify/search : 영화 수정 검색 이후 화면
    @GetMapping("/movie/modify/search")
    public String modifyMovieSearch(@RequestParam("movieId") Long movieId, Model model) {
        try {
            Movie movie = adminService.findMovieById(movieId);
            if (movie != null) {
                model.addAttribute("movie", movie);
            } else {
                model.addAttribute("error", "해당 ID에 해당하는 영화가 없습니다.");
            }
        } catch (Exception e) {
            model.addAttribute("error", "오류가 발생했습니다. 다시 시도해주세요.");
        }
        return "admin_movie_modify_search";
    }

    // /admin/movie/modify/ok : 영화 수정 처리용
    @PostMapping("/movie/modify/ok")
    public String modifyMovieOk(@ModelAttribute Movie movie, RedirectAttributes redirectAttributes) {
        try {
            adminService.updateMovie(movie);
            redirectAttributes.addFlashAttribute("success", "영화 정보가 성공적으로 수정되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "오류가 발생했습니다. 수정에 실패했습니다.");
        }
        return "redirect:/admin/movie"; // 영화 목록으로 리다이렉트
    }

    // /admin/movie/delete : 영화 삭제 첫화면
    @GetMapping("/movie/delete")
    public String deleteMovie(Model model) {
        return "admin_movie_delete";
    }

    @GetMapping("/movie/delete/search")
    public String deleteMovieSearch(@RequestParam("movieId") Long movieId, Model model, RedirectAttributes redirectAttributes) {
        Movie movie = adminService.findMovieById(movieId);
        if (movie != null) {
            model.addAttribute("movie", movie);
            return "admin_movie_delete_search";
        } else {
            redirectAttributes.addFlashAttribute("error", "해당 ID에 해당하는 영화가 없습니다.");
            return "redirect:/admin/movie/delete";
        }
    }

    @PostMapping("/movie/delete/ok")
    public String deleteMovieOk(@RequestParam("movieId") Long movieId, RedirectAttributes redirectAttributes) {
        try {
            adminService.deleteMovieById(movieId);
            redirectAttributes.addFlashAttribute("success", "영화가 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "영화 삭제 중 오류가 발생했습니다.");
        }
        return "redirect:/admin/movie"; // 영화 목록으로 리다이렉트
    }
}
