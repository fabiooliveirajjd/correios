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
public class AddressStatus {
    public static final int DEFAULT_ID = 1;

    @Id
    private int id;
    private Status status;
}