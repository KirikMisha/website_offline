package com.example.test32.repo;

import com.example.test32.models.Announcement;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    List<Announcement> findAllByCreatedAtBefore(Date date);
    List<Announcement> findAll(Sort sort);
}
