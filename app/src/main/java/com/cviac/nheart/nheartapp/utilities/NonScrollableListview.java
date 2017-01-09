package com.cviac.nheart.nheartapp.utilities;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by admin1 on 1/9/2017.
 */

public class NonScrollableListview extends ListView {


    public NonScrollableListview(Context context) {
        super(context);
    }
    public NonScrollableListview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public NonScrollableListview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMeasureSpec_custom = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec_custom);
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = getMeasuredHeight();
    }


}
