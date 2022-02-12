package com.example.app.services;

import com.example.app.interfaces.BookServiceInterface;
import com.example.app.models.dbentities.AuthorBookEntity;
import com.example.app.models.dbentities.BookEntity;
import com.example.app.models.dtos.AuthorBookJoin;
import com.example.app.models.dtos.Book;
import com.example.app.models.dtos.BookAndStock;
import com.example.app.repositories.AuthorBookRepository;
import com.example.app.repositories.BookRepository;
import com.example.app.repositories.CustomQueryRepository;
import com.example.app.repositories.mappers.BookRowMapper;
import com.example.app.repositories.query.BasicQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService implements BookServiceInterface {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorBookRepository authorBookRepository;

    @Autowired
    private CustomQueryRepository customQueryRepository;

    @Override
    public List<Book> getBooks(BasicQueryCriteria queryCriteria) {
        List<BookEntity> bookEntityList = customQueryRepository.getEntitesByCriteria(queryCriteria, new BookRowMapper());
        return bookEntityList.stream().map(Book::new).collect(Collectors.toList());
    }

    @Override
    public List<Book> getAllBooksByISBN(List<String> ISBNs) {
        List<BookEntity> listOfBookEntities = (List<BookEntity>) bookRepository.findAllById(ISBNs);
        return listOfBookEntities.stream().map(Book::new).collect(Collectors.toList());
    }

    @Override
    public Optional<Book> getBook(String ISBN) {
        return bookRepository.findById(ISBN).map(Book::new);
    }

    @Override
    public Book putBook(String ISBN, Book book) throws Exception {

        BookEntity savedBookEntity = new BookEntity(book);
        savedBookEntity.setISBN(ISBN);

        // Checking resource and status code
        try {
            return new Book(bookRepository.save(savedBookEntity));
        } catch (Exception constraintViolationException) {
            throw new Exception(constraintViolationException.getMessage());
        }
    }

    @Override
    public void deleteBook(String ISBN) throws Exception {
        try {
            bookRepository.deleteById(ISBN);
        } catch (DataAccessException dataAccessException) {
            throw new Exception("Book does not exists");
        }
    }

    @Override
    public List<AuthorBookJoin> postAuthorsToBook(String ISBN, List<Integer> authors_ids) throws Exception {
        Optional<BookEntity> bookEntityOptional = bookRepository.findById(ISBN);

        if (bookEntityOptional.isPresent()) {
            try {
                int startIndex = ((List<AuthorBookEntity>) authorBookRepository.findBookAuthors(ISBN)).size() + 1;
                List<AuthorBookEntity> authorBookEntities = new ArrayList<>();
                for (int i = 0; i < authors_ids.size(); ++i)
                    authorBookEntities.add(new AuthorBookEntity(authors_ids.get(i), ISBN, startIndex + i));

                authorBookRepository.saveAll(authorBookEntities);
                return ((List<AuthorBookEntity>) authorBookRepository.findBookAuthors(ISBN)).stream().map(AuthorBookJoin::new).collect(Collectors.toList());
            } catch (Exception exception) {
                throw new Exception("Incorect body format or one of the authors does not exists");
            }
        } else {
            throw new Exception("Book does not exists");
        }
    }

    @Override
    public List<Book> updateBooksStock(List<BookAndStock> bookAndStockList, Boolean addingStock) throws Exception {

        // Checking the bookAndStock objects' stock
        List<BookAndStock> bookAndStocks = bookAndStockList.stream().filter(bookAndStock -> bookAndStock.getStock() <= 0).collect(Collectors.toList());
        if(!bookAndStocks.isEmpty())
            throw new Exception("Invalid order. Invalid number of products requested.");

        // Getting books ids from
        List<String> bookIds = bookAndStockList.stream().map(BookAndStock::getISBN).collect(Collectors.toList());
        // Getting the books with these ids
        List<BookEntity> bookList = (List<BookEntity>) bookRepository.findAllById(bookIds);

        // There are book ids that does not correspond to a book
        if (bookList.size() != bookIds.size())
            throw new Exception("There are book ids that does not correspond to a book");

        boolean availableStockForAll = true;
        for (int i = 0; i < bookList.size(); ++i) {
            int newStock = bookList.get(i).getStock() - bookAndStockList.get(i).getStock();
            availableStockForAll &= (newStock >= 0);
            bookList.get(i).setStock(newStock);
        }

        if (!availableStockForAll)
            throw new Exception("Not enough stock for all the products");

        try {
            bookRepository.saveAll(bookList);
            return bookList.stream().map(Book::new).collect(Collectors.toList());
        } catch (Exception exception) {
            throw new Exception("Not enough stock for all the products");
        }
    }
}
