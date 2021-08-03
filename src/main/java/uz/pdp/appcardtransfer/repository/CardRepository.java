package uz.pdp.appcardtransfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appcardtransfer.entity.Card;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Integer> {
    List<Card> findAllByUsername(String username);
    Optional<Card> findByUsernameAndId(String username, Integer id);
    Optional<Card> findByUsernameAndIdAndActiveTrue(String username, Integer id);
    Optional<Card> findByIdAndActiveTrue(Integer id);

}
