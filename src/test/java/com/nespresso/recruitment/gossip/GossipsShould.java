package com.nespresso.recruitment.gossip;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GossipsShould {

    // a Mr spread every gossip
    @Test
    public void talk_To_Mister() {

        Gossips gossips = new Gossips("Mr White", "Mr Black", "Mr Blue")
                .from("White").to("Black")
                .from("Black").to("Blue");

        gossips.say("Hello").to("White");

        gossips.spread();

        assertThat(gossips.ask("Blue")).isEqualTo("Hello");

    }


}
