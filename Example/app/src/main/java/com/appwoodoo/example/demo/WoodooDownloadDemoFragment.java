package com.appwoodoo.example.demo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.appwoodoo.example.R;
import com.appwoodoo.sdk.Woodoo;
import com.appwoodoo.sdk.WoodooDelegate;
import com.appwoodoo.sdk.WoodooDialogDelegate;
import com.appwoodoo.sdk.model.Dialog;

import org.w3c.dom.Text;

/**
 * On this fragment, you can re-request the latest data from
 * the Appwoodoo service.
 *
 *   1. Add settings on www.appwoodoo.com
 *   2. Run this test app
 *   3. Put your API key in the settings and go!
 *
 * Created by wimagguc on 15/02/2017.
 */

public class WoodooDownloadDemoFragment extends Fragment implements WoodooDelegate, WoodooDialogDelegate {

    private TextView debug;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_appwoodoo, container, false);

        debug = (TextView) rootView.findViewById(R.id.debug);

        TextView textSection1 = (TextView) rootView.findViewById(R.id.textSection1);
        TextView textSection2 = (TextView) rootView.findViewById(R.id.textSection2);
        TextView textSection3 = (TextView) rootView.findViewById(R.id.textSection3);

        Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(), "roboto/RobotoBlack.ttf");
        textSection1.setTypeface(typeFace);
        textSection2.setTypeface(typeFace);
        textSection3.setTypeface(typeFace);

        textSection2.setText(Html.fromHtml( (String) getResources().getText(R.string.home_text_section2) ));
        textSection2.setMovementMethod(LinkMovementMethod.getInstance());
        textSection3.setText(Html.fromHtml( (String) getResources().getText(R.string.home_text_section3) ));
        textSection3.setMovementMethod(LinkMovementMethod.getInstance());

        downloadSettings();

        return rootView;
    }

    // Getting the Woodoo, preparing the UI
    public void downloadSettings() {

        String apiKey = "AAT";
        String gcmId = "";

        SharedPreferences preferences = getActivity().getSharedPreferences("WoodooSharedPreferences", Activity.MODE_PRIVATE);
        if (preferences != null) {
            String savedApiKey = preferences.getString("SavedApiKey", "");
            String savedGcmId = preferences.getString("SavedGcmId", "");

            if (!"".contentEquals(savedApiKey)) {
                apiKey = savedApiKey;
            }
            if (!"".contentEquals(savedGcmId )) {
                gcmId = savedGcmId;
            }
        }
        // ^^^

        Activity activity = getActivity();

        debug.setText("Woodoo taking off...");

        // WOODOO TAKEOFF >>>
        Woodoo.takeOffWithCallback(apiKey, this);

        // ADD THE PUSH NOTIFICATIONS TO THIS APP >>>
        String notificationTitle = activity.getResources().getString(R.string.app_name);
        if (!"".contentEquals(gcmId)) {
            Woodoo.pushNotifications().setupPushNotification(getActivity(), gcmId, notificationTitle, R.drawable.ic_push_notification);
            Woodoo.pushNotifications().setupPushNotificationSound(getActivity(), R.raw.notification_sound);
        }
        Woodoo.pushNotifications().removeAll(getActivity());
        // ^^^

        Typeface titleTypeFace = Typeface.createFromAsset(getActivity().getAssets(), "roboto/RobotoBlack.ttf");
        Typeface bodyTypeFace = Typeface.createFromAsset(getActivity().getAssets(), "roboto/RobotoLight.ttf");

        // ADD THE STORIES TO THIS APP >>>
        Woodoo.stories().init(getActivity());
        Woodoo.stories().getViewOptions().setStoryWallBackgroundColour("#ffffff");
        Woodoo.stories().getViewOptions().setStoryWallCellDateColour("#999999");
        Woodoo.stories().getViewOptions().setStoryWallCellDividerColour("#CCCCCC");
        Woodoo.stories().getViewOptions().setStoryWallCellTextColour("#000000");
        Woodoo.stories().getViewOptions().setStoryWallCellTitleColour("#000000");
        Woodoo.stories().getViewOptions().setStoryWallForegroundColour("#CCCCCC");
        Woodoo.stories().getViewOptions().setStoryWallCellHeight(240);
        Woodoo.stories().getViewOptions().setStoryWallTitleTypeface(titleTypeFace);
        Woodoo.stories().getViewOptions().setStoryWallCellTypeface(bodyTypeFace);
        // ^^^

        // ADD THE DIALOGS TO THIS APP >>>
        Woodoo.dialogs().initWithCallback(getActivity(), this);
        Woodoo.dialogs().getViewOptions().setDialogPanelBackgroundColour("#F1F1F1");
        Woodoo.dialogs().getViewOptions().setDialogPanelForegroundColour("#0310A0");
        Woodoo.dialogs().getViewOptions().setDialogTextColour("#999999");
        Woodoo.dialogs().getViewOptions().setDialogTitleColour("#0310A0");
        Woodoo.dialogs().getViewOptions().setDialogTypeFace(bodyTypeFace);
        // ^^^

    }

    @Override
    public void woodooArrived(Woodoo.WoodooStatus status) {
        switch (status) {
            case SUCCESS:
                debug.setText("Woodoo arrived");
                break;
            case ERROR:
                debug.setText("Woodoo error");
                break;
            case NETWORK_ERROR:
                debug.setText("Woodoo network error");
                break;
        }
    }

    @Override
    public void woodooDialogArrived(Woodoo.WoodooStatus status) {
        switch (status) {
            case SUCCESS:
                debug.setText("Dialogs arrived");
                break;
            case ERROR:
                debug.setText("Dialog error");
                break;
        }

    }

}
