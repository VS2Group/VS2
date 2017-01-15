package de.hska.exablog.Logik.Exception;

/**
 * Created by pongs on 15.01.2017.
 */
public class PasswordsDontMatchException extends Exception {
    public PasswordsDontMatchException() {super("The passwords aren't match.");}
}
