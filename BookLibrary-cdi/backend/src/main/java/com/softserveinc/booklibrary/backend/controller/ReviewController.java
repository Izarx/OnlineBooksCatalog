package com.softserveinc.booklibrary.backend.controller;

import com.softserveinc.booklibrary.backend.dto.ApplicationMapper;
import com.softserveinc.booklibrary.backend.dto.ReviewDto;
import com.softserveinc.booklibrary.backend.dto.ReviewDtoWithoutBook;
import com.softserveinc.booklibrary.backend.entity.Review;
import com.softserveinc.booklibrary.backend.pagination.RequestOptions;
import com.softserveinc.booklibrary.backend.pagination.ResponseData;
import com.softserveinc.booklibrary.backend.pagination.filtering.ReviewFilter;
import com.softserveinc.booklibrary.backend.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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

	@PostMapping("/create")
	public ResponseEntity<ReviewDto> createReview(@RequestBody ReviewDto reviewDto) {
		if (reviewDto == null) {
			LOGGER.warn("Creating Review, ReviewController, Transfer object which received from UI is empty!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		LOGGER.debug("Creating Review, ReviewController, Transfer object which received from UI is {}", reviewDto);
		return ResponseEntity.ok(appMapper.reviewToReviewDto(reviewService.create(appMapper.reviewDtoToReview(reviewDto))));
	}

	@PostMapping
	public ResponseEntity<ResponseData<ReviewDtoWithoutBook>> listReviewsOfBook(@RequestBody RequestOptions<ReviewFilter> requestOptions) {
		LOGGER.debug("Getting Filtered Sorted Page of Reviews, ReviewController, Request options to fetch page of Reviews is {}", requestOptions);
		return ResponseEntity.ok(convertReviewResponseToReviewDtoResponse(reviewService.listEntities(requestOptions)));
	}

	private ResponseData<ReviewDtoWithoutBook> convertReviewResponseToReviewDtoResponse(ResponseData<Review> responseData) {
		ResponseData<ReviewDtoWithoutBook> reviewDtoResponseData = new ResponseData<>();
		if (responseData != null) {
			LOGGER.debug("Converting Response Data with Reviews to Response Data with Reviews DTOs, ReviewController, " +
							"response data BEFORE converting: total number of books = {} ; list with reviews = {}",
					responseData.getTotalElements(), responseData.getContent());
			reviewDtoResponseData.setTotalElements(responseData.getTotalElements());
			reviewDtoResponseData.setContent(appMapper.listReviewsToListReviewsDtoWithoutBook(responseData.getContent()));
		}
		else {
			LOGGER.warn("Converting Response Data with Reviews to Response Data with Reviews DTOs, ReviewController, " +
					"response data is empty!");
		}
		LOGGER.debug("Converting Response Data with Reviews to Response Data with Reviews DTOs, ReviewController, " +
						"response data BEFORE converting: total number of books = {} ; size list of reviews = {}",
				reviewDtoResponseData.getTotalElements(), reviewDtoResponseData.getContent().size());
		return reviewDtoResponseData;
	}

}
