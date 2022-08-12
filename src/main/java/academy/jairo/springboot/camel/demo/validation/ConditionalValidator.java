package academy.jairo.springboot.camel.demo.validation;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.util.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class ConditionalValidator implements ConstraintValidator<Conditional, Object> {

    private String selected;

    private String[] required;

    private String message;

    private String[] values;

    private ConditionalType conditionalType;

    @Override
    public void initialize(Conditional requiredIfChecked) {

        selected = requiredIfChecked.selected();
        required = requiredIfChecked.required();
        message = requiredIfChecked.message();
        values = requiredIfChecked.values();
        conditionalType = requiredIfChecked.conditionalType();

    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        Boolean valid = true;
        try {
            Object checkedValue = BeanUtils.getProperty(object, selected);
            switch (conditionalType) {
                case EQUALS_AS_VALUES:
                    this.validEqualsAsValue(object, context, valid, checkedValue);
                    break;
                case BETTER_THAN:
                    this.validBetterThan(object, context, valid, checkedValue);
                    break;
            }
        } catch (IllegalAccessException e) {
            log.error("Accessor method is not available: exception : {}", e);
            return false;
        } catch (NoSuchMethodException e) {
            log.error("Field or method is not present: exception : {}", e);
            return false;
        } catch (InvocationTargetException e) {
            log.error("Exception occurred while accessing class:{}, exception:{}", object.getClass().getName(), e);
            return false;
        }

        return valid;

    }



    private void verifyValidation(ConstraintValidatorContext context, String propName, Boolean valid) {

        if (!valid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addPropertyNode(propName).addConstraintViolation();
        }

    }



    private void verifyRequiredValue(Object object, ConstraintValidatorContext context, Boolean valid)
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        for (String propName : required) {
            Object requiredValue = BeanUtils.getProperty(object, propName);
            valid = requiredValue != null && !ObjectUtils.isEmpty(requiredValue);
            verifyValidation(context, propName, valid);
        }

    }

    private void validEqualsAsValue(Object object, ConstraintValidatorContext context, Boolean valid,
                                    Object checkedValue)
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        if (Arrays.asList(values).contains(checkedValue)) {
            this.verifyRequiredValue(object, context, valid);
        }

    }

    private void validBetterThan(Object object, ConstraintValidatorContext context, Boolean valid, Object checkedValue)
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        BigDecimal value = new BigDecimal(Arrays.asList(values).get(0));
        if (new BigDecimal(String.valueOf(checkedValue)).compareTo(value) > 0) {
            this.verifyRequiredValue(object, context, valid);
        }

    }

}
