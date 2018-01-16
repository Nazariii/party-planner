package com.redparty.partyplanner.jsp.service.impl;

import com.redparty.partyplanner.jsp.service.LoginJspService;
import org.springframework.stereotype.Service;

@Service
public class LoginJspServiceImpl implements LoginJspService {
    @Override
    public boolean validateUser(String userid, String password) {
        // dummy
        return userid.equalsIgnoreCase("user")
                && password.equalsIgnoreCase("1234");
    }
}
