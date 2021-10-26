package com.softserveinc.booklibrary.backend.entity;

import java.io.Serializable;

public interface MyAppEntity<K extends Serializable> extends Serializable {

	K getEntityId();

}
