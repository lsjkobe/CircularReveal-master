package io.codetail.circualrevealsample.test;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.MediaController;
import android.widget.VideoView;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import io.codetail.circualrevealsample.CardViewPlus;
import io.codetail.circualrevealsample.R;

/**
 * Created by Le on 2016/1/22.
 */
public class lsj_test extends Activity{
    private AnimatorSet mSet;
    private FloatingActionButton mFAB;
    private SupportAnimator mAnimator;
    private CardViewPlus mCard, mCard1;
    private VideoView mVideoView;
    private MediaPlayer mp = new MediaPlayer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lsj_test);
        initView();
        initData();
    }
    private void initView(){
        mFAB = (FloatingActionButton) findViewById(R.id.fab_lsj_test);
        mCard = (CardViewPlus) findViewById(R.id.card_lsj_test);
        mCard1 = (CardViewPlus) findViewById(R.id.card_lsj_test2);
//        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" +R.raw.lsj);
        Uri uri = Uri.parse("rtsp://218.204.223.237:554/live/1/66251FC11353191F/e7ooqwcfbqjoo80j.sdp");
//        Uri uri1 = Uri.parse("http://t.live.cntv.cn/m3u8/cctv5-380.m3u8");

        mVideoView = (VideoView)this.findViewById(R.id.video_mp4_play);
        mVideoView.setMediaController(new MediaController(this));
        mVideoView.setVideoURI(uri);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                    @Override
                    public void onBufferingUpdate(MediaPlayer mp, int percent) {
//                        Toast.makeText(lsj_test.this, "-"+percent, Toast.LENGTH_SHORT).show();
                        Log.i("--------lsj--------", ":"+percent);
                    }
                });

            }
        });
    }

    private void initData(){
        mSet = new AnimatorSet();
    }
    private void initAnimation(){
        // get the center for the clipping circle
        //int cx = (myView.getLeft() + myView.getRight()) / 2;
        //int cy = (myView.getTop() + myView.getBottom()) / 2;
        int cx = mCard.getRight();
        int cy = mCard.getBottom();

        mCard.setCardBackgroundColor(Color.parseColor("#22ddbb"));
        // get the final radius for the clipping circle
        float finalRadius = hypo(mCard.getWidth(), mCard.getHeight());

        mAnimator = ViewAnimationUtils.createCircularReveal(mCard1, (int)mFAB.getX(), (int)mFAB.getY(), 0, finalRadius);
        mAnimator.addListener(new SupportAnimator.AnimatorListener() {
            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationEnd() {
                mVideoView.setVisibility(View.VISIBLE);
//
                mVideoView.start();
                mVideoView.requestFocus();
            }

            @Override
            public void onAnimationCancel() {

            }

            @Override
            public void onAnimationRepeat() {

            }
        });
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.setDuration(1000);
        mAnimator.start();
    }
    static float hypo(int a, int b){
        return (float) Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }
    public void FABOnclick(View view){
        final float y = mFAB.getY();
//        ObjectAnimator anim1 = ObjectAnimator.ofFloat(mFAB, "x",
//                x, x);
        final ObjectAnimator anim2 = ObjectAnimator.ofFloat(mFAB, "y",
                mFAB.getY(), mFAB.getY()-150);
//        ObjectAnimator anim3 = ObjectAnimator.ofFloat(mFAB, "scaleX",
//                mFAB.getY(), mFAB.getY()-150);
//        ObjectAnimator anim4 = ObjectAnimator.ofFloat(mFAB, "scaleY",
//                mFAB.getY(), mFAB.getY()-150);
        final ObjectAnimator anim5 = ObjectAnimator.ofFloat(mFAB, "alpha",
                0f);
        mSet.setDuration(200);
        mSet.play(anim2).with(anim5);
        mSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                initAnimation();
                mFAB.setVisibility(View.GONE);
                mCard.setVisibility(View.GONE);
                mCard1.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        mSet.start();
    }
}
