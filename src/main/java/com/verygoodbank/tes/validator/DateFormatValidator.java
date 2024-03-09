package com.verygoodbank.tes.validator;

import com.verygoodbank.tes.constant.TradeEnrichConstant;
import com.verygoodbank.tes.exception.DateFormatValidationException;
import com.verygoodbank.tes.model.Trade;
import org.apache.commons.validator.GenericValidator;
import org.springframework.batch.item.validator.Validator;

//@Component
public class DateFormatValidator implements Validator<Trade> {

    @Override
    public void validate(final Trade trade) throws DateFormatValidationException {
        if (!GenericValidator.isDate(trade.getDate(), TradeEnrichConstant.DATE_VALID_FORMAT, true))
            throw new DateFormatValidationException();

    }
}
