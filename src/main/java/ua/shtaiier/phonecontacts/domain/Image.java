package ua.shtaiier.phonecontacts.domain;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String originalFileName;
    private Long size;
    private String contentType;
    @Lob
    private byte[] bytes;
}
