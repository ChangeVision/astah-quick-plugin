package com.change_vision.astah.quick.internal.exception;

public class UncommitedCommandExcepition extends IllegalStateException {

    private static final long serialVersionUID = 1L;

    public UncommitedCommandExcepition() {
        super();
    }

    public UncommitedCommandExcepition(String message, Throwable cause) {
        super(message, cause);
    }

    public UncommitedCommandExcepition(String s) {
        super(s);
    }

    public UncommitedCommandExcepition(Throwable cause) {
        super(cause);
    }

}
