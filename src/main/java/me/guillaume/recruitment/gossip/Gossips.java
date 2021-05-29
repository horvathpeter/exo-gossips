package me.guillaume.recruitment.gossip;

import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class Gossips {

    private final Map<String, Node> nodes;

    public Gossips(String... names) {
        if (names == null || names.length == 0) {
            throw new IllegalArgumentException("Cannot create Gossips with no people");
        }

        this.nodes = Arrays.stream(names)
                .map(Node::new)
                .collect(toMap(
                        Node::getName,
                        Function.identity(),
                        (x, y) -> {
                            throw new IllegalStateException(String.format("Duplicate key %s", x));
                        },
                        LinkedHashMap::new)); // to maintain order
    }

    public ConnectionBuilder from(String name) {
        final Node from = getNodeByName(name);
        return new ConnectionBuilder(from, this);
    }

    public GossipBuilder say(String gossip) {
        if (gossip == null || gossip.trim().isEmpty()) {
            throw new IllegalArgumentException(String.format("Invalid gossip to say: %s", gossip));
        }
        return new GossipBuilder(gossip, this);
    }

    public String ask(String name) {
        return getNodeByName(name).printState();
    }

    public Node getNodeByName(String name) {
        return Optional.ofNullable(nodes.get(name))
                .orElseThrow(() -> new IllegalArgumentException(String.format("Non existing name: %s", name)));
    }

    public void spread() {
        // we need to collect because we will be changing state, which would affect the Stream execution
        final List<Node> nodesEligibleToPassState =
                nodes.values().stream()
                        .filter(Node::isEligibleToPassState)
                        .collect(toList());

        nodesEligibleToPassState
                .forEach(node -> new GossipExecutor(node).passGossipToSuccessor());

        nodes.values()
                .forEach(node -> node.setRecentlyChanged(false));
    }

}
