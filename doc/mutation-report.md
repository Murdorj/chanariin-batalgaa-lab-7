# Mutation Testing Report

## Overview
- `mutation.id` JVM property controls which mutant is active (default: none). Multiple mutants can be combined by passing a comma-separated list, e.g. `-Dmutation.id=MUT1,MUT2`.
- All mutants compile and live inside production code behind the runtime toggle implemented in `MutationConfig`.
- Baseline (`mvn test`) passes, confirming that the default behavior remains intact.

## Mutants implemented

| Mutant ID | Category | Location | Description |
|-----------|----------|----------|-------------|
| `MUTANT_REL_BOUNDARY` | Relational boundary | `Calendar.checkTimes` | Treats `start == end` as valid by mutating the `>=` guard into a strict `>` check. |
| `MUTANT_SWALLOW_ROOM_CONFLICT` | Exception handling / Statement deletion | `Room.addMeeting` | Swallows the `TimeConflictException` when an overlap occurs, leaving callers unaware of the conflict. |
| `MUTANT_NEGATE_PERSON_BUSY` | Logical negation | `Person.isBusy` | Returns the inverse of the calendar's `isBusy` result, flipping busy/free outcomes. |

These mutants touch different classes and use different operator categories, satisfying the lab constraint.

## Execution results

| Command | Outcome | Test(s) that killed the mutant |
|---------|---------|--------------------------------|
| `mvn test` | ✅ All tests passed (baseline) | – |
| `mvn -Dmutation.id=MUTANT_REL_BOUNDARY test` | ❌ `CalendarTest.testInvalidTimeStartAfterEnd` failed because the expected exception was not thrown. |
| `mvn -Dmutation.id=MUTANT_SWALLOW_ROOM_CONFLICT test` | ❌ `RoomTest.testAddOverlap_shouldThrowConflictMessage` failed because the conflict was silently ignored. |
| `mvn -Dmutation.id=MUTANT_NEGATE_PERSON_BUSY test` | ❌ `PersonTest.testAddMeetingAndIsBusy` failed because the calendar busy check was inverted. |

All injected mutants were killed by the previously written unit tests, so the current suite demonstrated adequate strength for these fault categories.
