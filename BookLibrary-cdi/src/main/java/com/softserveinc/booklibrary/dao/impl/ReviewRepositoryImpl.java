package com.softserveinc.booklibrary.dao.impl;

import com.softserveinc.booklibrary.dao.ReviewRepository;
import com.softserveinc.booklibrary.entity.Review;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ReviewRepositoryImpl extends EntityRepositoryImpl<Review> implements ReviewRepository {

    public ReviewRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Optional<Review> getById(Integer id) {
        Review review = null;
        try(Session session = getSessionFactory().openSession()){
            review = session.get(Review.class, id);
        }
        return Optional.of(review);
    }
}
