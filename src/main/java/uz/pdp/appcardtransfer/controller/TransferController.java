package uz.pdp.appcardtransfer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appcardtransfer.model.TransferDto;
import uz.pdp.appcardtransfer.service.TransferService;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/transfer")
public class TransferController {
    @Autowired
    TransferService transferService;

    @PostMapping
    public ResponseEntity<?> transfer(@RequestBody TransferDto dto, HttpServletRequest request){
        return transferService.transfer(dto, request);
    }
}
