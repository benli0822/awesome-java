# Thinking in Java

## OOP

1. Everything is an object.
2. A Program is bunch of objects telling each other what to do by sending messages.
3. Each object has its own memory made up of other objects.
4. Every object has a type.
5. All objects of a particular type can receive the same messages.

An object has state, behavior and identity.

## An object has an interface

It's like a protocol that defines how to interact with the object.

## An object provides services

High cohesion, make the object reuseble, treating every object as a service provider.

## The hidden implementation

Without rules, everything's naked to the world.

Access control is necessary.

Treat the outside as a client of the object, leave the entry for them but keep the hands off portions they should not touch.

## Reusing the implementation

Composition technique.
If the compostion happens dynamically, it's usually called aggregation.
It represents a 'has-a' relationship, just like 'A car has an engine'.

Comppsition provides a more flexible ways regarding the inheritance.

## Inheritance

1. Original class = Base class = Superclass = Parent class
2. Derived class = Inherited class = Subclass = Child class

Any changes on item 1. will reflect on item 2.

Inheritance means a type, the same behavior pattern. Messages sent to base class can also be sent to derived class. The derived class should expect the same pattern behvoir by default inherited from it's parent class, unless overriding happens;

2 ways for differentiate a base class and its dervied class:

1. Simply add new methods in derived class.
2. Using overriding mecanism.

## Is-a vs. is-like-a relationships

Pure substitution
Substitution principal

By making an is-a statement, we translate the inheriance as 'A circle is a shape', which means circle is the derived class of base class shape.

By making an is-like-a statement, we translate the inheriance as 'The heat pump is like an air conditioner', however the heat pump is a new type of interface extends from the air conditioner. It can do more than the original interface.

## Interchangeable objects with polymorphism

