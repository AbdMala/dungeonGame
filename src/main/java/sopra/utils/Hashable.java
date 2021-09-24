package sopra.utils;

/**
 * Enforces, that required methods are overridden by implementation.
 *
 */
public interface Hashable {

  @Override
  int hashCode();

  @Override
  boolean equals(Object obj);
}
