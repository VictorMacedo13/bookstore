package br.com.victor.bookstore.service;

import br.com.victor.bookstore.controller.BookController;
import br.com.victor.bookstore.data.vo.BookDTO;
import br.com.victor.bookstore.exceptions.PersonNotFoundException;
import br.com.victor.bookstore.mapper.ModelMapperV1;
import br.com.victor.bookstore.model.Book;
import br.com.victor.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    BookRepository bookRepository;

    @Override
    public List<BookDTO> findAll() {
        var books = ModelMapperV1.parseObjectList(bookRepository.findAll(), BookDTO.class);
        books.stream().forEach(b -> b.add(linkTo(methodOn(BookController.class).findById(b.getId())).withSelfRel()));
        return books;
    }

    @Override
    public BookDTO findById(Long id) {
        var entity = bookRepository.findById(id).orElseThrow(() -> new PersonNotFoundException("No records found for this ID"));
        BookDTO book = ModelMapperV1.parseObject(entity, BookDTO.class);
        book.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        return book;
    }

    @Override
    public BookDTO create(BookDTO bookDTO) {
        Book book = ModelMapperV1.parseObject(bookDTO, Book.class);
        bookDTO = ModelMapperV1.parseObject(bookRepository.save(book), BookDTO.class);
        bookDTO.add(linkTo(methodOn(BookController.class).findById(bookDTO.getId())).withSelfRel());
        return bookDTO;
    }

    @Override
    public BookDTO update(BookDTO bookDTO) {
        Book book = ModelMapperV1.parseObject(bookRepository.findById(bookDTO.getId()), Book.class);

        book.setAuthor(bookDTO.getAuthor());
        book.setPrice(bookDTO.getPrice());
        book.setTitle(bookDTO.getTitle());
        book.setLaunchDate(bookDTO.getLaunchDate());

        bookDTO = ModelMapperV1.parseObject(bookRepository.save(book), BookDTO.class);
        bookDTO.add(linkTo(methodOn(BookController.class).findById(bookDTO.getId())).withSelfRel());
        return bookDTO;
    }

    @Override
    public void delete(Long id) {
        BookDTO bookDTO = findById(id);
        Book book = ModelMapperV1.parseObject(bookDTO, Book.class);
        bookRepository.delete(book);
    }
}
