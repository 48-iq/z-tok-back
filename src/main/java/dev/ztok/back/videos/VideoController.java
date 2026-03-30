package dev.ztok.back.videos;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {
    
    private final VideoService videoService;
    
    @PostMapping
    public ResponseEntity<VideoDto> createVideo(@Valid @RequestBody CreateVideoRequest request) {
        VideoDto createdVideo = videoService.createVideo(request);
        return new ResponseEntity<>(createdVideo, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<VideoDto> getVideoById(@PathVariable Long id) {
        VideoDto video = videoService.getVideoById(id);
        return ResponseEntity.ok(video);
    }
    
    @GetMapping("/url")
    public ResponseEntity<VideoDto> getVideoByUrl(@RequestParam String url) {
        VideoDto video = videoService.getVideoByUrl(url);
        return ResponseEntity.ok(video);
    }
    
    @GetMapping
    public ResponseEntity<List<VideoDto>> getAllVideos() {
        List<VideoDto> videos = videoService.getAllVideos();
        return ResponseEntity.ok(videos);
    }
    
    @GetMapping("/public")
    public ResponseEntity<List<VideoDto>> getPublicVideos() {
        List<VideoDto> videos = videoService.getPublicVideos();
        return ResponseEntity.ok(videos);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<VideoDto>> searchVideos(@RequestParam String title) {
        List<VideoDto> videos = videoService.searchVideosByTitle(title);
        return ResponseEntity.ok(videos);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<VideoDto> updateVideo(
            @PathVariable Long id,
            @Valid @RequestBody UpdateVideoRequest request) {
        VideoDto updatedVideo = videoService.updateVideo(id, request);
        return ResponseEntity.ok(updatedVideo);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideo(@PathVariable Long id) {
        videoService.deleteVideo(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{id}/view")
    public ResponseEntity<VideoDto> incrementViewCount(@PathVariable Long id) {
        VideoDto video = videoService.incrementViewCount(id);
        return ResponseEntity.ok(video);
    }
    
    @GetMapping("/exists")
    public ResponseEntity<Boolean> existsByUrl(@RequestParam String url) {
        return ResponseEntity.ok(videoService.existsByUrl(url));
    }
}