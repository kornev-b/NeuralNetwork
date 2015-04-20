package me.neuralnetwork.core.exception;

/**
 * Base exception type for Neuroph.
 * @author jheaton
 */
public class NeuroException extends RuntimeException {
	public NeuroException() {
		
	}

	public NeuroException(final String msg) {
		super(msg);
	}

	public NeuroException(final Throwable t) {
		super(t);
	}

	public NeuroException(final String msg, final Throwable t) {
		super(msg, t);
	}
}
