package dev.ztok.back.videos;

import dev.ztok.back.db.entities.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    
    Optional<Video> findByUrl(String url);
    
    List<Video> findByTitleContainingIgnoreCase(String title);
    
    List<Video> findByIsPrivateFalse();
    
    List<Video> findByIsPrivate(Boolean isPrivate);
    
    boolean existsByUrl(String url);
    
    @Modifying
    @Transactional
    @Query("UPDATE Video v SET v.viewCount = v.viewCount + 1 WHERE v.id = :videoId")
    void incrementViewCount(@Param("videoId") Long videoId);
}