package com.labshigh.aicfo.internal.api.mainWallet.validator;

import com.labshigh.aicfo.internal.api.common.Constants;
import com.labshigh.aicfo.internal.api.mainWallet.model.request.MainWalletRequestModel;
import lombok.Builder;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

@Builder
public class MainWalletWithdrawalRequestValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {return MainWalletRequestModel.class.equals(clazz);}

    @Override
    public void validate(Object target, Errors errors) {
        MainWalletRequestModel requestModel = (MainWalletRequestModel) target;

        if(!StringUtils.hasText(requestModel.getWalletAddress())) {
            errors.reject("walletAddress.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "walletAddress"));
        }

        if(requestModel.getAmount().compareTo(BigDecimal.ZERO) < 0 ) {
            errors.reject("amount.required",
                    String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "amount"));
        }

    }
}
