# CS 445 Assignment 4: Hashing Lookup

## Motivation

Consider the `Lookup` data structure you implemented in Assignment 1. You may
have noticed that this data structure is fairly inefficient, as the items are
completely unorganized. This causes many methods to include sequential search
(in the worst case, considering each element).

Recall from lecture that a hash table can be used to locate items more quickly
on average. In this assignment, you will build a new implementation of the
Lookup ADT that stores its elements in a hash table organized by `id`. Use each
`id`’s `hashCode()` method to get its (unrestricted) hash code, then use modular
hashing to turn this into a hash value within your prime table size. You can use
an array of pairs **or** parallel arrays arranged based on hashing `id`s.

> **Note:** Your goal in this assignment is to write a class that faithfully
implements the ADT described in the interface. Sample client code is provided as
*one example* of how the class may be used. This sample client can be run using
`./gradlew run` (or `gradlew.bat run` on DOS-like terminals). In addition, unit
tests are available for *a few methods* that can be run using `./gradlew test`
(or `gradlew.bat test` on DOS-like terminals). However, the sample client and
unit test **do not test all of the capabilities you are asked to implement**,
and thus your goal is *not* simply to make this provided code work. You are
*strongly encouraged* to write your own test client as well, to further test
that all operations of your class work in all the corner cases described in the
interface.

## Provided code

First, look over the provided code in this repository. If you created it
correctly on GitHub Classroom, it should be a private repository named
`cs445-a1-abc123`, where `abc123` is your GitHub username.

The starting code for this assignment, as usual, is found in subdirectory
`app/src/main/java/`. Some files are within the `cs445/a4/` package folder
within it. Note the following provided Java files:

- The `LookupInterface<I, C>` interface (in the `cs445.a4` package) describes a
  lookup, a collection of unique identifiers, each of which is associated with a
  content object. An identifier can thus be used to “look up” its content
  object. This interface is slightly modified from the version provided in
  Assignment 1 (it no longer references `FullLookupException`). You are *not*
  permitted to modify this interface—doing so will cause all grading tests to
  fail!

- The `Primes` class (in the `cs445.a4` package) includes methods to help you
  check if a number is prime, find the smallest prime no less than a lower
  bound, or find the largest prime no greater than an upper bound.

- The `IdentifierNotFoundException` class (in the `cs445.a4` package) is
  included as a way of telling the client that a specified identifier was not
  found. It should be used as described in the interface. You are *not*
  permitted to modify this class.

- The `PhoneLookupExample` class (which is *not* in the `cs445.a4` package)
  shows one example usage of the `HashingLookup` data structure. It demonstrates
  a few features, but is not a thorough test. It can be run using `./gradlew
  run` (or `gradlew.bat run` on DOS-like terminals).

- The `PhoneNumber` class (which is *not* in the `cs445.a4` package) is a simple
  way of representing a phone number. This class contains a constructor and a
  `toString` method. It is used in `PhoneLookupExample`. It has also been
  extended to include `equals` and `hashCode` methods to allow you to write
  tests using `PhoneNumber` as an identifier type. You are *not* permitted to
  modify this class.

In addition to the starter code, we have also provided an example test class
that can be found in subdirectory `app/src/test/java/`. The test is in the
`cs445/a4/test/` folder within it. Note the following provided Java file:

- The `SampleTest` class contains example test cases for a few of the methods;
  it tests a subset of the functionality for the constructors, `getSize()`,
  `add(I, C)`, and `getAllIdentifiers()`. This **does not** test all required
  functionality of these methods, and does not test the other methods at all. It
  can be run using `./gradlew test` (or `gradlew.bat test` on DOS-like
  terminals). It is provided to give you some feedback on your progress, but you
  are still responsible for implementing additional tests to ensure your code
  works as specified in the provided documentation.

## Tasks

### Implement HashingLookup

Develop the generic class, `HashingLookup<I, C>`, a **dynamic-capacity
hash-table-based** implementation of the Lookup ADT described in
`LookupInterface<I, C>`. Include this class in package `cs445.a4` (and thus
place the `HashingLookup.java` file in subdirectory
`app/src/main/java/cs445/a4/`). Read the comments in the interface (and this
README!) carefully to ensure you implement it properly; it will be graded using
tests that assume *all* of the functionality described in the
`LookupInterface<I, C>` and here, not just the behavior you need to execute
`PhoneLookupExample` or run `SampleTest`.

You are not permitted to use *any* external code when developing your
`HashingLookup` class, but you may use the code written in lecture and the
textbook as examples. Do not use any code you find online, even for ideas. Do
not import or use any Java Class Library classes (except `java.util.Arrays` for
resizing, if you’d like).

In your implementation, you may use two arrays, one of identifiers (organized
using hashing) and one of content objects, associated via position (i.e.,
*parallel arrays*). Alternatively, you can write a `Pair` class and use a single
array of `Pair` objects, organized via hashing each pair’s identifier.

You must use **linear probing** as your collision resolution mechanism. Whenever
the load factor is over 0.5, the array(s) *must* resize, using the techniques
discussed in lecture.

When implementing removal, you have two options:

- *Remove eagerly*: Re-hash the rest of the cluster, to ensure all items can
  still be accessed. If you use this approach, you must **only** rehash the
  potentially-affected items (those to the right within the cluster). **Do not**
  rehash the entire table.
- *Remove lazily*: Replace the removed item with a placeholder/“tombstone”. If
  you use this approach, you must **include** these placeholders when
  calculating the load factor. That is, you should resize when more than half
  the table is filled, **including placeholders**. The `getSize()` method must
  still reflect the number of actual pairs (i.e., excluding placeholders).

You must include a constructor `public HashingLookup(int capacity)` that
initializes the array(s) to *smallest prime greater than or equal to the
specified initial capacity*. For example, if the requested capacity is 17, the
actual initial capacity should be 17. If the given capacity is 20, the actual
initial capacity should be 23. If the specified capacity is less than 5, simply
throw `IllegalArgumentException`, as a hash table smaller than this is not
practical. You must also include a constructor `public HashingLookup()` that
uses a reasonable default (prime) initial capacity.

### Testing

`PhoneLookupExample` is provided as an example client of the `HashingLookup`
class. `SampleTest` is provided with unit tests for a subset of the
functionality of a few methods. These **do not** exhaustively test the
functionality of the data structure you must write. You are responsible for
ensuring your implementation works properly in all cases, even those not tested
by these classes, and that it follows the ADT described in the provided
interface. Thus, it is **highly recommended** that you write additional test
client code to verify your implementation’s behavior in *all* of the corner
cases described in the interface.

Note that the gradle project for this assignment is set up to execute assert
statements; if you'd like, you may use assert statements to help you debug your
implementation.

> **Note:** For functionality that cannot be tested (e.g., methods that crash or
> cannot be compiled), **no points will be awarded**. At this level, turning in
> code that crashes or does not compile is not acceptable and will not yield
> success. We suggest writing and testing one method at a time (while leaving
> the others as non-functional stubs), to maximize the amount of functionality
> you can show in your submission.

## Submission

Put your `HashingLookup` class in the appropriate location in your GitHub
repository. Commit and push your changes to this private repository, created for
you when you accepted the assignment on GitHub Classroom. **Double check that
your submission appears on the GitHub web interface after pushing.** If you
accidentally overwrite the provided interface or exception classes, remember to
restore them to their original versions. You may not make changes to these
files. I recommend that you push your changes as often as possible (at a
minimum, every time you stop working).

Once you are satisfied with your submission, you can submit it on Gradescope for
grading. You can access Gradescope from the Canvas sidebar. Select our course,
then the correct assignment. Connect your GitHub account to Gradescope, and then
specify which repository (i.e., `cs445-a4-abc123`, where `abc123` is your GitHub
username) and which branch (most likely `main`, unless you change it) you want
graded. You will receive an email confirmation if your submission was received
on Gradescope.

Your submission will be tested and graded via a *very* exhaustive set of unit
tests using Gradle, so you must make sure your code runs properly with this
build system. If you use an IDE to develop your program (not recommended), you
must export the java files from the IDE and ensure that they compile and run in
our standard way. Do not submit any IDE project files. The sample client should
run when you execute `./gradlew run` (or `gradlew.bat run` on DOS-like
terminals). The test cases should run when you execute `./gradlew test` (or
`gradlew.bat test` on DOS-like terminals). In addition to the automated testing,
your code will be reviewed to make sure it follows the requirements. Very steep
penalties (including potentially 0 on the assignment) will be given for using
external code, importing Java Class Library classes, implementing
`HashingLookup` in a way beside a hash table with linear probing, etc.

Your project is due at the precise date and time stated on Canvas. You should
commit and push your progress frequently, even far in advance of this deadline:
**No late submissions will be accepted.**

## Grading

As stated above, your submission will be tested and graded via a *very*
exhaustive set of unit tests using Gradle and reviewed to make sure it follows
the requirements. Be sure to review these instructions, and the details of
`LookupInterface`, carefully when making your implementation. It is **highly
recommended** that you write additional test client code to verify your
implementation’s behavior in *all* of the corner cases described in the
interface.

| **Method**                     | **Points**
| ----------                     | ----------
| `HashingLookup()`              | 6
| `HashingLookup(int)`           | 6
| `int getSize()`                | 6
| `boolean isEmpty()`            | 6
| `boolean add(I, C)`            | 10
| `C replace(I, C)`              | 8
| `C get(I)`                     | 10
| `C remove(I)`                  | 10
| `Object[] remove()`            | 10
| `void clear()`                 | 8
| `boolean contains(I)`          | 8
| `Object[] getAllIdentifiers()` | 12

