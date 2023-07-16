package nl.han.oose.clipper.clipperapi.domain.diet.data;

import nl.han.oose.clipper.clipperapi.domain.diet.application.dto.UserCustomDiets;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCustomDietsRepository extends JpaRepository<UserCustomDiets, Long> {
}
