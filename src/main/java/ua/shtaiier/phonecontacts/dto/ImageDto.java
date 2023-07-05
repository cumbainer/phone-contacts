package ua.shtaiier.phonecontacts.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageDto {

    private Integer id;
    private String originalFileName;
    private Long size;
    private String contentType;
    private byte[] bytes;

}
