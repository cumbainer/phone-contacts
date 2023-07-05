package ua.shtaiier.phonecontacts.mapper;

import org.mapstruct.Mapper;
import ua.shtaiier.phonecontacts.common.BaseMapper;
import ua.shtaiier.phonecontacts.domain.Image;
import ua.shtaiier.phonecontacts.dto.ImageDto;

@Mapper(componentModel = "spring")
public interface ImageMapper extends BaseMapper<Image, ImageDto> {
}
