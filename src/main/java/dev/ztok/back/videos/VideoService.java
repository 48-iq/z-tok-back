package dev.ztok.back.videos;

import java.util.List;

public interface VideoService {
    VideoDto createVideo(CreateVideoRequest request);
    VideoDto getVideoById(Long id);
    VideoDto getVideoByUrl(String url);
    List<VideoDto> getAllVideos();
    List<VideoDto> getPublicVideos();
    List<VideoDto> searchVideosByTitle(String title);
    VideoDto updateVideo(Long id, UpdateVideoRequest request);
    void deleteVideo(Long id);
    VideoDto incrementViewCount(Long id);
    boolean existsByUrl(String url);
}