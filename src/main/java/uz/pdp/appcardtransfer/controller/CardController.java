package uz.pdp.appcardtransfer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appcardtransfer.entity.Card;
import uz.pdp.appcardtransfer.model.CardDto;
import uz.pdp.appcardtransfer.service.CardService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/card")
public class CardController {
    @Autowired
    CardService cardService;

    @GetMapping
    public ResponseEntity<?> get(HttpServletRequest request) {
        return cardService.get(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Integer id, HttpServletRequest request) {
        return cardService.get(id, request);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody CardDto dto, HttpServletRequest request) {
        return cardService.add(dto, request);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<?> edit(@PathVariable Integer id, @RequestBody CardDto dto, HttpServletRequest request){
//        return cardService.edit(id, dto, request);
//    }
}
