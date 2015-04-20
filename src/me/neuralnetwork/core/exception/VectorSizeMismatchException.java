package me.neuralnetwork.core.exception;

/**
 * Thrown to indicate that vector size does not match the network input or training element size.
 */
public class VectorSizeMismatchException extends NeuroException {

    public VectorSizeMismatchException() {
        super();
    }

    public VectorSizeMismatchException(String message) {
        super(message);
    }

    public VectorSizeMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public VectorSizeMismatchException(Throwable cause) {
        super(cause);
    }
}
