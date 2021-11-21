package com.softserveinc.booklibrary.backend.service.impl;

import com.softserveinc.booklibrary.backend.dto.BookDto;
import com.softserveinc.booklibrary.backend.dto.paging.MyPage;
import com.softserveinc.booklibrary.backend.entity.Book;
import com.softserveinc.booklibrary.backend.repository.BookRepository;
import com.softserveinc.booklibrary.backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl extends AbstractEntityService<Book> implements BookService {

	@Autowired
	public BookServiceImpl(BookRepository bookRepository) {
		repository = bookRepository;
	}

	@Override
	public MyPage<BookDto> convertPageEntityDto(MyPage<Book> page) {
		MyPage<BookDto> bookDtoMyPage = (MyPage<BookDto>) super.convertPageEntityDto(page);
		bookDtoMyPage.setContent(BookDto.convertListBookToDto(page.getContent()));
		return bookDtoMyPage;
	}

}
