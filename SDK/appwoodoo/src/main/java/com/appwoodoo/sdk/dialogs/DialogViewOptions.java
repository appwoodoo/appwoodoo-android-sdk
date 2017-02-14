package com.appwoodoo.sdk.dialogs;

import android.graphics.Color;
import android.graphics.Typeface;

public class DialogViewOptions {

    private int dialogPanelBackgroundColour = Color.WHITE;
    private int dialogPanelForegroundColour = Color.LTGRAY;
    private int dialogTitleColour = Color.BLACK;
    private int dialogTextColour = Color.DKGRAY;
    private Typeface dialogTypeFace;

    public int getDialogPanelBackgroundColour() {
        return dialogPanelBackgroundColour;
    }

    public void setDialogPanelBackgroundColour(String hexColour) {
        this.dialogPanelBackgroundColour = Color.parseColor(hexColour);
    }

    public int getDialogPanelForegroundColour() {
        return dialogPanelForegroundColour;
    }

    public void setDialogPanelForegroundColour(String hexColour) {
        this.dialogPanelForegroundColour = Color.parseColor(hexColour);
    }

    public int getDialogTitleColour() {
        return dialogTitleColour;
    }

    public void setDialogTitleColour(String hexColour) {
        this.dialogTitleColour = Color.parseColor(hexColour);
    }

    public int getDialogTextColour() {
        return dialogTextColour;
    }

    public void setDialogTextColour(String hexColour) {
        this.dialogTextColour = Color.parseColor(hexColour);
    }

    public Typeface getDialogTypeFace() {
        return dialogTypeFace;
    }

    public void setDialogTypeFace(Typeface dialogTypeFace) {
        this.dialogTypeFace = dialogTypeFace;
    }

}
