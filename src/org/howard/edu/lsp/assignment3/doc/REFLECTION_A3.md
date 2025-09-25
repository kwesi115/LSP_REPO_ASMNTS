# Reflection: Assignment 2 vs Assignment 3

## What changed in the design?

**A2** used a single class with a procedural `main` that performed extract, transform, and load in place.  
**A3** decomposes responsibilities into multiple classes and interfaces:

- `Extractor<T>`, `Loader<T>`, and `Transformer<T>` define roles.
- `CsvProductExtractor`, `CsvProductLoader`, and `ProductTransformer` implement those roles.
- `ETLPipeline` orchestrates steps and prints a run summary.
- `Product` and `PriceRange` encapsulate domain state and derived values.

This separation improved readability and testability. Each class now has a narrow purpose and can be swapped independently (e.g., a JSON extractor later).

## How is A3 more object-oriented?

- **Objects & Classes:** Domain entity `Product` holds state; `PriceRange` models a finite domain as an enum.
- **Encapsulation:** `Product` hides fields behind getters/setters; transformation updates state via methods.
- **Inheritance/Interfaces:** Polymorphic interfaces (`Extractor`, `Transformer`, `Loader`) allow plugging in alternative implementations without changing the pipeline.
- **Polymorphism:** `ETLPipeline` is written against interfaces; it doesn’t care if we use CSV, JSON, or DB-backed components.

## Which OO ideas did I use?

- **Object/Class:** `Product` and `PriceRange`.
- **Encapsulation:** Private fields with accessor methods; `toCsvRow()` centralizes output formatting.
- **Inheritance/Polymorphism:** Interfaces with multiple implementations enable late binding and easier testing.

## Testing parity with A2

I verified identical behavior using the same three cases:

1. **Normal Input** → Output matched the golden `transformed_products_normal.csv`.
2. **Empty Input (header only)** → Output file contains only the header.
3. **Missing Input** → Clear error message and exit; no output written.
   Additionally tested a malformed row (bad integer) to confirm it’s skipped and counters report correctly.

## Tradeoffs & Notes

- The OO design adds a few files but reduces coupling and improves clarity.
- The transformation order is enforced in `ProductTransformer` and documented in Javadocs to avoid regressions.
- The runner (`ETLRunner`) preserves A2’s exact CLI behavior and relative paths
