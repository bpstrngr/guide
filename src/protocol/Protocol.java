package protocol;

import java.io.*;
import java.net.*;
import java.util.Random;

/**
* Communication rules on a socket.
* */
class Protocol {

    /**
    * Conversation logic
    * */
    public enum Story {
        TURNIP("Turnip the heat, it's cold in here!"),
        LITTLEOLDLADY("I didn't know you could yodel!"),
        ATCH("Bless you!"),
        WHO("Is there an owl in here?"),
        Who("Is there an echo in here?");
        public String joke;
        private Story(String joke) {
            this.joke = joke;
        }
        private static Story reset() {
            return Story.values()[new Random().nextInt(Story.values().length)];
        }
    }
    public static Story story = Story.reset();
    public Story getStory() {
        return story;
    }

    /**
     * Response conditions
     * */
    public enum State {
        VOCATIVE("Hi, how can I help?", null),
        RESPONSIVE(story.name().toLowerCase(), "Who's there?"),
        REACTIVE(story.joke, story.name().toLowerCase() + " who?"),
        SUGGESTIVE("Want another? (y)", null),
        QUITTING("Bye!", "n");
        public String diction;
        public String ignorecase;
        private State(String diction, String ignorecase) {
            this.diction = diction;
            this.ignorecase = ignorecase;
        }
        public State next() {
            if (this.ordinal() < State.values().length) {
                return State.values()[this.ordinal() + 1];
            } else {
                story = Story.reset();
                return State.VOCATIVE;
            }
        }
    }
    public static State state = State.values()[0];
    public State getState() {
        return state;
    }

    public String processRequest(String request) {
        String response = null;
        if (state.ignorecase == null || request.equalsIgnoreCase(state.ignorecase)) {
            response = state.diction;
            state = state.next();
            if(state.ignorecase == null){
                processRequest(null);
            }
        } else {
            response = "You're supposed to say \"" + state.ignorecase + "\"! I'll start over.";
            state = State.VOCATIVE;
        }
        return response;
    }
}