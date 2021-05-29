package me.guillaume.recruitment.gossip;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

public class Gossips {

    private final Map<String, Node> nodes;

    public Gossips(String... people) {
        if (people == null || people.length == 0) {
            throw new IllegalArgumentException("Cannot create Gossips with no people");
        }

        this.nodes = Arrays.stream(people)
                .map(Node::new)
                .collect(toMap(
                        Node::getName,
                        Function.identity(),
                        (x, y) -> {
                            throw new IllegalStateException(String.format("Duplicate key %s", x));
                        },
                        LinkedHashMap::new));
    }

    // from should return a NodeBuilder which holds reference to Gossips, where will be a to() method, which will
    // say with set the state of a Node
    // when you call spread() you ask to pass the state to the successor node, it resets its own state
    // use LinkedHashMap to preserve the order, indexed by name, value is Node and it holds a field of Node type as successor (optional)

    public NodeBuilder from(String name) {
        final Node from = nodes.get(name); // assert not null
        return new NodeBuilder(from, this);
    }

    public SayingBuilder say(String saying) {
        if (saying == null || saying.trim().isEmpty()) {
            throw new IllegalArgumentException(String.format("Invalid gossip to say: %s", saying));
        }
        return new SayingBuilder(saying, this);
    }

    public String ask(String name) {
        return getNodeByName(name).getState().toString();
    }

    public Node getNodeByName(String name) {
        return Optional.ofNullable(nodes.get(name))
                .orElseThrow(() -> new IllegalArgumentException(name));
    }

    public void spread() {
        nodes.values().forEach(x -> x.setRecentlyChanged(false));

        List<Node> nodesNonEmpty =
                nodes.values().stream()
                        .filter(Node::isEligibleToPassState)
                        .collect(Collectors.toList());

        nodesNonEmpty.forEach(Node::passStateToSuccessorIfPresent);
    }

}
