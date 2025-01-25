package br.com.compass.model;

public class Session {
    private static User loggedUser;
    private static Account userAccount;

    public static User getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(User user) {
        loggedUser = user;
    }

    public static Account getUserAccount() {
        return userAccount;
    }

    public static void setUserAccount(Account userAccount) {
        Session.userAccount = userAccount;
    }

    public static void clearSession() {
        loggedUser = null;
    }
}
