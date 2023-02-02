package br.com.victor.bookstore.service;


import br.com.victor.bookstore.data.vo.BookDTO;

import java.util.List;

public interface BookService {
    List<BookDTO> findAll();
    BookDTO findById(Long id);
    BookDTO create(BookDTO person);
    BookDTO update(BookDTO person);
    void delete(Long id);
}
