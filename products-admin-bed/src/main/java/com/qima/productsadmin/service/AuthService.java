package com.qima.productsadmin.service;

import com.qima.productsadmin.model.JwtResponse;
import com.qima.productsadmin.model.LoginFormRequest;

public interface AuthService {

    JwtResponse authenticate(LoginFormRequest login);

}
