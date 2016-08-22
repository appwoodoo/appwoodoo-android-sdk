package com.appwoodoo.sdk.stories;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.LayoutDirection;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.appwoodoo.sdk.model.Story;
import com.appwoodoo.sdk.model.StoryWall;

public class StoryActivity extends AppCompatActivity {

    public final static String STORY_ID_KEY = "STORY_ID";

    private WebView webView;
    private ProgressBar progressView;
    private Story story;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(createView());

        if (getIntent() != null && getIntent().getExtras() != null) {
            int storyID = getIntent().getExtras().getInt(STORY_ID_KEY);
            story = StoriesHelper.getInstance().getStoryById(storyID, getApplicationContext());
        }

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setElevation(0);

            StoryWall storyWall = StoriesHelper.getInstance().getStoryWall(getApplicationContext());
            if (storyWall != null) {
                actionBar.setTitle(storyWall.getTitle());
            }
        }

        if (webView != null) {
            WebViewClient webViewClient = new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    // An external link should be passed on to whoever wants to handle it
                    if (url.startsWith("external-")) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url.replaceFirst("external-", "")));
                        startActivity(intent);
                        return true;
                    }
                    return false;
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    progressView.setVisibility(View.GONE);
                    webView.setVisibility(View.VISIBLE);
                }
            };

            webView.setWebViewClient(webViewClient);
            webView.getSettings().setJavaScriptEnabled(true);

            webView.setScrollbarFadingEnabled(true);
            webView.setScrollBarStyle(WebView.SCROLLBARS_INSIDE_OVERLAY);
        }

        loadWebView();
    }

    private View createView() {
        RelativeLayout relativeLayout = new RelativeLayout(this);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        relativeLayout.setLayoutParams(params);

        webView = new WebView(this);
        RelativeLayout.LayoutParams webViewParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        webView.setLayoutParams(webViewParams);
        relativeLayout.addView(webView);

        progressView = new ProgressBar(this);
        progressView.setVisibility(View.VISIBLE);

        int backgroundColor = StoriesHelper.getInstance().getViewOptions().getStoryWallBackgroundColour();
        int foregroundColor = StoriesHelper.getInstance().getViewOptions().getStoryWallForegroundColour();
        webView.setBackgroundColor(backgroundColor);

        RelativeLayout.LayoutParams progressViewParams = new RelativeLayout.LayoutParams(40, 40);
        progressViewParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        progressView.setLayoutParams(progressViewParams);
        progressView.setIndeterminate(true);
        progressView.getIndeterminateDrawable().setColorFilter(foregroundColor, PorterDuff.Mode.MULTIPLY);
        relativeLayout.addView(progressView);

        return relativeLayout;
    }

    private void loadWebView() {
        progressView.setVisibility(View.VISIBLE);
        webView.setVisibility(View.GONE);

        if (story != null) {
            webView.loadUrl( story.getUrl() );
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
