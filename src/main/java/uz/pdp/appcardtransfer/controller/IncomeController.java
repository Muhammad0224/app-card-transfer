package uz.pdp.appcardtransfer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appcardtransfer.service.IncomeService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/income")
public class IncomeController {
    @Autowired
    IncomeService incomeService;

    @GetMapping("/card/{id}")
    public ResponseEntity<?> get(@PathVariable Integer id, HttpServletRequest request){
       return incomeService.get(id, request);
    }
}
