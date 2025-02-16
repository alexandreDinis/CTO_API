package com.dinis.cto.infra.security;

import com.dinis.cto.model.person.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    public static void verifyUserPermission(HttpServletRequest request, Long targetUserId) {
        Long authenticatedUserId = (Long) request.getAttribute("userID");

        if (!authenticatedUserId.equals(targetUserId)) {
            throw new RuntimeException("Acesso negado: você não tem permissão.");
        }
    }

    public static User authenticateAndGetUser(HttpServletRequest request) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof User) {
            return (User) principal;
        }

        throw new RuntimeException("Usuário não autenticado.");
    }
}
