## App architecture

I refactored the initial codebase to be more modular according to **Clean Architecture** principles, while keeping the implementation lightweight and practical.
The main components are:
- **data**: Contains the data models and repositories for fetching and managing accounting records and transactions.
- **domain**: Contains the business logic and use cases for handling bank reconciliation tasks.
- **presentation**: Contains the UI components and state management for displaying the accounting records and transaction.
- **di**: Contains the dependency injection setup for managing dependencies across the app.
- **utils**: Contains utility functions and helpers for the application.

## Implementation enhancements:
- It is no problem to display a price from float, but when it comes to arithmetic operations, float can cause precision issues, so I converted monetary values to Long in cents for better accuracy in financial calculations.
- `isSelected = record.id in selectedRecordIds`.  Whether a record is selected or not should be controlled by the data, not by the UI itself. UI should only reflect the underlying state rather than owning the selection state itself. (**UDF principle**)
- Create models by layers, from data layer(e.g. AccountingRecord) to domain layer(e.g. MatchResult) to presentation layer(e.g. FindMatchUiState), and use mapping functions to convert between them. This way we can decouple the layers and make the code more maintainable and testable.
- The Repository and DataSource use interfaces to allow for easier testing with fake implementations.
- Replace the original Java POJO to Kotlin data classes for the AccountingRecord data model, which provides better readability, immutability and built-in functions such as `equals()`(used in RecyclerView diffing).
- In real life, the Accounting records data is typically fetched from a remote DB, so I add a delay(1000ms) and a loading spinner to simulate network latency.
- For each business logic use case, it's a good practice to encapsulate them in a **UseCase class**(Domain layer) for reusability and testing(see FindMatchUseCaseTest.kt), this is why I create the `FindMatchUseCase`
- The data layer SHOULD NOT be responsible for the UI display of the different types of records, so I created ModelMappingExt.kt to convert the record type to the display text

## Potential UX improvements:
- Add a **Deselect All** button to allow users to quickly clear their selections. (while "Select All" might not be necessary since it's unlikely that all records will match the transaction total)
- Add "**Sort by** Amount" and "Sort by Date" options to help users find matching records more easily.
- If **multiple match candidates** are found, prioritise the most simple and likely match first, for example fewer records and larger amount records. A "Next Match" button could allow the user to browse other possible combinations.


## Task 1
Please run the app and directly test the task 1 result

## Task 2
Please switch the target amount constant in FindMatchActivity.kt companion object block

## Task 3
Please switch the target amount constant in FindMatchActivity.kt companion object block
Please enable the Task 3 implementation in FindMatchUseCase.kt as indicated in the comments.

This is basically a subset-sum problem.
I use **backtracking + pruning + memoization** to solve it.
The idea is pretty simple:
Each record has 2 choices: pick it or skip it
For example: target = 100, records = [60, 40, 30]
First we try pick 60, then remaining = 40, then continue pick 40, remaining = 0, we found a match [60, 40]
Then **backtracking** happens: remove 40, remove 60, and continue searching other possible combinations.
This part is the code backtracking logic:
`
selected += record
dfs(...)
selected.removeAt(selected.lastIndex)
`
means: try current choice, search deeper, then undo the choice (backtrack) and try other choices.

**Pruning** is used to avoid impossible search branches.
Example: remaining = 40, current record = 60. Since 60 is already bigger than remaining amount, there is no point searching this branch further.
`if (record.amountInCents > remaining) {
    continue
}`
This can reduce a lot of unnecessary recursion.

I also added failed state **memoization**.
A state is: (startIndex, remaining) Example: (3, 25) means: starting from index 3, try to build remaining amount 25.
If we already searched this state before and found no valid result, next time we hit same state we can directly return.
`if (state in failedStates) {
    return
}`
This avoids repeated failed searching.

Worst case time complexity is still exponential:  **O(2^n)**, because finding all possible combinations may still need to search many subsets.
But in real reconciliation scenarios, records count is usually not very large, and pruning + failed state cache can reduce a lot of unnecessary searching.

Other possible approaches: Dynamic Programming (possibly more complex to implement), Pure brute-force(very inefficient)