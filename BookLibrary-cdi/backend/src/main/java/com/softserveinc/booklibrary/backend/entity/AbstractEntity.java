package com.softserveinc.booklibrary.backend.entity;

import java.io.Serializable;

public interface AbstractEntity<K extends Serializable> extends Serializable {

	K getEntityId();

}
