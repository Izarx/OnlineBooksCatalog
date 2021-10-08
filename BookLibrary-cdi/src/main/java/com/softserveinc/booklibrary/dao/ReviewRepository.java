package com.softserveinc.booklibrary.dao;

import com.softserveinc.booklibrary.entity.Review;

public interface ReviewRepository {

    public Review save(Review author);
    public Review getById(Integer id);
    public void delete(Integer id);

}
