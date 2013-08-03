package com.linkites.nexgenfilmfestival;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

public class CustomVideo extends VideoView {

    private PlayPauseListener mListener;

    public CustomVideo(Context context) {
        super(context);
    }

    public CustomVideo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomVideo(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setPlayPauseListener(PlayPauseListener listener) {
        mListener = listener;
    }

    @Override
    public void pause() {
        super.pause();
        if (mListener != null) {
            mListener.onPause();
        }
    }

    @Override
    public void start() {
        super.start();
        if (mListener != null) {
            mListener.onPlay();
        }
    }

    interface PlayPauseListener {
        void onPlay();
        void onPause();
    }

}
