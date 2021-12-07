package com.softserveinc.booklibrary.backend.repository;

import com.softserveinc.booklibrary.backend.dto.filtering.ReviewFilter;
import com.softserveinc.booklibrary.backend.entity.Review;

public interface ReviewRepository extends EntityRepository<Review, ReviewFilter> {

}
