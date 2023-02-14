package com.salesianostriana.meal.validation.validator;

import com.salesianostriana.meal.validation.annotation.FieldsMatch;
import org.springframework.beans.PropertyAccessorFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldsMatchValidator implements ConstraintValidator<FieldsMatch, Object> {

    private String field;
    private String fieldMatch;

    @Override
    public void initialize(FieldsMatch constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        Object field1 = PropertyAccessorFactory.forBeanPropertyAccess(0).getPropertyValue(field);
        Object field2 = PropertyAccessorFactory.forBeanPropertyAccess(0).getPropertyValue(fieldMatch);

        return field1.equals(field2);
    }
}
