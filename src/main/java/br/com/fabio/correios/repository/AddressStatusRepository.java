package br.com.fabio.correios.repository;

import br.com.fabio.correios.entidade.AddressStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AddressStatusRepository extends CrudRepository<AddressStatus, Integer> {
}
