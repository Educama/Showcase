package org.educama.common.exceptions;

/**
 * Exception to throw if precondition is not satisfied.
 */
@SuppressWarnings("serial")
public class PreconditionViolationException extends RuntimeException {

    private final String messageKey;

    /**
     * Constructor to initialize exception with a message key to translate.
     *
     * @param messageKey the message key to translate
     */
    public PreconditionViolationException(String messageKey) {
        this.messageKey = messageKey;
    }

    /**
     * Constructor to initialize exception with a message key to translate and error cause.
     *
     * @param messageKey the message key to translate
     * @param cause      the cause
     */
    public PreconditionViolationException(String messageKey, Throwable cause) {
        super(cause);
        this.messageKey = messageKey;
    }

    public String getMessageKey() {
        return messageKey;
    }
}
