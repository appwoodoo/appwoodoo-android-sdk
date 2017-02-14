package com.appwoodoo.sdk.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.appcompat.BuildConfig;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.appwoodoo.sdk.storage.SharedPreferencesHelper;

import java.net.URL;

public class DialogFragment extends android.support.v4.app.DialogFragment {

	private com.appwoodoo.sdk.model.Dialog dialogData = new com.appwoodoo.sdk.model.Dialog();

	public void setDialogData(com.appwoodoo.sdk.model.Dialog dialogData) {
		this.dialogData = dialogData;
	}

	@NonNull
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        Typeface typeface = DialogsHelper.getInstance().getViewOptions().getDialogTypeFace();

        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setBackgroundColor(DialogsHelper.getInstance().getViewOptions().getDialogPanelBackgroundColour());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setPadding(0, 0, 0, 0);
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        if (dialogData.getBodyText() != null && !"".contentEquals(dialogData.getBodyText())) {
            TextView bodyText = new TextView(getContext());
            LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            titleParams.setMargins(35, 10, 35, 15);
            bodyText.setLayoutParams(titleParams);
            bodyText.setPadding(5, 5, 5, 5);
            bodyText.setGravity(Gravity.LEFT);
            bodyText.setTextSize(16);
            bodyText.setTextColor( DialogsHelper.getInstance().getViewOptions().getDialogTextColour() );
            if (typeface != null) {
                bodyText.setTypeface(typeface);
            }
            bodyText.setText(dialogData.getBodyText());
            linearLayout.addView(bodyText);
        }

        if (dialogData.getBodyImage() != null) {
            final ImageView bodyImageView = new ImageView(getContext());
            LinearLayout.LayoutParams imageLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            imageLayoutParams.setMargins(0, 0, 0, 0);
            bodyImageView.setLayoutParams(imageLayoutParams);
            bodyImageView.setPadding(0, 0, 0, 0);
            bodyImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            linearLayout.addView(bodyImageView);

            final ProgressBar progressView = new ProgressBar(getContext());
            LinearLayout.LayoutParams progressLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 50);
            progressLayoutParams.setMargins(10, 10, 0, 10);
            progressView.setLayoutParams(progressLayoutParams);
            bodyImageView.setPadding(0, 0, 0, 0);
            progressView.setIndeterminate(true);
            progressView.getIndeterminateDrawable().setColorFilter(DialogsHelper.getInstance().getViewOptions().getDialogPanelForegroundColour(), PorterDuff.Mode.MULTIPLY);
            linearLayout.addView(progressView);

            progressView.setVisibility(View.VISIBLE);

	    	AsyncTask<Void, Void, Drawable> at = new AsyncTask<Void, Void, Drawable>() {
				@Override
				protected Drawable doInBackground(Void... params) {
					try {
						URL url = new URL(dialogData.getBodyImage());
                        return Drawable.createFromStream(url.openStream(), "src");
					} catch (Exception e) {
						if (BuildConfig.DEBUG) {
							e.printStackTrace();
						}
					}
					return null;
				}
				@Override
				protected void onPostExecute(Drawable drawable) {
					try {
						progressView.setVisibility(View.GONE);
						bodyImageView.setImageDrawable(drawable);
					} catch(Exception e) {
						if (BuildConfig.DEBUG) {
							e.printStackTrace();
						}
					}
				}
	    	};
	    	at.execute((Void) null);
        }

        builder.setView(linearLayout);

        if (dialogData.getTitle() != null) {
            LinearLayout titleLayout = new LinearLayout(getContext());
            LinearLayout.LayoutParams titleLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            titleLayout.setLayoutParams(titleLayoutParams);
            linearLayout.setPadding(0, 0, 0, 0);
            titleLayout.setOrientation(LinearLayout.VERTICAL);

            TextView titleText = new TextView(getContext());
            LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            titleText.setLayoutParams(titleParams);
            titleParams.setMargins(35, 20, 35, 15);
            titleText.setPadding(5, 5, 5, 5);
            titleText.setGravity(Gravity.LEFT);
            titleText.setTextSize(26);
            titleText.setTextColor( DialogsHelper.getInstance().getViewOptions().getDialogTitleColour() );
            if (typeface != null) {
                titleText.setTypeface(typeface);
            }
            titleText.setText(dialogData.getTitle());

            titleLayout.addView(titleText);

            builder.setCustomTitle(titleLayout);
	    }

		SharedPreferences preferences = SharedPreferencesHelper.getInstance().getSharedPreferences(getContext());
		final SharedPreferences.Editor edit = preferences.edit();

	    if (dialogData.getActionButtonTitle() != null && dialogData.getActionButtonUrl() != null) {
	    	builder.setPositiveButton(dialogData.getActionButtonTitle(), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int which) {
					try {
						edit.putBoolean(DialogsHelper.APW_SP_DIALOG_ALREADY_OPENED_PREFIX + dialogData.getObjectid(), true);
						edit.apply();

						Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse( dialogData.getActionButtonUrl() ));
						startActivity(intent);

					} catch(Exception e) {
						if (BuildConfig.DEBUG) {
							e.printStackTrace();
						}
					}
				}
			});
	    }

        if (dialogData.getCloseButtonTitle() != null) {
            builder.setNegativeButton(dialogData.getCloseButtonTitle(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    edit.putBoolean(DialogsHelper.APW_SP_DIALOG_ALREADY_OPENED_PREFIX + dialogData.getObjectid(), true);
                    edit.apply();

                    DialogFragment.this.getDialog().cancel();
                }
            });
        }

        Dialog returnDialog = builder.create();

        ColorDrawable cd = new ColorDrawable( DialogsHelper.getInstance().getViewOptions().getDialogPanelBackgroundColour() );
        Window window = returnDialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(cd);
        }

        return returnDialog;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (dialogData.getActionButtonTitle() != null && dialogData.getActionButtonUrl() != null) {
            Button positiveButton = ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setTextColor( DialogsHelper.getInstance().getViewOptions().getDialogPanelForegroundColour() );
        }

        if (dialogData.getCloseButtonTitle() != null) {
            Button negativeButton = ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_NEGATIVE);
            negativeButton.setTextColor( DialogsHelper.getInstance().getViewOptions().getDialogPanelForegroundColour() );
        }
    }

    @Override
    public void show(FragmentManager manager, String tag) {
    	// If dialogData already exists, close it first (in case it needs an update)
    	if (manager.findFragmentByTag(tag) != null) {
    		DialogFragment fragment = (DialogFragment) manager.findFragmentByTag(tag);
    		fragment.getDialog().cancel();
    	}
    	super.show(manager, tag);
    }
    
}
