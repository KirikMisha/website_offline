package com.example.test32.repository;

import com.example.test32.models.News;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    News findTopByOrderByCreatedAtDesc();
    List<News> findAllByOrderByCreatedAtDesc();
    List<News> findAllByCreatedAtBefore(Date date);
    List<News> findAll(Sort sort);
}
