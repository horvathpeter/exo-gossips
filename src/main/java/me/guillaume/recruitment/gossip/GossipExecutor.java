package me.guillaume.recruitment.gossip;

import me.guillaume.recruitment.gossip.Node.GossipType;

import static me.guillaume.recruitment.gossip.Node.GossipType.KeepAndPass;
import static me.guillaume.recruitment.gossip.Node.GossipType.KeepOnly;

class GossipExecutor {

    private final Node gossiper;
    private final Node successor;

    GossipExecutor(Node gossiper) {
        this.gossiper = gossiper;
        this.successor = gossiper.getSuccessor();
    }

    void passGossipToSuccessor() {
        // should be retained if recipient has already a gossip
        if (successor.isRecentlyChanged()) {
            return;
        }

        // should be stopped by Agent
        if (gossiper.isAgent()) {
            gossiper.clear();
            return;
        }

        // should be delayed une turn by Professor
        if (gossiper.shouldDelayOneTurn()) {
            gossiper.setDelayOneTurn(false);
            return;
        }

        // pass the gossip
        successor.addToState(gossiper.getGossipToPass(), findGossipType());

        // should always be listened by Agent
        if (!successor.isAgent()) {
            successor.setRecentlyChanged(true);
        }

        // should be remembered by Doctor
        if (!gossiper.isDoctor()) {
            gossiper.clear();
        }

        // should be returned by Gentlemen
        if (successor.isGentleman()) {
            successor.setSuccessor(gossiper);
        }
    }

    private GossipType findGossipType() {
        // should be propagated By lady when coming from Doctor, otherwise not
        if (successor.isLady()) {
            return gossiper.isDoctor() ? KeepAndPass : KeepOnly;
        }
        return KeepAndPass;
    }
}
