package me.guillaume.recruitment.gossip;

import static me.guillaume.recruitment.gossip.Node.GossipType.KeepAndPass;

class GossipBuilder {

    private final Gossips gossips;
    private final String gossip;

    GossipBuilder(String gossip, Gossips gossips) {
        this.gossips = gossips;
        this.gossip = gossip;
    }

    void to(String name) {
        final Node to = gossips.getNodeByName(name);
        to.addToState(gossip, KeepAndPass);
    }
}
