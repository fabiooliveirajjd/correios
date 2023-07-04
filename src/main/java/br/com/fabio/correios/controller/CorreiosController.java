package br.com.fabio.correios.controller;

import br.com.fabio.correios.entidade.Address;
import br.com.fabio.correios.exception.NoContentException;
import br.com.fabio.correios.exception.NotReadyException;
import br.com.fabio.correios.service.CorreioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("correios")
public class CorreiosController {

    @Autowired
    private CorreioService service;

    @GetMapping("/status")
    public String getStatus() {
        return "Service status: " + service.getStatus();
    }

    @GetMapping("/correios/{zipcode}")
    public Address getAddressByZipcode(@PathVariable("zipcode") String zipcpde) throws NoContentException, NotReadyException {
        return service.getByZipcode(zipcpde);
        //  return  Address.builder().zipcode(zipcpde).build();

    }
}

