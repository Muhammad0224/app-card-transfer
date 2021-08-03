package uz.pdp.appcardtransfer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.appcardtransfer.entity.Card;
import uz.pdp.appcardtransfer.model.CardDto;
import uz.pdp.appcardtransfer.repository.CardRepository;
import uz.pdp.appcardtransfer.security.JwtProvider;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {
    @Autowired
    CardRepository cardRepository;

    @Autowired
    JwtProvider jwtProvider;

    public ResponseEntity<?> get(HttpServletRequest request) {
        String username = getUsername(request);
        return ResponseEntity.ok(cardRepository.findAllByUsername(username));
    }

    public ResponseEntity<?> get(Integer id, HttpServletRequest request) {
        String username = getUsername(request);
        Optional<Card> optionalCard = cardRepository.findByUsernameAndId(username,id);
        if (optionalCard.isPresent()) {
            return ResponseEntity.ok(optionalCard.get());
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> add(CardDto dto, HttpServletRequest request) {
        String username = getUsername(request);
        Card card = new Card();
        card.setActive(true);
        card.setBalance(dto.getBalance());
        card.setExpireDate(new Date(System.currentTimeMillis()+ 31536000000L));
        card.setUsername(username);
        card.setNumber(dto.getNumber());
        cardRepository.save(card);
        return ResponseEntity.ok("Card added!");
    }

//    public ResponseEntity<?> edit(Integer id, CardDto dto, HttpServletRequest request) {
//        String username = getUsername(request);
//        Optional<Card> optionalCard = cardRepository.findByUsernameAndId(username, id);
//        if (optionalCard.isPresent()){
//            Card card = optionalCard.get();
//            card.setNumber(dto.getNumber());
//        }
//    }

    private String getUsername(HttpServletRequest request){
        String token = request.getHeader("Authorization").substring(7);
        return jwtProvider.getUsernameFromToken(token);
    }

    public void eliminateCard(String username){
        List<Card> cardList = cardRepository.findAllByUsername(username);
        for (Card card : cardList) {
            if (card.getExpireDate().before(new java.util.Date())){
                card.setActive(false);
                cardRepository.save(card);
            }
        }
    }
}
