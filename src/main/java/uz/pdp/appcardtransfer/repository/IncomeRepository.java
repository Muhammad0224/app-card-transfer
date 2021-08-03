package uz.pdp.appcardtransfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appcardtransfer.entity.Card;
import uz.pdp.appcardtransfer.entity.Income;

import java.util.List;
import java.util.Optional;

public interface IncomeRepository extends JpaRepository<Income, Integer> {
    List<Income> findAllByToCardId(Integer toCard_id);
}
