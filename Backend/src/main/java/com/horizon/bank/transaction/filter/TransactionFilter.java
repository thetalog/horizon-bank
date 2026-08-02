package com.horizon.bank.transaction.filter;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.horizon.bank.accounts.service.AccountService;
import com.horizon.bank.user.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class TransactionFilter extends OncePerRequestFilter {
    private final UserService userService;
    private final AccountService accountService;
    public TransactionFilter(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }
    private static final List<String> FILTERED_PATHS = List.of(
        "/transaction/deposit"
    );

    private final AntPathMatcher matcher = new AntPathMatcher();
  
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();

        return FILTERED_PATHS.stream()
                .noneMatch(pattern -> matcher.match(pattern, path));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain)
            throws java.io.IOException, ServletException {
                // verify deposit sanction personnel
        String sanctionedBy = request.getHeader("sanctioned_by");
        if (sanctionedBy == null || sanctionedBy.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing sanctioned_by header");
            return;
        }
        Boolean isSanctionedByValid = userService.isCreatorIdValidAndActive(sanctionedBy);
        if (!isSanctionedByValid) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid sanctioned_by header");
            return;
        }
        // verify account user
        String accountUserId = request.getHeader("account_user_id");
        if (accountUserId == null || accountUserId.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing account_user_id header");
            return;
        }
        Boolean isAccountUserActive = userService.isUserActive(accountUserId);
        if (!isAccountUserActive) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Account user is not active");
            return;
        }
        // verify account is active
        String accountId = request.getHeader("account_id");
        if (accountId == null || accountId.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing account_id header");
            return;
        }
        Boolean isAccountActive = accountService.isAccountActive(accountId);
        if (!isAccountActive) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Account is not active");
            return;
        }
        filterChain.doFilter(request, response);
    }
    
}
