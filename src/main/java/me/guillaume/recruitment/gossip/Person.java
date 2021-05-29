package me.guillaume.recruitment.gossip;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Person {

    private static final String HONORIFIC_TO_REMEMBER = "Dr";

    private final String honorific;
    private final String name;

    private final List<String> toSay = new ArrayList<>();

    public Person(String nameWithHonorific) {
        final String[] nameWithHonorificSplit = nameWithHonorific.split(" ");
        if (nameWithHonorificSplit.length != 2) {
            throw new IllegalArgumentException(String.format("Invalid name with honorific provided: %s", nameWithHonorific));
        }

        this.honorific = nameWithHonorificSplit[0];
        this.name = nameWithHonorificSplit[1];
    }

    public String getHonorific() {
        return honorific;
    }

    public String getName() {
        return name;
    }

    void say(String toSay) {
        if (Objects.equals(honorific, HONORIFIC_TO_REMEMBER)) {
            this.toSay.add(toSay);
        } else {
            this.toSay.clear();
            this.toSay.add(toSay);
        }
    }

    void resetOrRemember() {
        if (Objects.equals(honorific, HONORIFIC_TO_REMEMBER)) {
            // do the remembering
        } else toSay.clear();
    }

    public String getSay() {
        if (toSay.isEmpty()) return "";
        return toSay.size() == 1 ? toSay.get(0) : toSay.toString().replace("[","").replace("]","");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(honorific, person.honorific) && Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(honorific, name);
    }
}
