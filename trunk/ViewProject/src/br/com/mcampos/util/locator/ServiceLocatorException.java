package br.com.mcampos.util.locator;

public class ServiceLocatorException extends Exception {
    public ServiceLocatorException(Exception e) {
        super(e.getMessage());
    }
}
