package dev.omar_learning.postgres.mappers;

public interface Mapper<A, B> {

  B mapTo(A a);

  A mapFrom(B b);
}
