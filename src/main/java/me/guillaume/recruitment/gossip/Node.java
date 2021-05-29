package me.guillaume.recruitment.gossip;

import java.util.*;

class Node {

    private static final String HONORIFIC_DR = "Dr";
    private static final String HONORIFIC_AGENT = "Agent";
    private static final String HONORIFIC_PROFESSOR = "Pr";
    private static final String HONORIFIC_LADY = "Lady";
    private static final String HONORIFIC_SIR = "Sir";

    private final String honorific;
    private final String name;
    private final State state = new State();

    private Node successor;
    private boolean recentlyChanged;
    private boolean delayOneTurn;

    Node(String nameWithHonorific) {
        final String[] nameWithHonorificSplit = nameWithHonorific.split(" ");
        if (nameWithHonorificSplit.length != 2) {
            throw new IllegalArgumentException(String.format("Invalid name with honorific provided: %s", nameWithHonorific));
        }

        this.honorific = nameWithHonorificSplit[0];
        this.name = nameWithHonorificSplit[1];
        this.delayOneTurn = isProfessor();
    }

    String getName() {
        return name;
    }

    String printState() {
        return state.toString();
    }

    void addToState(String gossip, GossipType gossipType) {
        if (isDoctor() || isAgent()) {
            state.addGossipToState(gossip, gossipType);
        } else {
            state.clearAndAddToState(gossip, gossipType);
        }
    }

    void clear() {
        state.clear();
    }

    String getGossipToPass() {
        return isGentleman() ? reverseGossip(state.getGossipToPass()) : state.getGossipToPass();
    }

    boolean isRecentlyChanged() {
        return recentlyChanged;
    }

    void setRecentlyChanged(boolean recentlyChanged) {
        this.recentlyChanged = recentlyChanged;
    }

    boolean shouldDelayOneTurn() {
        return delayOneTurn;
    }

    void setDelayOneTurn(@SuppressWarnings("SameParameterValue") boolean delayOneTurn) {
        this.delayOneTurn = delayOneTurn;
    }

    boolean isDoctor() {
        return Objects.equals(honorific, HONORIFIC_DR);
    }

    boolean isAgent() {
        return Objects.equals(honorific, HONORIFIC_AGENT);
    }

    boolean isProfessor() {
        return Objects.equals(honorific, HONORIFIC_PROFESSOR);
    }

    boolean isLady() {
        return Objects.equals(honorific, HONORIFIC_LADY);
    }

    boolean isGentleman() {
        return Objects.equals(honorific, HONORIFIC_SIR);
    }

    boolean isEligibleToPassState() {
        return !state.gossipsToPass.isEmpty() && successor != null;
    }

    Node getSuccessor() {
        return successor;
    }

    void setSuccessor(Node successor) {
        this.successor = successor;
    }

    private String reverseGossip(String gossip) {
        final StringBuilder stringBuilder = new StringBuilder(gossip);
        stringBuilder.reverse();
        return stringBuilder.toString();
    }

    enum GossipType {
        KeepAndPass,
        KeepOnly
    }

    private static class State {
        private final List<String> gossipsToKeep = new ArrayList<>();
        private final Queue<String> gossipsToPass = new ArrayDeque<>();

        private void clear() {
            gossipsToKeep.clear();
            gossipsToPass.clear();
        }

        private String getGossipToPass() {
            return gossipsToPass.poll();
        }

        private void addGossipToState(String gossip, GossipType gossipType) {
            gossipsToKeep.add(gossip); // always keep

            switch (gossipType) {
                case KeepAndPass:
                    gossipsToPass.add(gossip);
                    break;
                case KeepOnly:
                    // do nothing
                    break;
                default:
                    throw new UnsupportedOperationException(gossipType.toString());
            }
        }

        private void clearAndAddToState(String gossip, GossipType gossipType) {
            clear();
            addGossipToState(gossip, gossipType);
        }

        @Override
        public String toString() {
            if (gossipsToKeep.isEmpty()) return "";
            return gossipsToKeep.size() == 1 ? gossipsToKeep.get(0) : gossipsToKeep.toString().replace("[", "").replace("]", "");
        }
    }
}
