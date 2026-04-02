package com.sivalabs.bookstore.orders.domain;

import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    public String getLoginUser() {
        // In a real application, this would return the currently authenticated user
        return "user";
    }
}
