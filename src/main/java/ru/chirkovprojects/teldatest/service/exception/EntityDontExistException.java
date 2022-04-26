package ru.chirkovprojects.teldatest.service.exception;

public class EntityDontExistException extends RuntimeException {

    public EntityDontExistException(String message) {
        super(message);
    }

}
