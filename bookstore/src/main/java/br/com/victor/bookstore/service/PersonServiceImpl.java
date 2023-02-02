package br.com.victor.bookstore.service;

import br.com.victor.bookstore.controller.PersonController;
import br.com.victor.bookstore.data.vo.PersonDTO;
import br.com.victor.bookstore.exceptions.PersonNotFoundException;
import br.com.victor.bookstore.mapper.ModelMapperV1;
import br.com.victor.bookstore.model.Person;
import br.com.victor.bookstore.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonRepository personRepository;

    public List<PersonDTO> findAll() {

        var persons =  ModelMapperV1.parseObjectList(personRepository.findAll(), PersonDTO.class);
        persons.stream().forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getId())).withSelfRel()));
        return persons;

    }

    public PersonDTO findById(Long id) {
        var entity = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException("No records found for this ID"));
        PersonDTO personDTO = ModelMapperV1.parseObject(entity, PersonDTO.class);
        personDTO.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return personDTO;
    }

    @Override
    public PersonDTO create(PersonDTO person) {
        var entity = personRepository.save(ModelMapperV1.parseObject(person, Person.class));
        var personDTO = ModelMapperV1.parseObject(entity, PersonDTO.class);
        personDTO.add(linkTo(methodOn(PersonController.class).findById(personDTO.getId())).withSelfRel());
        return personDTO;
    }

    @Override
    public PersonDTO update(PersonDTO personDTO) {
        Person person = ModelMapperV1.parseObject(findById(personDTO.getId()), Person.class);

        person.setFirstName(personDTO.getFirstName());
        person.setLastName(personDTO.getLastName());
        person.setAddress(personDTO.getAddress());
        person.setGender(personDTO.getGender());

        var returnedEntity = personRepository.save(person);
        var entity = ModelMapperV1.parseObject(returnedEntity, PersonDTO.class);
        entity.add(linkTo(methodOn(PersonController.class).findById(personDTO.getId())).withSelfRel());
        return entity;
    }

    @Override
    public void delete(Long id) {
        PersonDTO entity = findById(id);
        Person person = ModelMapperV1.parseObject(entity, Person.class);
        personRepository.delete(person);
    }


}
