package com.brentvatne.exoplayer;

import android.app.Dialog;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.google.android.exoplayer2.ui.PlayerControlView;

public class FullScreenPlayerView extends Dialog {
  private final PlayerControlView playerControlView;
  private final ExoPlayerView exoPlayerView;
  private ViewGroup parent;
  private final FrameLayout containerView;

  public FullScreenPlayerView(Context context, ExoPlayerView exoPlayerView, PlayerControlView playerControlView) {
    super(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    this.playerControlView = playerControlView;
    this.exoPlayerView = exoPlayerView;
    containerView = new FrameLayout(context);
    setContentView(containerView, generateDefaultLayoutParams());
  }

  @Override
  protected void onStart() {
    parent = (FrameLayout)(exoPlayerView.getParent());

    parent.removeView(exoPlayerView);
    containerView.addView(exoPlayerView, generateDefaultLayoutParams());

    if (playerControlView != null) {
      ImageButton imageButton = playerControlView.findViewById(com.brentvatne.react.R.id.exo_fullscreen);
      imageButton.setImageResource(com.google.android.exoplayer2.ui.R.drawable.exo_icon_fullscreen_exit);
      imageButton.setContentDescription(getContext().getString(com.google.android.exoplayer2.ui.R.string.exo_controls_fullscreen_exit_description));
      parent.removeView(playerControlView);
      containerView.addView(playerControlView, generateDefaultLayoutParams());
    }

    super.onStart();
  }

  @Override
  protected void onStop() {
    containerView.removeView(exoPlayerView);
    parent.addView(exoPlayerView, generateDefaultLayoutParams());

    if (playerControlView != null) {
      ImageButton imageButton = playerControlView.findViewById(com.brentvatne.react.R.id.exo_fullscreen);
      imageButton.setImageResource(com.google.android.exoplayer2.ui.R.drawable.exo_icon_fullscreen_enter);
      imageButton.setContentDescription(getContext().getString(com.google.android.exoplayer2.ui.R.string.exo_controls_fullscreen_enter_description));
      containerView.removeView(playerControlView);
      parent.addView(playerControlView, generateDefaultLayoutParams());
    }

    parent.requestLayout();
    parent = null;

    super.onStop();
  }

  private FrameLayout.LayoutParams generateDefaultLayoutParams() {
    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
        FrameLayout.LayoutParams.MATCH_PARENT,
        FrameLayout.LayoutParams.MATCH_PARENT
    );
    layoutParams.setMargins(0, 0, 0, 0);
    return layoutParams;
  }
}
