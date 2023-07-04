package br.com.fabio.correios.entidade;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    private String zipcode;
    private String street;
    private String district;
    private String state;
    private String city;
}

