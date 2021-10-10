package com.softserveinc.booklibrary.dao.impl;

import com.softserveinc.booklibrary.dao.ReviewRepository;
import com.softserveinc.booklibrary.entity.Review;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public class ReviewRepositoryImpl extends EntityRepositoryImpl<Review> implements ReviewRepository {

    @Override
    @Transactional
    public Optional<Review> getById(Integer id) {
        Review review = getEntityManager().find(Review.class, id);
        return Optional.ofNullable(review);
    }
}
