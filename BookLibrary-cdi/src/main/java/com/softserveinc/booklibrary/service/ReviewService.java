package com.softserveinc.booklibrary.service;

import com.softserveinc.booklibrary.entity.Review;

public interface ReviewService {

    public Review create(Review review);
    public Review getById(Integer id);
    public Review update(Review review);
    public void delete(Integer id);
}
