package com.learning.components.query.hsql;

public @interface Like {
	LikeType value() default LikeType.LIKE;
	String propertyName() default "";
}
