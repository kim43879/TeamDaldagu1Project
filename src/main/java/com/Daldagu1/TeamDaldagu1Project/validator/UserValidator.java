package com.Daldagu1.TeamDaldagu1Project.validator;

import com.Daldagu1.TeamDaldagu1Project.beans.UserBean;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return UserBean.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        UserBean userBean = (UserBean)target;
        String beanName = errors.getObjectName();
        System.out.println("유저 빈 : "+beanName);
        System.out.println("테스트 : "  + userBean.getUser_pw());
        System.out.println("테스트 : "  + userBean.getUser_pw2());

        if(beanName.equals("modifyUserBean")){

            if (!userBean.getUser_pw().equals(userBean.getUser_pw2())) {
                errors.rejectValue("user_pw", "NotEquals");
                errors.rejectValue("user_pw2", "NotEquals");
            }
        }
    }
}
