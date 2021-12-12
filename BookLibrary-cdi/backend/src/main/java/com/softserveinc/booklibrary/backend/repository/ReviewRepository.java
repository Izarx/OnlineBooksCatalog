package com.softserveinc.booklibrary.backend.repository;

import com.softserveinc.booklibrary.backend.entity.Review;
import com.softserveinc.booklibrary.backend.pagination.filtering.ReviewFilter;

public interface ReviewRepository extends EntityRepository<Review, ReviewFilter> {

}
