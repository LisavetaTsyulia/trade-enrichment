package com.verygoodbank.tes.validator;

import com.verygoodbank.tes.exception.DateFormatValidationException;
import com.verygoodbank.tes.model.Trade;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.validator.Validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DateFormatValidatorTest {

    @Test
    public void testDateFormatValidatorSuccess() {
        // given date format validator and Trade with valid date
        Validator<Trade> validator = new DateFormatValidator();
        Trade validTrade = new Trade();
        validTrade.setDate("19990101");

        // when validating
        // then no exception is thrown
        assertDoesNotThrow(() -> validator.validate(validTrade));
    }

    @Test
    public void testDateFormatValidatorNoDate() {
        // given date format validator and Trade with no date set
        Validator<Trade> validator = new DateFormatValidator();
        Trade validTrade = new Trade();

        // when validating
        // then no exception is thrown
        assertThrows(DateFormatValidationException.class, () -> validator.validate(validTrade));
    }

    @Test
    public void testDateFormatValidatorNotValidDateMonthMoreThenExists() {
        // given date format validator and Trade with not valid date set: month is not correct
        Validator<Trade> validator = new DateFormatValidator();
        Trade validTrade = new Trade();
        validTrade.setDate("19993131");

        // when validating
        // then no exception is thrown
        assertThrows(DateFormatValidationException.class, () -> validator.validate(validTrade));
    }

    @Test
    public void testDateFormatValidatorNotValidDate() {
        // given date format validator and Trade with not valid date set, too long
        Validator<Trade> validator = new DateFormatValidator();
        Trade validTrade = new Trade();
        validTrade.setDate("1999010101");

        // when validating
        // then no exception is thrown
        assertThrows(DateFormatValidationException.class, () -> validator.validate(validTrade));
    }
}
