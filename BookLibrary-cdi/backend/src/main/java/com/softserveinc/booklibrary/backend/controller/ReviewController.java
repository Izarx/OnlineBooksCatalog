package com.softserveinc.booklibrary.backend.controller;

import com.softserveinc.booklibrary.backend.dto.ApplicationMapper;
import com.softserveinc.booklibrary.backend.dto.ReviewDto;
import com.softserveinc.booklibrary.backend.dto.filtering.ReviewFilter;
import com.softserveinc.booklibrary.backend.entity.Review;
import com.softserveinc.booklibrary.backend.pagination.RequestOptions;
import com.softserveinc.booklibrary.backend.pagination.ResponseData;
import com.softserveinc.booklibrary.backend.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReviewController.class);

	private final ApplicationMapper appMapper;

	private final ReviewService reviewService;

	public ReviewController(ApplicationMapper appMapper, ReviewService reviewService) {
		this.appMapper = appMapper;
		this.reviewService = reviewService;
	}

	@PostMapping
	public ResponseEntity<ResponseData<ReviewDto>> listReviewsOfBook(
			@RequestBody RequestOptions<ReviewFilter> requestOptions) {
		return ResponseEntity
				.ok(convertAuthorResponseToAuthorDtoResponse(
						reviewService.listEntities(requestOptions)));
	}

	private ResponseData<ReviewDto> convertAuthorResponseToAuthorDtoResponse(ResponseData<Review> responseData) {
		ResponseData<ReviewDto> reviewDtoResponseData = new ResponseData<>();
		reviewDtoResponseData.setTotalElements(responseData.getTotalElements());
		reviewDtoResponseData.setContent(appMapper.listReviewsToListReviewsDto(responseData.getContent()));
		return reviewDtoResponseData;
	}

}
