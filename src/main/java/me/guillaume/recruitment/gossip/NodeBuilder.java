package me.guillaume.recruitment.gossip;

public class NodeBuilder {

    private final Gossips gossips;
    private final Node from;

    public NodeBuilder(Node from, Gossips gossips) {
        this.gossips = gossips;
        this.from = from;
    }

    public Gossips to(String name) {
        final Node to = gossips.getNodeByName(name);

        from.setSuccessor(to);

        return gossips;
    }

}