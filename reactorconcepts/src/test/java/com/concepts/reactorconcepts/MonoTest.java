package com.concepts.reactorconcepts;


import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/*
    Reactive streams
    1- Asynchronous
    2- Non-blocking
    3- Backpressure
    ***************
    Publisher
    Subscription
    Subscriber
    Processor
 */

class MonoTest {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MonoTest.class);

    @Test
    void shouldReturnAStringWhenStartedTest(){
        log.info("Testando aplicação!");
        System.out.println();
        System.out.println("----------------------------");
        System.out.println();
    }

    @Test
    void monoSubscriber(){
        String name = "Bruno Monteiro";
        Mono<String> mono = Mono.just(name)
                        .log();

        mono.subscribe();

        log.info("Mono {}", mono);
        log.info("testando");

        StepVerifier.create(mono)
                        .expectNext(name)
                                .verifyComplete();

        System.out.println("---------------------");
        System.out.println();
    }

    @Test
    void monoSubscribreConsumer(){
        String name = "Bruno Monteiro";
        Mono<String> mono = Mono.just(name)
                .log();

        mono.subscribe(s -> log.info("Value {}", s));
        log.info("-------------------------------");

        StepVerifier.create(mono)
                .expectNext(name)
                .verifyComplete();
    }

    @Test
    void monoSubscribreConsumerError(){
        String name = "Bruno Monteiro";
        Mono<String> mono = Mono.just(name)
                .map(s -> {throw new RuntimeException("Testing mono with error");});

        mono.subscribe(s -> log.info("Name {}", s), s -> log.error("algo ruím aconteceu"));
        mono.subscribe(s -> log.info("Name {}", s), Throwable::printStackTrace);
        log.info("-------------------------------");

        StepVerifier.create(mono)
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void monoSubscriberConsumerComplete(){
        String name = "Bruno Monteiro";

        Mono<String> mono = Mono.just(name)
                .log()
                .map(String::toUpperCase);

        mono.subscribe(s -> log.info("Value {}",s),
                Throwable::printStackTrace,
                () -> log.info("FINISHED!"));

        log.info("-------------------------------");

        StepVerifier.create(mono)
                .expectNext(name.toUpperCase())
                .verifyComplete();
    }
}
