package me.guillaume.recruitment.gossip;

class ConnectionBuilder {

    private final Gossips gossips;
    private final Node from;

    ConnectionBuilder(Node from, Gossips gossips) {
        this.gossips = gossips;
        this.from = from;
    }

    Gossips to(String name) {
        from.setSuccessor(gossips.getNodeByName(name));
        return gossips;
    }

}