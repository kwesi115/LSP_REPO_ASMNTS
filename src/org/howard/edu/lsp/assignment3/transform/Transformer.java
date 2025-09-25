package org.howard.edu.lsp.assignment3.transform;

/**
 * Transforms a record of type T, returning the transformed instance.
 * 
 * @param <T> record type
 */
public interface Transformer<T> {
    /**
     * Applies in-place or copy-on-write transformations per business rules.
     * 
     * @param input a record to transform
     * @return transformed record (may be the same instance)
     */
    T transform(T input);
}