package ua.shtaiier.phonecontacts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.shtaiier.phonecontacts.domain.Image;

public interface ImageRepository extends JpaRepository<Image, Integer> {

}
