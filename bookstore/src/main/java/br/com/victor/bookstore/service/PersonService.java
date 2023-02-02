package br.com.victor.bookstore.service;

import br.com.victor.bookstore.data.vo.PersonDTO;
import br.com.victor.bookstore.model.Person;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PersonService {
    List<PersonDTO> findAll();
    PersonDTO findById(Long id);
    PersonDTO create(PersonDTO person);
    PersonDTO update(PersonDTO person);
    void delete(Long id);
}
