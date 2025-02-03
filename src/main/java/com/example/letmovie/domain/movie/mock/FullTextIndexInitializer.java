package com.example.letmovie.domain.movie.mock;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class FullTextIndexInitializer implements CommandLineRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        /*
        JPA @Index 어노테이션은 일반 B-Tree 인덱스만 생성

        풀텍스트 인덱스는 수동 생성 필수
        */

        // ALTER TABLE 쿼리 실행 -> full text 인덱스 강제로 넣어줌.
        entityManager.createNativeQuery("ALTER TABLE movie ADD FULLTEXT INDEX idx_movie_name_fulltext (movie_name)").executeUpdate();
    }
}
