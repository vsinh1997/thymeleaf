package com.example.thymeleaftutorial.helper;

import javax.servlet.http.HttpSession;

public class UserHelper {
    public static String getSessionAttributeAsString(HttpSession session, String atrributeName) {
        return session.getAttribute(atrributeName) != null ? (String) session.getAttribute(atrributeName) : "";
    }

    public static String redirectToUserPageWithSearchByEmail(int currentPage, String email) {
        return "redirect:/user/page/" + currentPage + "?searchEmail=" + email;
    }
}
