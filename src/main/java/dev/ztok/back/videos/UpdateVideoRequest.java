package dev.ztok.back.videos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateVideoRequest {
    
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
    private String title;
    
    private String url;
    
    @Min(value = 1, message = "Duration must be at least 1 second")
    private Integer duration;
    
    private String description;
    
    private Boolean isPrivate;
}