package com.example.test32.repository;

import com.example.test32.models.Quotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends JpaRepository<Quotes, Long> {
    // Здесь вы можете добавить дополнительные методы для запросов к таблице цитат
}
