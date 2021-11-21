package com.softserveinc.booklibrary.backend.service.impl;

import com.softserveinc.booklibrary.backend.dto.AuthorDto;
import com.softserveinc.booklibrary.backend.dto.paging.MyPage;
import com.softserveinc.booklibrary.backend.entity.Author;
import com.softserveinc.booklibrary.backend.repository.AuthorRepository;
import com.softserveinc.booklibrary.backend.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl extends AbstractEntityService<Author> implements AuthorService {

	@Autowired
	public AuthorServiceImpl(AuthorRepository authorRepository) {
		repository = authorRepository;
	}

	@Override
	public MyPage<AuthorDto> convertPageEntityDto(MyPage<Author> page) {
		MyPage<AuthorDto> authorDtoMyPage = (MyPage<AuthorDto>) super.convertPageEntityDto(page);
		authorDtoMyPage.setContent(AuthorDto.convertListAuthorToDto(page.getContent()));
		return authorDtoMyPage;
	}
}
