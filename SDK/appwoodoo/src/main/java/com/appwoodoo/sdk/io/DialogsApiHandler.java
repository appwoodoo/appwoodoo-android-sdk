package com.appwoodoo.sdk.io;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.appwoodoo.sdk.BuildConfig;
import com.appwoodoo.sdk.Woodoo;
import com.appwoodoo.sdk.WoodooDialogDelegate;
import com.appwoodoo.sdk.model.ApiResponse;
import com.appwoodoo.sdk.model.Dialog;
import com.appwoodoo.sdk.state.Config;
import com.appwoodoo.sdk.state.State;
import com.appwoodoo.sdk.storage.SharedPreferencesHelper;

import java.io.IOException;
import java.util.ArrayList;

public class DialogsApiHandler {

	public static String KEY_DIALOGWALL_DATA = "WOODOO_DIALOG_DATA";

    public static void getDialogData(final SharedPreferences sp, final WoodooDialogDelegate delegate) {
        final String url = Config.API_ENDPOINT + "cms/objects/" + State.getInstance().getAppKey() + "/dialogs/";

        AsyncTask<Void, Void, String> bgTask = new AsyncTask<Void, Void, String>(){
            @Override
            protected String doInBackground(Void... arg) {
                try {
                    return HttpsClient.getInstance().doGetRequest(url);
                } catch (IOException e) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s != null && sp != null) {
                    ApiResponse ar = ApiResponse.parseJSON(s);
                    if (ar != null && ar.getStatus() != null && ar.getStatus() == 200) {
                        ArrayList<Dialog> dialogs = Dialog.parseJSON(s);
                        SharedPreferencesHelper.getInstance().storeValue(sp, KEY_DIALOGWALL_DATA, Dialog.getJsonString(dialogs));
                    }
                    if (delegate != null) {
                        delegate.woodooDialogArrived(Woodoo.WoodooStatus.SUCCESS);
                    }
                }
                else if (delegate != null) {
                    delegate.woodooDialogArrived(Woodoo.WoodooStatus.ERROR);
                }
            }
        };
        bgTask.execute((Void) null);
    }

}
