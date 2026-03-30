package dev.ztok.back.videos;

import dev.ztok.back.db.entities.Video;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {
    
    private final VideoRepository videoRepository;
    
    @Override
    @Transactional
    public VideoDto createVideo(CreateVideoRequest request) {
        // Проверка уникальности URL
        if (videoRepository.existsByUrl(request.getUrl())) {
            throw new RuntimeException("Video URL already exists: " + request.getUrl());
        }
        
        Video video = new Video();
        video.setTitle(request.getTitle());
        video.setUrl(request.getUrl());
        video.setDuration(request.getDuration());
        video.setDescription(request.getDescription());
        video.setIsPrivate(request.getIsPrivate());
        video.setViewCount(0L);
        
        Video savedVideo = videoRepository.save(video);
        return mapToDto(savedVideo);
    }
    
    @Override
    public VideoDto getVideoById(Long id) {
        Video video = videoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Video not found with id: " + id));
        return mapToDto(video);
    }
    
    @Override
    public VideoDto getVideoByUrl(String url) {
        Video video = videoRepository.findByUrl(url)
            .orElseThrow(() -> new RuntimeException("Video not found with url: " + url));
        return mapToDto(video);
    }
    
    @Override
    public List<VideoDto> getAllVideos() {
        return videoRepository.findAll().stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<VideoDto> getPublicVideos() {
        return videoRepository.findByIsPrivateFalse().stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<VideoDto> searchVideosByTitle(String title) {
        return videoRepository.findByTitleContainingIgnoreCase(title).stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public VideoDto updateVideo(Long id, UpdateVideoRequest request) {
        Video video = videoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Video not found with id: " + id));
        
        // Проверка уникальности URL при изменении
        if (request.getUrl() != null && !request.getUrl().equals(video.getUrl())) {
            if (videoRepository.existsByUrl(request.getUrl())) {
                throw new RuntimeException("Video URL already exists: " + request.getUrl());
            }
            video.setUrl(request.getUrl());
        }
        
        if (request.getTitle() != null) {
            video.setTitle(request.getTitle());
        }
        
        if (request.getDuration() != null) {
            video.setDuration(request.getDuration());
        }
        
        if (request.getDescription() != null) {
            video.setDescription(request.getDescription());
        }
        
        if (request.getIsPrivate() != null) {
            video.setIsPrivate(request.getIsPrivate());
        }
        
        Video updatedVideo = videoRepository.save(video);
        return mapToDto(updatedVideo);
    }
    
    @Override
    @Transactional
    public void deleteVideo(Long id) {
        if (!videoRepository.existsById(id)) {
            throw new RuntimeException("Video not found with id: " + id);
        }
        videoRepository.deleteById(id);
    }
    
    @Override
    @Transactional
    public VideoDto incrementViewCount(Long id) {
        Video video = videoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Video not found with id: " + id));
        
        videoRepository.incrementViewCount(id);
        
        // Обновляем объект для возврата актуальных данных
        video.setViewCount(video.getViewCount() + 1);
        return mapToDto(video);
    }
    
    @Override
    public boolean existsByUrl(String url) {
        return videoRepository.existsByUrl(url);
    }
    
    private VideoDto mapToDto(Video video) {
        return new VideoDto(
            video.getId(),
            video.getTitle(),
            video.getUrl(),
            video.getDuration(),
            video.getViewCount(),
            video.getDescription(),
            video.getIsPrivate(),
            video.getCreatedAt(),
            video.getUpdatedAt(),
            video.getVersion()
        );
    }
}