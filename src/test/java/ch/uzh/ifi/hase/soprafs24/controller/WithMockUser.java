package ch.uzh.ifi.hase.soprafs24.controller;

public @interface WithMockUser {

    String username();

    String[] roles();

    long userId();

}
