package com.softserveinc.booklibrary.backend.dto;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import com.softserveinc.booklibrary.backend.dto.paging.MyPage;
import com.softserveinc.booklibrary.backend.entity.AbstractEntity;

public final class DtoEntityConverter {

	private DtoEntityConverter() {
	}

	public static <T extends MyAppDto<? extends AbstractEntity<? extends Serializable>>>
	List<AbstractEntity<? extends Serializable>> convertListDtoToEntity(List<T> dtos) {
		return dtos.stream().map(MyAppDto::convertDtoToEntity).collect(Collectors.toList());
	}

	public static <T, V> MyPage<T> convertPageEntityDto(MyPage<V> page) {
		MyPage<T> entityDtoPage = new MyPage<>();
		entityDtoPage.setPageable(page.getPageable());
		entityDtoPage.setLast(page.getLast());
		entityDtoPage.setTotalPages(page.getTotalPages());
		entityDtoPage.setTotalElements(page.getTotalElements());
		entityDtoPage.setFirst(page.getFirst());
		entityDtoPage.setNumberOfFirstPageElement(page.getNumberOfFirstPageElement());
		entityDtoPage.setNumberOfElements(page.getNumberOfElements());
		return entityDtoPage;
	}
}
