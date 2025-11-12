package org.howard.edu.lsp.assignment6;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * JUnit 5 tests for {@link IntegerSet}.
 * Each public method has at least one test, with edge cases covered.
 */
public class IntegerSetTest {

  @Test
  void testAddAndLengthAndToString() {
    IntegerSet s = new IntegerSet();
    assertTrue(s.isEmpty());
    s.add(3);
    s.add(1);
    s.add(2);
    s.add(2); // duplicate ignored
    assertEquals(3, s.length());
    // sorted, bracketed format
    assertEquals("[1, 2, 3]", s.toString());
  }

  @Test
  void testClearAndIsEmpty() {
    IntegerSet s = new IntegerSet();
    s.add(10);
    s.add(20);
    assertFalse(s.isEmpty());
    s.clear();
    assertTrue(s.isEmpty());
    assertEquals(0, s.length());
    assertEquals("[]", s.toString());
  }

  @Test
  void testContains() {
    IntegerSet s = new IntegerSet();
    s.add(5);
    assertTrue(s.contains(5));
    assertFalse(s.contains(6));
  }

  @Test
  void testLargestNormalAndException() {
    IntegerSet s = new IntegerSet();
    s.add(-2);
    s.add(10);
    s.add(7);
    assertEquals(10, s.largest());

    IntegerSet empty = new IntegerSet();
    assertThrows(IllegalStateException.class, empty::largest);
  }

  @Test
  void testSmallestNormalAndException() {
    IntegerSet s = new IntegerSet();
    s.add(4);
    s.add(-9);
    s.add(0);
    assertEquals(-9, s.smallest());

    IntegerSet empty = new IntegerSet();
    assertThrows(IllegalStateException.class, empty::smallest);
  }

  @Test
  void testRemoveNoThrow() {
    IntegerSet s = new IntegerSet();
    s.add(1);
    s.add(2);
    s.add(3);
    s.remove(2);
    assertEquals("[1, 3]", s.toString());
    // remove absent should be a no-op
    s.remove(99);
    assertEquals("[1, 3]", s.toString());
  }

  @Test
  void testUnionBasicAndSelf() {
    IntegerSet a = new IntegerSet();
    a.add(1); a.add(2);

    IntegerSet b = new IntegerSet();
    b.add(2); b.add(3);

    a.union(b);  // modifies a
    assertEquals("[1, 2, 3]", a.toString());
    // union with self should be idempotent
    a.union(a);
    assertEquals("[1, 2, 3]", a.toString());
  }

  @Test
  void testIntersectBasicAndDisjoint() {
    IntegerSet a = new IntegerSet();
    a.add(1); a.add(2); a.add(3);

    IntegerSet b = new IntegerSet();
    b.add(2); b.add(4);

    a.intersect(b); // modifies a
    assertEquals("[2]", a.toString());

    IntegerSet c = new IntegerSet();
    c.add(10);
    IntegerSet d = new IntegerSet();
    d.add(20);
    c.intersect(d);
    assertTrue(c.isEmpty());
    assertEquals("[]", c.toString());
  }

  @Test
  void testDiffBasicAndSelf() {
    IntegerSet a = new IntegerSet();
    a.add(1); a.add(2); a.add(3);

    IntegerSet b = new IntegerSet();
    b.add(2); b.add(4);

    a.diff(b); // removes 2
    assertEquals("[1, 3]", a.toString());

    a.diff(a); // diff with self -> empty
    assertTrue(a.isEmpty());
  }

  @Test
  void testComplement() {
    IntegerSet a = new IntegerSet();
    a.add(1); a.add(3);

    IntegerSet b = new IntegerSet();
    b.add(1); b.add(2); b.add(3); b.add(4);

    // complement: a becomes (b \ a) = {2,4}
    a.complement(b);
    assertEquals("[2, 4]", a.toString());

    // complement with empty 'other'
    IntegerSet x = new IntegerSet();
    IntegerSet y = new IntegerSet();
    y.add(5);
    // (y \ x) = {5}
    x.complement(y);
    assertEquals("[5]", x.toString());
  }

  @Test
  void testEqualsOrderIndependentAndNotEquals() {
    IntegerSet a = new IntegerSet();
    a.add(1); a.add(2); a.add(3);

    IntegerSet b = new IntegerSet();
    b.add(3); b.add(2); b.add(1);

    assertTrue(a.equals(b));
    assertTrue(b.equals(a));
    assertTrue(a.equals(a));
    assertFalse(a.equals(null));
    assertFalse(a.equals("not a set"));

    b.remove(1);
    assertFalse(a.equals(b));
  }

    private void assertFalse(boolean empty) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}