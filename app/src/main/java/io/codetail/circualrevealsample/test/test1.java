package io.codetail.circualrevealsample.test;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.MediaController;
import android.widget.VideoView;
import java.util.ArrayList;
import java.util.List;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import io.codetail.circualrevealsample.CardViewPlus;
import io.codetail.circualrevealsample.R;

/**
 * Created by Le on 2016/1/25.
 */
public class test1 extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener{
    private AnimatorSet mSet;
    private FloatingActionButton mFAB;
    private AppBarLayout mLay;
    private RecyclerView mRecyclerView;
    private CollapsingToolbarLayout mCtlLsjTest;
    private Toolbar mTbLsj;
    private CardViewPlus mCard1,mCard2;
    private SupportAnimator mAnimator;
    private VideoView mVideoView;
    private final String testUri = "http://materialdesign.qiniudn.com/videos/inline%20whatismaterial-materialprop-physicalprop-PaperShadow_01_xhdpi_008.mp4";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lsj_test_1);
        initView();
        initData();
    }

    private void initView() {
        mFAB = (FloatingActionButton) findViewById(R.id.fab_lsj_test_1);
        mLay = (AppBarLayout) findViewById(R.id.abl_top_bar);
//        mRecyclerView = (RecyclerView) findViewById(R.id.lsj_recyclerView);
        mCtlLsjTest = (CollapsingToolbarLayout) findViewById(R.id.ctl_lsj_test);
        mTbLsj = (Toolbar) findViewById(R.id.tb_lsj_toolbar);
        mCard1 = (CardViewPlus) findViewById(R.id.card_lsj_test1_test1);
        mCard2 = (CardViewPlus) findViewById(R.id.card_lsj_test1_test2);
        mVideoView = (VideoView) findViewById(R.id.vedio_lsj_test1_test);
        mCtlLsjTest.setTitle("美女剑豪带着肉");
        mCtlLsjTest.setScrimsShown(true);
        mLay.addOnOffsetChangedListener(this);
    }
    private void initData() {
        mSet = new AnimatorSet();
        initRecycleDate();
//        mTbLsj.add
        Uri uri = Uri.parse(testUri);
        mVideoView.setMediaController(new MediaController(this));
        mVideoView.setVideoURI(uri);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                mp.setLooping(true);
                mp.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                    @Override
                    public void onBufferingUpdate(MediaPlayer mp, int percent) {
                        Log.i("--------lsj--------", ":" + percent);
                    }

                });

            }
        });
    }

    private void initAnimation(){
        // get the center for the clipping circle
        //int cx = (myView.getLeft() + myView.getRight()) / 2;
        //int cy = (myView.getTop() + myView.getBottom()) / 2;
        int cx = mCard1.getRight();
        int cy = mCard1.getBottom();

        mCard1.setCardBackgroundColor(Color.parseColor("#22ddbb"));
        // get the final radius for the clipping circle
        float finalRadius = hypo(mCard1.getWidth(), mCard1.getHeight());

        mAnimator = ViewAnimationUtils.createCircularReveal(mCard2, cx, cy, 0, finalRadius);
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
    public void test1FABOnclick(View view){
//        Toast.makeText(this, ":"+mLay.getTargetElevation(), Toast.LENGTH_SHORT).show();
        final float y = mFAB.getY();
        final float x = mFAB.getX();
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(mFAB, "x",
                x, x+200);
        final ObjectAnimator anim2 = ObjectAnimator.ofFloat(mFAB, "y",
                y, y-150);
//        ObjectAnimator anim3 = ObjectAnimator.ofFloat(mFAB, "scaleX",
//                mFAB.getY(), mFAB.getY()-150);
//        ObjectAnimator anim4 = ObjectAnimator.ofFloat(mFAB, "scaleY",
//                mFAB.getY(), mFAB.getY()-150);
        final ObjectAnimator anim5 = ObjectAnimator.ofFloat(mFAB, "alpha",
                0f);
        mSet.setDuration(200);
        mSet.play(anim2).with(anim5).before(anim1);
        mSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                initAnimation();
                mFAB.setVisibility(View.GONE);
                mCard1.setVisibility(View.GONE);
                mCard2.setVisibility(View.VISIBLE);
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
    private void initRecycleDate(){
        mRecyclerView = (RecyclerView) this.findViewById(R.id.lsj_recyclerView);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        List<Integer> datas = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            datas.add(i);
        }
        mRecyclerView.setAdapter(new MyRecyclerViewAdapter(this, datas));
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        float mTotal = appBarLayout.getHeight() - mTbLsj.getHeight();
        float mFloat =  - verticalOffset;

        if(mFloat == 0){
            mVideoView.start();
        }else{
            mVideoView.pause();
        }
        if(mTotal == mFloat){
            appBarLayout.setAlpha(1f);
        }else{
            appBarLayout.setAlpha((mTotal - mFloat) / mTotal);
        }
//        Log.i("------------lsj--------:","lsj"+verticalOffset+":"+mTbLsj.getHeight()+":"+appBarLayout.getHeight());
    }
}
