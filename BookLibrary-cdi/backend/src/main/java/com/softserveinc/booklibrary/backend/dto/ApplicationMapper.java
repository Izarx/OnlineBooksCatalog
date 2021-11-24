package com.softserveinc.booklibrary.backend.dto;

import java.util.List;

import com.softserveinc.booklibrary.backend.entity.Author;
import com.softserveinc.booklibrary.backend.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {

	// this annotation sets number format with 2 signs after comma
	@Mapping(target = "authorRating", source = "authorRating", numberFormat = "$#.00")
	AuthorDto authorToAuthorDto(Author author);

	Author authorDtoToAuthor(AuthorDto authorDto);

	BookDto bookToBookDto(Book book);

	Book bookDtoToBook(BookDto bookDto);

	List<AuthorDto> listAuthorsToListAuthorsDto(List<Author> authors);

	List<BookDto> listBooksToListBooksDto(List<Book> books);

}
