# GEAR: Generic Engine for Analogical Reasoning
This is a formal analogy engine able to perform **verification** and **solving** operations on multiple types of objects.

This code is a rewriting of [ilar](https://github.com/vletard/analogy-lua), taking advantage of Java types and structures to get a clearer, better maintainable project.

Note that work is still in progress, the system still expects more features, documentation and useful primitives.

## Built-in types

We describe here the list of types upon which analogies can be computed.
For each one, the developer can choose to use the built-in type from the GEAR library,
or to subclass it if needed with any additional functionalities or fields.
In the latter please refer to the "subtype reconstruction" section below.

### Sequence
This type represents lists of any type of items.
When an analogy exists between four sequences, it can be decomposed into the factorization of subsequences of its items.
A factorization is an alignment of alternating subsequence pairings as illustrated below.

The analogical equation `baa : aba :: aab : aab` can be decomposed into the following factorizations (among others):

    0    |ba|a|  
    1   a|ba| |  
    2    |  |a|ab
    3   a|  | |ab
    
    0     |b|a| |a
    1     | |a|b|a
    2   aa|b| | | 
    3   aa| | |b| 

The notion of degree defined on factorizations can be a hint of the quality (or intelligibility) of the analogy, the lower degree being the better (simpler).
The degree is the total number of factors (unit alignment of a pair of subsequences) in a factorization.
Here, factors are separated by vertical bars.

The first factorization has a degree of 4, and the second one has a degree of 5.

For homogeneity reasons and because of the recursion feature, the notion of degree has been extended to every type used by GEAR in an analogy.

### Tuple

A tuple is comparable to a map with keys of any type associated to items of a specified type.
An analogy between tuples is processed as a set of analogies of items, one analogy per key.

The degree of an analogy between four tuples is the maximum degree of the set of their item analogies.

### Set

Analogies are also defined on sets (named ImmutableSet in GEAR, to avoid class name collision). 
They are analogous to analogies between tuples which keys are the items in the sets and values are booleans denoting whether the item is included.

    [1, 2] : [1, 3, 5] :: [0, 2, 4] : [0, 3, 4, 5]

### Anything else

Analogies between quadruplets of any other type or heterogeneous types are processed as atomic analogies.
In atomic analogies, items are only tested for equality (equals method).
Only two atomic analogies exist:

    itemA : itemA :: itemB : itemB
    
    itemA : itemB :: itemA : itemB

## Subtype reconstruction

While in simple situations, developers can use predefined Sequence, Tuple and ImmutableSet from the GEAR library,
more complex cases may require subclassing those.
However, when an analogical equation is computed over subtypes, the solution can only be generated as the corresponding base type.
To address this issue, SubtypeRebuilders can be submitted to the analogical equation constructors in order to construct solutions
based on the subtype instead.

Note that only fields of the subtype that can be inferred from fields of its corresponding base type can be successfully filled,
since nothing else will be taken into account by the analogical equation solving process.
