package me.guillaume.recruitment.gossip;

import java.util.*;

public class Node {

    private static final String HONORIFIC_DR = "Dr";
    private static final String HONORIFIC_AGENT = "Agent";
    private static final String HONORIFIC_PROFESSOR = "Pr";

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

    public String getHonorific() {
        return honorific;
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

    public void addToState(String state) {
        this.state.addToState(state);
    }

    public void clearAndAddToState(String state) {
        this.state.clearAndAddToState(state);
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

        if (!shouldDelay && (isDoctor(successor) || isAgent(successor))) {
            successor.addToState(state.getStateToPass());
        } else {
            successor.clearAndAddToState(state.getStateToPass());
        }

        if (!isAgent(successor)) {
            successor.setRecentlyChanged(true);
        }

        if (!isDoctor(this)) {
            state.clear();
        }
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

    public boolean isEligibleToPassState() {
        return !Objects.equals(state.toString(), "") && successor != null;
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

        private void addToState(String saying) {
            sayingsToKeep.add(saying);
            sayingsToPass.add(saying);
        }

        private void clearAndAddToState(String saying) {
            clear();
            addToState(saying);
        }

        @Override
        public String toString() {
            if (sayingsToKeep.isEmpty()) return "";
            return sayingsToKeep.size() == 1 ? sayingsToKeep.get(0) : sayingsToKeep.toString().replace("[", "").replace("]", "");
        }
    }
}
