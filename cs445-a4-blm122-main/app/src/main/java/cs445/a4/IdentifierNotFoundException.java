package cs445.a4;

/**
 * An exception that is thrown when a lookup operation cannot be completed
 * because the specified identifier is not found.
 */
public class IdentifierNotFoundException extends RuntimeException {
    public IdentifierNotFoundException() { super(); }
    public IdentifierNotFoundException(String e) { super(e); }
}

