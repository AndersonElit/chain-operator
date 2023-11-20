package io.chainoperator;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.function.Function;

public class ChainOperator {

    @SafeVarargs
    public static <I> Flux<?> chainOneToMany(Mono<I> input, Function<I, ? extends Mono<?>>... processes) {
        Flux<Mono<?>> monoFlux = input.flatMapMany(data -> {
            Flux<Mono<?>> result = Flux.empty();
            for (Function<I, ? extends Mono<?>> process : processes) {
                result = result.concatWithValues(process.apply(data));
            }
            return result;
        });
        return Flux.concat(monoFlux);
    }

}
