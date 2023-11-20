package io.chainoperator;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.function.Function;

class ChainOperatorTest {

    @Test
    void chainOneToMany() {

        Mono<Integer> input = Mono.just(5);

        Function<Integer, Mono<?>> process1 = data -> Mono.just(data * data);
        Function<Integer, Mono<?>> process2 = data -> Mono.just(data + data);

        Flux<?> result = ChainOperator.chainOneToMany(input, process1, process2);

        StepVerifier.create(result)
                .expectNextMatches(item -> item.equals(25))
                .expectNextMatches(item -> item.equals(10))
                .verifyComplete();

    }
}