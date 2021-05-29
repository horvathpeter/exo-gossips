package me.guillaume.recruitment.gossip;

public class SayingBuilder {

    private final Gossips gossips;
    private final String saying;

    public SayingBuilder(String saying, Gossips gossips) {
        this.gossips = gossips;
        this.saying = saying;
    }

    public void to(String name) {
        final Node to = gossips.getNodeByName(name);

        to.addToState(saying);
    }
}
