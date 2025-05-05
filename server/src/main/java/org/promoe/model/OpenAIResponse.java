package org.promoe.model;


public class OpenAIResponse {
    String id;
    String object;
    java.util.List<Choice> choices;

    public java.util.List<Choice> getChoices() {
        return choices;
    }
}
