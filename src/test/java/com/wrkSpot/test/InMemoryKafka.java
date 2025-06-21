package com.wrkSpot.test;

import io.smallrye.reactive.messaging.memory.InMemoryConnector;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

import java.util.HashMap;
import java.util.Map;

public class InMemoryKafka implements
        QuarkusTestResourceLifecycleManager {


    @Override
    public Map<String, String> start() {


        Map<String, String> env = new HashMap<>();
        env.putAll(InMemoryConnector.switchOutgoingChannelsToInMemory(
                "customer-outbound"));
        return env;
    }

    @Override
    public void stop() {

    }
}
