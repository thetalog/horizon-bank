package com.horizon.bank.cards.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.horizon.bank.accounts.entity.AccountEntity;
import com.horizon.bank.accounts.repository.AccountRepository;
import com.horizon.bank.common.component.ResponseStructure;
import com.horizon.bank.user.entity.UserEntity;
import com.horizon.bank.user.entity.enums.UserRoles;
import com.horizon.bank.user.repository.UserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class CardFilter implements Filter {
    ResponseStructure responseStructure;
    UserRepository userRepository;
    AccountRepository accountRepository;
    CardFilter(ResponseStructure responseStructure, UserRepository userRepository, AccountRepository accountRepository){
        this.responseStructure = responseStructure;
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();

        HttpServletRequest req = (HttpServletRequest) request;
        if(!req.getRequestURI().startsWith("/card")){
            chain.doFilter(request, response);
            return;
        }
        System.out.println("Request URI: " + req.getRequestURI());
        String employeeId = req.getHeader("EMPLOYEE_ID");
//        String cardNumber = req.getHeader("CARD_NUMBER");
//|| cardNumber == null || cardNumber.isEmpty()
        if(employeeId == null || employeeId.isEmpty() ){
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            responseStructure.setMessage("Something went missing.");
            responseStructure.setError(true);
            responseStructure.setStatusCode(403);
            mapper.writeValue(response.getWriter(), responseStructure);
            return;
        }
        // Check Employee has admin role
        Optional<UserEntity> employee = userRepository.findById(employeeId);
        if(employee.get().getIsActive() == null){
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            responseStructure.setMessage("Something went wrong");
            responseStructure.setError(true);
            responseStructure.setStatusCode(403);
            responseStructure.setData(null);
            mapper.writeValue(response.getWriter(), responseStructure);
            return;
        }
        if(employee.isEmpty()){
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            responseStructure.setMessage("Employee is not found");
            responseStructure.setError(true);
            responseStructure.setStatusCode(403);
            mapper.writeValue(response.getWriter(), responseStructure);
            return;
        } else if(!employee.get().getIsActive() || !employee.get().getRoles().contains(UserRoles.ADMIN)){
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            responseStructure.setMessage("Employee is not admin");
            responseStructure.setError(true);
            responseStructure.setStatusCode(403);
            mapper.writeValue(response.getWriter(), responseStructure);
            return;
        }
        // Continue to the next filter or controller
        chain.doFilter(request, response);

        System.out.println("Response sent.");
    }
}