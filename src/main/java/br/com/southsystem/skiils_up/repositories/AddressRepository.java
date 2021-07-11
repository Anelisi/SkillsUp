package br.com.southsystem.skiils_up.repositories;


import br.com.southsystem.skiils_up.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}

