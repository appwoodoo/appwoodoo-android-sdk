package com.appwoodoo.sdk.dialogs;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.appwoodoo.sdk.BuildConfig;
import com.appwoodoo.sdk.WoodooDialogDelegate;
import com.appwoodoo.sdk.io.DialogsApiHandler;
import com.appwoodoo.sdk.model.Dialog;
import com.appwoodoo.sdk.state.State;
import com.appwoodoo.sdk.storage.SharedPreferencesHelper;

import java.util.ArrayList;

public class DialogsHelper {

    final static String APW_SP_DIALOG_ALREADY_OPENED_PREFIX = "APW_ALREADY_OPENED_";

    private DialogViewOptions dialogViewOptions;

    private static DialogFragment dialogFragment;
    private static DialogsHelper _instance;

    private DialogsHelper() {}

    public static DialogsHelper getInstance() {
        synchronized(DialogsHelper.class) {
            if (_instance == null) {
                _instance = new DialogsHelper();
            }
        }
        return _instance;
    }

    public void init(Context context) {
        State.getInstance().setPackageName(context);
        SharedPreferences sp = SharedPreferencesHelper.getInstance().getSharedPreferences(context);
        DialogsApiHandler.getDialogData(sp, null);
    }

    public void initWithCallback(Context context, WoodooDialogDelegate delegate) {
        SharedPreferences sp = SharedPreferencesHelper.getInstance().getSharedPreferences(context);
        DialogsApiHandler.getDialogData(sp, delegate);
    }

    public ArrayList<Dialog> getDialogs(Context context) {
        SharedPreferences sp = SharedPreferencesHelper.getInstance().getSharedPreferences(context);
        String storedString = SharedPreferencesHelper.getInstance().getStoredString(sp, DialogsApiHandler.KEY_DIALOGWALL_DATA);
        return Dialog.parseJSON(storedString);
    }

    public Dialog getDialogById(String shorthand, Context context) {
        if (shorthand == null) {
            return null;
        }
        ArrayList<Dialog> dialogs = getDialogs(context);
        if (dialogs != null) {
            for (Dialog d : dialogs) {
                if (shorthand.equals(d.getName())) {
                    return d;
                }
            }
        }
        return null;
    }

    public DialogFragment getFragment(Dialog dialog) {
        if (dialogFragment == null) {
            dialogFragment = new DialogFragment();
        }
        dialogFragment.setDialogData(dialog);
        return dialogFragment;
    }

    public DialogViewOptions getViewOptions() {
        if (dialogViewOptions != null) {
            return dialogViewOptions;
        }
        dialogViewOptions = new DialogViewOptions();
        return dialogViewOptions;
    }

    public void showFirst(Context context, FragmentManager fragmentManager, boolean onlyOnce) {
        ArrayList<Dialog> dialogs = getDialogs(context);
        if (dialogs != null && dialogs.size() > 0) {
            show(context, fragmentManager, dialogs.get(0), onlyOnce);
        } else {
            if (BuildConfig.DEBUG) {
                Log.d("APPWOODOO", "Dialogs: none to display");
            }
        }
    }

    public void show(Context context, FragmentManager fragmentManager, Dialog dialog, boolean onlyOnce) {

        // Don't open the same dialog twice
        if (onlyOnce) {
            SharedPreferences sp = SharedPreferencesHelper.getInstance().getSharedPreferences(context);
            boolean alreadyClosed= sp.getBoolean(APW_SP_DIALOG_ALREADY_OPENED_PREFIX + dialog.getObjectid(), false);
            if (alreadyClosed) {
                return;
            }
        }

        try {
            dialogFragment = getFragment(dialog);
            dialogFragment.show(fragmentManager, "appwoodoo_dialog");

        } catch(Exception e) {
            // If app enters background for any reason
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            }
        }

    }

}
