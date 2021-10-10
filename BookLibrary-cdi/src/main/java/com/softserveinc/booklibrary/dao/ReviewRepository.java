package com.softserveinc.booklibrary.dao;

import com.softserveinc.booklibrary.entity.Review;

import java.util.Optional;

public interface ReviewRepository {

    public Optional<Review> save(Review review);
    public Optional<Review> getById(Integer id);
    public void delete(Review review);

}
