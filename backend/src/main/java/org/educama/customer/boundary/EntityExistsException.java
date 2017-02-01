package org.educama.customer.boundary;

/**
 * This exception is thrown whenever an operation on a given entity could not be performed
 * due to the fact that an entity with the same values already exists.
 */
public class EntityExistsException extends RuntimeException {
	
	private String entityAsString;

	private static final long serialVersionUID = -5891292871507404809L;

	/**
	 * Constructs a new exception with the specified detail message. The cause
	 * is not initialized, and may subsequently be initialized by a call to
	 * {@link #initCause}.
	 *
	 * @param message
	 *            the detail message. The detail message is saved for later
	 *            retrieval by the {@link #getMessage()} method.
	 */
	public EntityExistsException(String message, String entityAsString) {
		super(message);
		this.entityAsString = entityAsString;
	}
	
	public String getEntityAsString() {
		return entityAsString;
	}

}
