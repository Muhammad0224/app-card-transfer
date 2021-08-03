package uz.pdp.appcardtransfer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.appcardtransfer.entity.Card;
import uz.pdp.appcardtransfer.repository.CardRepository;
import uz.pdp.appcardtransfer.repository.IncomeRepository;
import uz.pdp.appcardtransfer.repository.OutcomeRepository;
import uz.pdp.appcardtransfer.security.JwtProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class OutcomeService {
    @Autowired
    OutcomeRepository outcomeRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    JwtProvider jwtProvider;

    public ResponseEntity<?> get(Integer id, HttpServletRequest request) {
        String username = getUsername(request);
        Optional<Card> optionalCard = cardRepository.findByUsernameAndIdAndActiveTrue(username, id);
        if (optionalCard.isPresent()) {
            return ResponseEntity.ok(outcomeRepository.findAllByFromCardId(id));
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    private String getUsername(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        return jwtProvider.getUsernameFromToken(token);
    }
}
