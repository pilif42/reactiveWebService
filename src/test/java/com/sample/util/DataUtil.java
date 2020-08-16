package com.sample.util;

import com.sample.db.entity.Account;

public class DataUtil {
    private static final Long ACCOUNT_ID = 1L;

    private static final String EMAIL = "john_doe@gmail.com";
    private static final String PASSWORD = "samplePwd";
    private static final String ROLE = "tester";

    public static Account buildAccount() {
        return Account.builder().id(ACCOUNT_ID).email(EMAIL).password(PASSWORD).role(ROLE).build();
    }
}
