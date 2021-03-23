package com.sample.web.admin.controller.html.users.users;

import static com.sample.common.util.ValidateUtils.isNotEquals;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.sample.domain.validator.AbstractValidator;

/**
 * 사용자 등록 입력 체크
 */
@Component
public class UserFormValidator extends AbstractValidator<UserForm> {

    @Override
    protected void doValidate(UserForm form, Errors errors) {

        // 확인용 암호와 일치하는지 체크한다.
        if (isNotEquals(form.getPassword(), form.getPasswordConfirm())) {
            errors.rejectValue("password", "users.unmatchPassword");
            errors.rejectValue("passwordConfirm", "users.unmatchPassword");
        }
    }
}
