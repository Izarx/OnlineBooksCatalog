package com.softserveinc.booklibrary.dao.impl;

import com.softserveinc.booklibrary.dao.ReviewRepository;
import com.softserveinc.booklibrary.entity.Review;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public class ReviewRepositoryImpl implements ReviewRepository {

    private static Logger logger = LoggerFactory.getLogger(ReviewRepositoryImpl.class);
    private final SessionFactory sessionFactory;

    @Autowired
    public ReviewRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    @Override
    public Optional<Review> save(Review review) {
        try(Session session = sessionFactory.openSession()) {
            session.save(review);
        }
        return Optional.of(review);
    }

    @Transactional
    @Override
    public Optional<Review> getById(Integer id) {
        logger.info("Review Repository get id method");
        Review review = null;
        try(Session session = sessionFactory.openSession()){
            review = session.get(Review.class, id);
        }
        return Optional.of(review);
    }

    @Transactional
    @Override
    public void delete(Review review) {
        try(Session session = sessionFactory.openSession()) {
            session.delete(review);
        }
    }
}
