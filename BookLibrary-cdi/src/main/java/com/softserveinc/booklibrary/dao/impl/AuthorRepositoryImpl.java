package com.softserveinc.booklibrary.dao.impl;

import com.softserveinc.booklibrary.dao.AuthorRepository;
import com.softserveinc.booklibrary.entity.Author;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public class AuthorRepositoryImpl extends EntityRepositoryImpl<Author> implements AuthorRepository {

    @Override
    @Transactional
    public Optional<Author> getById(Integer id) {
        Author author = getEntityManager().find(Author.class, id);
        return Optional.of(author);
    }
}
