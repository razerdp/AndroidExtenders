package com.liessu.extender;

import android.os.Build;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
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
     * Appends the character sequence {@code text} and spans {@code what} over the appended part.
     * See {@link Spanned} for an explanation of what the flags mean.
     * @param text the character sequence to append.
     * @param what the object to be spanned over the appended text.
     * @param flags see {@link Spanned}.
     * @return this {@code SpannableStringBuilderX}.
     */
    @Override
    public SpannableStringBuilderX append(CharSequence text, Object what, int flags) {
            int start = length();
            append(text);
            setSpan(what, start, length(), flags);
            return this;
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
            Log.e( TAG , "Android sdk  version number must be greater than or equal to 23. ");
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
