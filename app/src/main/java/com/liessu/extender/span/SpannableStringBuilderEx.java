package com.liessu.extender.span;

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
public class SpannableStringBuilderEx extends SpannableStringBuilder{
    private final static String TAG = "SpannableStringBuilderEx";

    /**
     * Create a new SpannableStringBuilderEx with empty contents
     */
    public SpannableStringBuilderEx() {
        super("");
    }

    /**
     * Create a new SpannableStringBuilderEx containing a copy of the
     * specified text, including its spans if any.
     */
    public SpannableStringBuilderEx(CharSequence text) {
        super(text, 0, text.length());
    }

    /**
     * Create a new SpannableStringBuilderEx containing a copy of the
     * specified slice of the specified text, including its spans if any.
     */
    public SpannableStringBuilderEx(CharSequence text, int start, int end) {
       super(text , start , end);
    }


    public SpannableStringBuilderEx append(char text) {
        append(String.valueOf(text));
        return this;
    }

    public SpannableStringBuilderEx append(CharSequence text) {
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
    public SpannableStringBuilderEx append(CharSequence text, Object what, int flags) {
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
     * @return this {@code SpannableStringBuilderEx}.
     */
    public SpannableStringBuilderEx append(CharSequence text, int flags , Object... whats) {
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
     * @return SpannableStringBuilderEx instance
     */
    public SpannableStringBuilderEx appendImageSpan(Drawable drawable , int imageSpanFlags , int spanFlags ){
        int start = length();
        append("ImageSpan");
        setSpan(new ImageSpan(drawable , imageSpanFlags), start, length(), spanFlags);
        return this;
    }

    /**
     *  Cat off  the SpannableStringBuilderEx's content , then build new one .
     * @param start cat off start index
     * @param end cat off end index
     * @return a new SpannableStringBuilderEx instance .
     */
    public SpannableStringBuilderEx rebuild(int start , int end){
        return new SpannableStringBuilderEx(this, start, end);
    }


    public static SpannableStringBuilderEx valueOf(CharSequence source) {
        if (source instanceof SpannableStringBuilderEx) {
            return (SpannableStringBuilderEx) source;
        } else {
            return new SpannableStringBuilderEx(source);
        }
    }


    /**
     * Returns the depth of TextWatcher callbacks. Returns 0 when the object is not handling
     * TextWatchers. A return value greater than 1 implies that a TextWatcher caused a change that
     * recursively triggered a TextWatcher.
     * @return -1 if Android sdk version is too low .
     */
    @Override
    public int getTextWatcherDepth() {
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            return super.getTextWatcherDepth();
        else {
            Log.e(TAG , "Android sdk  version number must be greater than or equal to 23. ");
            return -1;
        }
    }
}
