/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.liessu.extender.span;


import android.graphics.Color;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * A extensional class for ClickableSpan .
 *
 * <pre>
 * ex :
 * new ClickableSpanEx(Color.BLUE,Color.GRAY) {
 *      public void onClick(View widget) {
 *          Toast.makeText(DemoApplication.getContext(),name,Toast.LENGTH_SHORT).show();
 *      }
 * }
 * </pre>
 * <pre>
 * Note : If you need to  indicate click event of  ClickableSpanEx  , you must set the TextView OnTouchListener
 * like this :
 * <b>textView.setOnTouchListener(new ClickableSpanEx.ClickableSpanSelector());</b>
 * or
 * call <b>{@link #onTouch(View, MotionEvent)} in your own onTouch method </b>.
 * </pre>
 */
public abstract class ClickableSpanEx extends ClickableSpan {
    private static final String TAG = "ClickableSpanEx";

    /**
     * foreground color , protected member
     **/
    protected int mForegroundColor = Color.BLUE;
    /**
     * background color  , protected member
     **/
    protected int mBackgroundColor = Color.TRANSPARENT;
    /**
     * Determine whether the background of ClickableSpanEx transparent
     **/
    protected boolean isBackgroundTransparent = true;

    /**
     * New ClickableSpanEx instance .
     *
     * @param foregroundColor foreground color
     */
    public ClickableSpanEx(int foregroundColor) {
        this(foregroundColor, Color.TRANSPARENT);
    }

    /**
     * New ClickableSpanEx instance .
     *
     * @param foregroundColor foreground color
     * @param backgroundColor backgroundColor color
     */
    public ClickableSpanEx(int foregroundColor, int backgroundColor) {
        mForegroundColor = foregroundColor;
        mBackgroundColor = backgroundColor;
    }

    /**
     * If you have  implement OnTouchListener, call this one in your own
     * {@link android.view.View.OnTouchListener#onTouch(View, MotionEvent) OnTouchListener.onTouch}  method .
     *
     * @param v     The view the touch event has been dispatched to.
     * @param event The MotionEvent object containing full information about the event.
     * @return True if the listener has consumed the event , false otherwise.
     */
    public static boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        if (v instanceof TextView) {
            TextView widget = (TextView) v;
            if (action == MotionEvent.ACTION_UP ||
                    action == MotionEvent.ACTION_DOWN ||
                        action == MotionEvent.ACTION_MOVE) {
                int x = (int) event.getX();
                int y = (int) event.getY();

                x -= widget.getTotalPaddingLeft();
                y -= widget.getTotalPaddingTop();

                x += widget.getScrollX();
                y += widget.getScrollY();

                Layout layout = widget.getLayout();
                int line = layout.getLineForVertical(y);
                int off = layout.getOffsetForHorizontal(line, x);


                //* Return the text the TextView is displaying. If TextView.setText() was called with
                // * an argument of BufferType.SPANNABLE or BufferType.EDITABLE, you can cast
                // * the return value from this method to Spannable or Editable, respectively.
                // *
                //* Note: The content of the return value should not be modified. If you want
                //* a modifiable one, you should make your own copy first.
                Spannable buffer = (Spannable) widget.getText();
                ClickableSpanEx[] link = buffer.getSpans(off, off, ClickableSpanEx.class);

                if (link.length != 0) {
                    if(action == MotionEvent.ACTION_DOWN) {
                        Selection.setSelection(buffer, buffer.getSpanStart(link[0]), buffer.getSpanEnd(link[0]));
                        link[0].setTransparent(false);
                    }else{
                        link[0].onClick(widget);
                        link[0].setTransparent(true);
                        Selection.removeSelection(buffer);
                    }

                    return true;
                }
            }
        } else {
            Log.e(TAG, "ClickableSpanEx supports TextView only .");
        }
        return false;
    }

    /**
     * Determine whether the background of ClickableSpanEx transparent .
     *
     * @param isTransparent disable background color if true
     */
    public void setTransparent(boolean isTransparent) {
        this.isBackgroundTransparent = isTransparent;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setColor(mForegroundColor);
        ds.setUnderlineText(false);

        if (!isBackgroundTransparent)
            ds.bgColor = mBackgroundColor;
        else
            ds.bgColor = Color.TRANSPARENT;
    }

    /**
     * A inner static  class implements OnTouchListener interface . You can use it to indicate click
     * event of  ClickableSpanEx .
     */
    public static class ClickableSpanSelector implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return ClickableSpanEx.onTouch(v, event);
        }
    }
}
