package ua.shtaiier.phonecontacts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {

    private Integer id;
    private String originalFileName;
    private Long size;
    private String contentType;
    private byte[] bytes;

}
