package com.appwoodoo.sdk.stories;

import android.graphics.Color;
import android.graphics.Typeface;

public class StoryViewOptions {

    private int storyWallBackgroundColour = Color.WHITE;
    private int storyWallForegroundColour = Color.LTGRAY;
    private int storyWallCellDividerColour = Color.LTGRAY;
    private int storyWallCellTitleColour = Color.BLACK;
    private int storyWallCellTextColour = Color.DKGRAY;
    private int storyWallCellDateColour = Color.LTGRAY;
    private Typeface storyWallTitleTypeface;
    private Typeface storyWallCellTypeface;
    private int storyWallCellHeight = 240;

    public int getStoryWallBackgroundColour() {
        return storyWallBackgroundColour;
    }

    public void setStoryWallBackgroundColour(String hexColour) {
        this.storyWallBackgroundColour = Color.parseColor(hexColour);
    }

    public int getStoryWallForegroundColour() {
        return storyWallForegroundColour;
    }

    public void setStoryWallForegroundColour(String hexColour) {
        this.storyWallForegroundColour = Color.parseColor(hexColour);
    }

    public int getStoryWallCellDividerColour() {
        return storyWallCellDividerColour;
    }

    public void setStoryWallCellDividerColour(String hexColour) {
        this.storyWallCellDividerColour = Color.parseColor(hexColour);
    }

    public int getStoryWallCellTitleColour() {
        return storyWallCellTitleColour;
    }

    public void setStoryWallCellTitleColour(String hexColour) {
        this.storyWallCellTitleColour = Color.parseColor(hexColour);
    }

    public int getStoryWallCellDateColour() {
        return storyWallCellDateColour;
    }

    public void setStoryWallCellDateColour(String hexColour) {
        this.storyWallCellDateColour = Color.parseColor(hexColour);
    }

    public int getStoryWallCellTextColour() {
        return storyWallCellTextColour;
    }

    public void setStoryWallCellTextColour(String hexColour) {
        this.storyWallCellTextColour = Color.parseColor(hexColour);
    }

    public Typeface getStoryWallTitleTypeface() {
        return storyWallTitleTypeface;
    }

    public void setStoryWallTitleTypeface(Typeface storyWallTitleTypeface) {
        this.storyWallTitleTypeface = storyWallTitleTypeface;
    }

    public Typeface getStoryWallCellTypeface() {
        return storyWallCellTypeface;
    }

    public void setStoryWallCellTypeface(Typeface storyWallCellTypeface) {
        this.storyWallCellTypeface = storyWallCellTypeface;
    }

    public int getStoryWallCellHeight() {
        return storyWallCellHeight;
    }

    public void setStoryWallCellHeight(int storyWallCellHeight) {
        this.storyWallCellHeight = storyWallCellHeight;
    }

}
