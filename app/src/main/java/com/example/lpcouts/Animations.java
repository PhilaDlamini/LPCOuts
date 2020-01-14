package com.example.lpcouts;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;

public class Animations {

    //Animate the dots
    public static void animateDots(View paramView1, View paramView2, View paramView3) {
        ObjectAnimator objectAnimator1 = getAlphaAnim(paramView1, 1.0F, 0.0F);
        objectAnimator1.setRepeatCount(2);
        objectAnimator1.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator1.setRepeatCount(-1);

        ObjectAnimator objectAnimator2 = getAlphaAnim(paramView2, 1.0F, 0.0F);
        objectAnimator2.setStartDelay(100L);
        objectAnimator2.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator2.setRepeatCount(-1);

        ObjectAnimator objectAnimator3 = getAlphaAnim(paramView3, 1.0F, 0.0F);
        objectAnimator3.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator3.setRepeatCount(-1);
        objectAnimator3.setStartDelay(200L);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimator1, objectAnimator2, objectAnimator3);
        animatorSet.start();
    }

    public static ObjectAnimator getAlphaAnim(View paramView, float paramFloat1, float paramFloat2) {
        return ObjectAnimator.ofFloat(paramView, View.ALPHA,  paramFloat1, paramFloat2);
    }

    public static Animator getRevealAnimation(View paramView) {
        int i = (int)Math.hypot(paramView.getWidth(), paramView.getHeight());
        Animator animator = ViewAnimationUtils.createCircularReveal(paramView, paramView.getWidth(), paramView.getHeight(), 0.0F, i);
        animator.setDuration(400L);
        return animator;
    }

    public static Transition getSlide(View paramView) {
        Slide slide = new Slide(80);
        slide.addTarget(paramView);
        slide.setDuration(400L);
        return slide;
    }

    private static Transition getSlide(View paramView, int paramInt1, int paramInt2) {
        Slide slide = new Slide(paramInt1);
        slide.setStartDelay(paramInt2);
        slide.addTarget(paramView);
        return (Transition)slide;
    }

    public static void revealFromBottom(View paramView) {
        int i = (int)Math.hypot(paramView.getWidth(), paramView.getHeight());
        Animator animator = ViewAnimationUtils.createCircularReveal(paramView, paramView.getWidth(), paramView.getHeight(), 0.0F, i);
        animator.setDuration(400L);
        paramView.setVisibility(View.VISIBLE);
        animator.start();
    }

    public static void revealFromBottomStart(View paramView) {
        int i = (int)Math.hypot(paramView.getWidth(), paramView.getHeight());
        Animator animator = ViewAnimationUtils.createCircularReveal(paramView, (int)paramView.getX(), paramView.getHeight(), 0.0F, i);
        animator.setDuration(400L);
        paramView.setVisibility(View.VISIBLE);
        animator.start();
    }

    public static void slideButtonsIn(View paramView1, View paramView2, ViewGroup paramViewGroup) {
        Transition transition1 = getSlide(paramView1, 8388613, 0);
        Transition transition2 = getSlide(paramView2, 8388613, 300);
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition(transition1).addTransition(transition2);
        TransitionManager.beginDelayedTransition(paramViewGroup, transitionSet);
        paramView1.setVisibility(View.VISIBLE);
        paramView2.setVisibility(View.VISIBLE);
    }

    public static void slideButtonsOut(View paramView1, View paramView2, ViewGroup paramViewGroup) {
        Transition transition1 = getSlide(paramView1, 8388613, 0);
        Transition transition2 = getSlide(paramView2, 8388613, 300);
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition(transition1).addTransition(transition2);
        TransitionManager.beginDelayedTransition(paramViewGroup, (Transition)transitionSet);
        paramView1.setVisibility(4);
        paramView2.setVisibility(4);
    }

    public static void unReveal(View paramView) {
        int i = paramView.getHeight();
        int j = paramView.getWidth();
        int k = (int)Math.hypot(i, j);
        Animator animator = ViewAnimationUtils.createCircularReveal(paramView, j / 2, i / 2, k, 0.0F);
        animator.setStartDelay(2000L);
        animator.setDuration(500L);
        animator.start();
    }
}
