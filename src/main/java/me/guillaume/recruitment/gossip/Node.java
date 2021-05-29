package me.guillaume.recruitment.gossip;

import java.util.*;

import static me.guillaume.recruitment.gossip.Node.AddType.KeepAndPass;
import static me.guillaume.recruitment.gossip.Node.AddType.KeepOnly;

public class Node {

    private static final String HONORIFIC_DR = "Dr";
    private static final String HONORIFIC_AGENT = "Agent";
    private static final String HONORIFIC_PROFESSOR = "Pr";
    private static final String HONORIFIC_LADY = "Lady";
    private static final String HONORIFIC_SIR = "Sir";

    private final String honorific;
    private final String name;
    private final State state = new State();
    private boolean shouldDelay;

    private Node successor;
    private boolean recentlyChanged = false;

    public Node(String nameWithHonorific) {
        final String[] nameWithHonorificSplit = nameWithHonorific.split(" ");
        if (nameWithHonorificSplit.length != 2) {
            throw new IllegalArgumentException(String.format("Invalid name with honorific provided: %s", nameWithHonorific));
        }

        this.honorific = nameWithHonorificSplit[0];
        this.name = nameWithHonorificSplit[1];
        this.shouldDelay = isProfessor(this);
    }

    public String getName() {
        return name;
    }

    public void setSuccessor(Node successor) {
        this.successor = successor;
    }

    public State getState() {
        return state;
    }

    public void addToState(String state, AddType addType) {
        this.state.addToState(state, addType);
    }

    public void clearAndAddToState(String state, AddType addType) {
        this.state.clearAndAddToState(state, addType);
    }

    public void setRecentlyChanged(boolean recentlyChanged) {
        this.recentlyChanged = recentlyChanged;
    }

    public void passStateToSuccessorIfPresent() {
        if (successor.recentlyChanged) {
            return;
        }

        if (isAgent(this)) {
            state.clear();
            return;
        }

        if (shouldDelay && isProfessor(this)) {
            shouldDelay = false;
            return;
        }

        final AddType addType = findAddType();
        String stateToPass = state.getStateToPass();
        if (isGentleman(this)) {
            StringBuilder stringBuilder = new StringBuilder(stateToPass);
            stringBuilder.reverse();
            stateToPass = stringBuilder.toString();
        }


        if (!shouldDelay && (isDoctor(successor) || isAgent(successor))) {
            successor.addToState(stateToPass, addType);
        } else {
            successor.clearAndAddToState(stateToPass, addType);
        }

        if (!isAgent(successor)) {
            successor.setRecentlyChanged(true);
        }

        if (!isDoctor(this)) {
            state.clear();
        }

        if (isGentleman(successor)) {
            successor.successor = this;
        }
    }

    private AddType findAddType() {
        if (isLady(successor)) {
            if (isDoctor(this)) {
                return KeepAndPass;
            } else
                return KeepOnly;
        }

        return KeepAndPass;
    }

    private boolean isDoctor(Node node) {
        return Objects.equals(node.honorific, HONORIFIC_DR);
    }

    private boolean isAgent(Node node) {
        return Objects.equals(node.honorific, HONORIFIC_AGENT);
    }

    private boolean isProfessor(Node node) {
        return Objects.equals(node.honorific, HONORIFIC_PROFESSOR);
    }

    private boolean isLady(Node node) {
        return Objects.equals(node.honorific, HONORIFIC_LADY);
    }

    private boolean isGentleman(Node node) {
        return Objects.equals(node.honorific, HONORIFIC_SIR);
    }

    public boolean isEligibleToPassState() {
        return !state.sayingsToPass.isEmpty() && successor != null;
    }

    enum AddType {
        KeepAndPass,
        KeepOnly
    }

    static class State {
        private final List<String> sayingsToKeep = new ArrayList<>();
        private final Queue<String> sayingsToPass = new ArrayDeque<>();

        private void clear() {
            sayingsToKeep.clear();
            sayingsToPass.clear();
        }

        private String getStateToPass() {
            return sayingsToPass.poll();
        }

        private void addToState(String saying, AddType addType) {
            switch (addType) {
                case KeepAndPass:
                    sayingsToKeep.add(saying);
                    sayingsToPass.add(saying);
                    break;
                case KeepOnly:
                    sayingsToKeep.add(saying);
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        private void clearAndAddToState(String saying, AddType addType) {
            clear();
            addToState(saying, addType);
        }

        @Override
        public String toString() {
            if (sayingsToKeep.isEmpty()) return "";
            return sayingsToKeep.size() == 1 ? sayingsToKeep.get(0) : sayingsToKeep.toString().replace("[", "").replace("]", "");
        }
    }
}
