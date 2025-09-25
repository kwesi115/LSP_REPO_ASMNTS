# AI Prompts & Excerpts (Assignment 3)

## Prompt 1

**Q:** “Redesign my single-file Java ETL into an object-oriented structure. Keep the same behavior (inputs/outputs), but separate extract, transform, load. Suggest interfaces and classes.”

**Excerpt of AI Response:**  
“Create interfaces `Extractor<T>`, `Transformer<T>`, and `Loader<T>` and implement CSV versions. Add `Product` (entity) and `PriceRange` (enum). Use a coordinator `ETLPipeline` and an entry `ETLRunner` to assemble and run.”

**Adaptation:** I followed this structure and adjusted method signatures to return an `ExtractionResult` containing counters so my summary matches the rubric.

--

## Prompt 2

**Q:** “Help me implement transformation order: uppercase name → discount (Electronics) → recategorization → final price range with HALF_UP rounding.”

**Excerpt of AI Response:**  
“Compute discount only if original category equals ‘Electronics’. After discount and rounding to 2 decimals (HALF_UP), recategorize to ‘Premium Electronics’ if price > 500.00. Derive the price range from the final price.”

**Adaptation:** I encoded these steps in `ProductTransformer` with explicit constants and comments to avoid order mistakes.

---

## Prompt 3

**Q:** “What’s a minimal way to keep summaries (rows read/transformed/skipped) without mixing concerns?”

**Excerpt of AI Response:**  
“Have the extractor return a result object that includes parsed records and counters. Then the pipeline can print a summary after loading.”

**Adaptation:** I created `ExtractionResult<T>` and kept summary printing in `ETLPipeline`.

---

## Prompt 4

**Q:** “Show me how to preserve Assignment 2’s behavior for missing input and empty input.”

**Excerpt of AI Response:**  
“In your runner, check if `data/products.csv` exists. If not, print a clear error and exit. For empty files (header only), write the header to the output via the loader.”

**Adaptation:** Implemented in `ETLRunner` and `CsvProductLoader`.
