package com.worddocparser;

/**
 * Represents a heading in a Word document.
 * Contains the heading text and its level (1-9).
 */
public class Heading {
    private final String text;
    private final int level;

    /**
     * Creates a new heading with the specified text and level.
     *
     * @param text  The heading text
     * @param level The heading level (1-9)
     */
    public Heading(String text, int level) {
        this.text = text;
        this.level = level;
    }

    /**
     * Gets the heading text.
     *
     * @return The heading text
     */
    public String getText() {
        return text;
    }

    /**
     * Gets the heading level.
     *
     * @return The heading level (1-9)
     */
    public int getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return "Heading{" +
                "level=" + level +
                ", text='" + text + '\'' +
                '}';
    }
}