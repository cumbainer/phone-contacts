package ua.shtaiier.phonecontacts.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.shtaiier.phonecontacts.domain.Image;
import ua.shtaiier.phonecontacts.dto.ImageDto;
import ua.shtaiier.phonecontacts.mapper.ImageMapper;
import ua.shtaiier.phonecontacts.repository.ImageRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {

    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;

    public ImageDto getById(int id){
        Image contact = imageRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Image with id: " + id + " not found"));

        return imageMapper.toDto(contact);

    }

}
