package com.cao.shoppingApp.exception;

public class DuplicateUsernameOrEmailException extends Exception{
    public DuplicateUsernameOrEmailException(String s) {
        super(s);
    }
}
