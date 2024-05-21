package ru.vsu.cs.tp.recipesServerApplication.exception;

public class ImageUploadException extends RuntimeException {

    public ImageUploadException(
            final String message
    ) {
        super(message);
    }

}