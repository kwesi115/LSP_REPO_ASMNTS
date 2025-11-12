package org.howard.edu.lsp.assignment6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * IntegerSet models a mathematical set of unique integers using an {@link ArrayList}.
 * <p>
 * - No duplicates are stored.<br>
 * - All mutator methods modify the current instance (this).<br>
 * - Order is not semantically meaningful; {@code equals} is order-independent.<br>
 * - {@code largest()} and {@code smallest()} throw {@link IllegalStateException} if the set is empty.
 */
public class IntegerSet  {
  private List<Integer> set = new ArrayList<>();

  /**
   * Clears the internal representation of the set.
   */
  public void clear() {
    set.clear();
  }

  /**
   * Returns the number of elements in the set.
   * @return number of elements
   */
  public int length() {
    return set.size();
  }

  /**
   * Two sets are equal if they contain exactly the same values in any order.
   * Overrides {@link Object#equals(Object)}.
   * @param o object to compare
   * @return true if contents are equal (order-independent), false otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof IntegerSet)) return false;
    IntegerSet other = (IntegerSet) o;
    // Order-independent comparison via HashSet
    Set<Integer> a = new HashSet<>(this.set);
    Set<Integer> b = new HashSet<>(other.set);
    return a.equals(b);
  }

  /**
   * Returns true if the set contains the value; false otherwise.
   * @param value integer value to check
   * @return true if present, false otherwise
   */
  public boolean contains(int value) {
    return set.contains(value);
  }

  /**
   * Returns the largest item in the set.
   * @return largest value
   * @throws IllegalStateException if the set is empty
   */
  public int largest()  {
    if (set.isEmpty()) {
      throw new IllegalStateException("largest() called on empty IntegerSet");
    }
    return Collections.max(set);
  }

  /**
   * Returns the smallest item in the set.
   * @return smallest value
   * @throws IllegalStateException if the set is empty
   */
  public int smallest()  {
    if (set.isEmpty()) {
      throw new IllegalStateException("smallest() called on empty IntegerSet");
    }
    return Collections.min(set);
  }

  /**
   * Adds an item to the set if not already present.
   * @param item value to add
   */
  public void add(int item) {
    if (!set.contains(item)) {
      set.add(item);
    }
  }

  /**
   * Removes an item from the set if present.
   * @param item value to remove
   */
  public void remove(int item) {
    // remove(Object) removes first match; if absent, no-op as required
    set.remove(Integer.valueOf(item));
  }

  /**
   * Set union: modifies this to contain all unique elements present
   * in either this or {@code other}.
   * @param other other set
   */
  public void union(IntegerSet other) {
    // Access allowed: private fields are visible across instances of the same class
    for (Integer v : other.set) {
      if (!this.set.contains(v)) {
        this.set.add(v);
      }
    }
  }

  /**
   * Set intersection: modifies this to contain only elements present in both sets.
   * @param other other set
   */
  public void intersect(IntegerSet other) {
    this.set.retainAll(other.set);
  }

  /**
   * Set difference (this \ other): removes from this any elements found in other.
   * @param other other set
   */
  public void diff(IntegerSet other) {
    this.set.removeAll(other.set);
  }

  /**
   * Set complement: modifies this to become (other \ this).
   * @param other other set
   */
  public void complement(IntegerSet other) {
    List<Integer> result = new ArrayList<>(other.set);
    result.removeAll(this.set);
    this.set.clear();
    this.set.addAll(result);
  }

  /**
   * Returns true if the set is empty; false otherwise.
   * @return emptiness flag
   */
  public boolean isEmpty() {
    return set.isEmpty();
  }

  /**
   * Returns a String representation in square brackets, e.g., {@code [1, 2, 3]}.
   * Overrides {@link Object#toString()}.
   * Order is not semantically meaningful, but the output is sorted for readability.
   * @return string representation
   */
  @Override
  public String toString() {
    if (set.isEmpty()) return "[]";
    List<Integer> copy = new ArrayList<>(set);
    Collections.sort(copy);
    StringBuilder sb = new StringBuilder();
    sb.append('[');
    for (int i = 0; i < copy.size(); i++) {
      if (i > 0) sb.append(", ");
      sb.append(copy.get(i));
    }
    sb.append(']');
    return sb.toString();
  }
}
