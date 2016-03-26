package com.liessu.extender;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.util.Log;

/**
 *  A extensional class for SpannableStringBuilder , ignore the Android sdk version .
 */
public class SpannableStringBuilderX extends SpannableStringBuilder{
    private final static String TAG = "SpannableStringBuilderX";

    /**
     * Create a new SpannableStringBuilderX with empty contents
     */
    public SpannableStringBuilderX() {
        super("");
    }

    /**
     * Create a new SpannableStringBuilderX containing a copy of the
     * specified text, including its spans if any.
     */
    public SpannableStringBuilderX(CharSequence text) {
        super(text, 0, text.length());
    }

    /**
     * Create a new SpannableStringBuilderX containing a copy of the
     * specified slice of the specified text, including its spans if any.
     */
    public SpannableStringBuilderX(CharSequence text, int start, int end) {
       super(text , start , end);
    }


    public SpannableStringBuilderX append(char text) {
        append(String.valueOf(text));
        return this;
    }

    public SpannableStringBuilderX append(CharSequence text) {
        int length = length();
        replace(length, length, text, 0, text.length());
        return this;
    }

    public SpannableStringBuilder append(CharSequence text, int start, int end) {
        int length = length();
        replace(length, length, text, start, end);
        return this;
    }


    /**
     * Appends the character sequence {@code text} and spans {@code what} over the appended part.
     * See {@link Spanned} for an explanation of what the flags mean.
     * @param text the character sequence to append.
     * @param what the object to be spanned over the appended text.
     * @param flags see {@link Spanned}.
     * @return this {@code SpannableStringBuilder}.
     */
    public SpannableStringBuilderX append(CharSequence text, Object what, int flags) {
            int start = length();
            append(text);
            setSpan(what, start, length(), flags);
            return this;
    }

    /**
     * Appends the character sequence {@code text} and spans {@code what} over the appended part.
     * See {@link Spanned} for an explanation of what the flags mean.
     * @param text the character sequence to append.
     * @param flags see {@link Spanned}.
     * @param whats the objects to be spanned over the appended text.
     * @return this {@code SpannableStringBuilderX}.
     */
    public SpannableStringBuilderX append(CharSequence text, int flags ,Object... whats) {
        int start = length();
        append(text);

        for(Object object : whats){
            setSpan(object, start, length(), flags);
        }
        return this;
    }


    /**
     * Append ImageSpan easily .
     * @param drawable  drawable instance to insert
     * @param imageSpanFlags one of {@link DynamicDrawableSpan#ALIGN_BOTTOM} or
     * {@link DynamicDrawableSpan#ALIGN_BASELINE}.
     * @param spanFlags determine how the span will behave
     * @return SpannableStringBuilderX instance
     */
    public SpannableStringBuilderX appendImageSpan(Drawable drawable , int imageSpanFlags , int spanFlags ){
        int start = length();
        append("ImageSpan");
        setSpan(new ImageSpan(drawable , imageSpanFlags), start, length(), spanFlags);
        return this;
    }

    /**
     *  Cat off  the SpannableStringBuilderX's content , then build new one .
     * @param start cat off start index
     * @param end cat off end index
     * @return a new SpannableStringBuilderX instance .
     */
    public SpannableStringBuilderX rebuild(int start , int end){
        return new SpannableStringBuilderX(this, start, end);
    }

    /**
     * Return a CharSequence containing a copy of the chars in this buffer.
     */
    public CharSequence toSequence() {
        return new SpannableStringBuilder(this);
    }



    /**
     * Returns the depth of TextWatcher callbacks. Returns 0 when the object is not handling
     * TextWatchers. A return value greater than 1 implies that a TextWatcher caused a change that
     * recursively triggered a TextWatcher.
     * @return -1 if Android sdk version is too low .
     */
    @Override
    public int getTextWatcherDepth() {
        if( getVersion() >= Build.VERSION_CODES.M)
            return super.getTextWatcherDepth();
        else {
            Log.e(TAG , "Android sdk  version number must be greater than or equal to 23. ");
            return -1;
        }
    }

    /**
     * Returns the version string for the Android sdk .
     */
    private int getVersion(){
        return Build.VERSION.SDK_INT;
    }
}
