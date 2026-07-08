# Expected Results - `test/`

This small dataset exists so you can verify your reconciliation logic against a known answer before running the larger `data/` set. If your numbers don't match these, your matching or fee math is off somewhere.

**Input:** 12 internal transactions, 13 settlement records.

## Reconciliation summary

| Outcome                                            | Count |
| -------------------------------------------------- | ----- |
| Cleanly matched (6 sales + 2 refunds)              | 8     |
| Unmatched - internal (in ledger, never settled)    | 1     |
| Unmatched - settlement (settled, no ledger record) | 1     |
| Amount mismatch (principal off beyond rounding)    | 1     |
| Fee discrepancy (fees deviate from schedule)       | 1     |
| Duplicate settlement (one payment settled twice)   | 1     |

Notes:

- The duplicate produces **two** settlement rows for **one** internal transaction - which is why there are 13 settlement records but only 8 clean matches plus the breaks.
- The **fee discrepancy** row is deliberately tricky: its `settled_amount` is internally consistent with the fees the processor _reported_, so a check that only compares `settled_amount` against `gross − reported_fees` will miss it. You have to compare the reported fees against the **published schedule** (`fee_schedule.json`) to catch it.
- Each **refund** references a real originating sale: its `merchant_ref` matches an existing `SALE` already in the ledger, so both refunds resolve to a prior order (that order's ref carries two internal rows and two settlement rows). Matching must therefore be type/sign aware - pair the negative refund settlement with the `REFUND` row, not the sale's positive settlement.
- This set is deliberately clean of the harder categories: **0 orphan refunds, 0 split (partial) settlements, 0 wide-window timing cases, and 0 malformed rows**. Those all live only in `data/`.
- Every transaction here is in the normal settlement window.
