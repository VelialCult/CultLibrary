package ru.velialcult.library.bukkit.file.exception;

public class FileNotLoadException extends RuntimeException {
    public FileNotLoadException() {
    }

    public FileNotLoadException(String message, Object... objects) {
        this(String.format(message, objects));
    }

    public FileNotLoadException(String message) {
        super(message);
    }

    public FileNotLoadException(String message, Throwable cause, Object... objects) {
        this(String.format(message, objects), cause);
    }

    public FileNotLoadException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileNotLoadException(Throwable cause) {
        super(cause);
    }

    public FileNotLoadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
