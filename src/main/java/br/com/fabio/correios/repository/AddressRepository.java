package br.com.fabio.correios.repository;

import br.com.fabio.correios.entidade.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<Address, String> {
}
