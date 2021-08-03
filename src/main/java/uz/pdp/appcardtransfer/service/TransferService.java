package uz.pdp.appcardtransfer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.appcardtransfer.entity.Card;
import uz.pdp.appcardtransfer.entity.Income;
import uz.pdp.appcardtransfer.entity.Outcome;
import uz.pdp.appcardtransfer.model.TransferDto;
import uz.pdp.appcardtransfer.repository.CardRepository;
import uz.pdp.appcardtransfer.repository.IncomeRepository;
import uz.pdp.appcardtransfer.repository.OutcomeRepository;
import uz.pdp.appcardtransfer.security.JwtProvider;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TransferService {
    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    IncomeRepository incomeRepository;

    @Autowired
    OutcomeRepository outcomeRepository;

    @Autowired
    CardRepository cardRepository;

    public ResponseEntity<?> transfer(TransferDto dto, HttpServletRequest request) {
        String username = getUsername(request);
        Optional<Card> fromOptionalCard = cardRepository.findByUsernameAndIdAndActiveTrue(username, dto.getFromCard());
        if (fromOptionalCard.isPresent()) {
            Card fromCard = fromOptionalCard.get();
            Optional<Card> toOptionalCard = cardRepository.findByIdAndActiveTrue(dto.getToCard());
            if (toOptionalCard.isPresent()) {
                Card toCard = toOptionalCard.get();
                Double amount = dto.getAmount();
                if (amount * 1.01 > fromCard.getBalance())
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                fromCard.setBalance(fromCard.getBalance() - amount * 1.01);
                toCard.setBalance(toCard.getBalance() + amount);

                Outcome outcome = new Outcome();
                outcome.setAmount(amount);
                outcome.setCommissionAmount(amount*0.01);
                outcome.setFromCard(fromCard);
                outcome.setToCard(toCard);
//                outcome.setDate(Timestamp.valueOf(LocalDateTime.now()));
                outcomeRepository.save(outcome);

                Income income = new Income();
                income.setAmount(amount);
                income.setFromCard(fromCard);
                income.setToCard(toCard);
//                income.setDate(Timestamp.valueOf(LocalDateTime.now()));
                incomeRepository.save(income);

                cardRepository.save(fromCard);
                cardRepository.save(toCard);
                return ResponseEntity.ok("Transfer successfully ended");
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private String getUsername(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        return jwtProvider.getUsernameFromToken(token);
    }
}
