
package com.weibo.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.loopj.android.image.SmartImageView;

/**
 * @作者:陈华清
 * 
 * @版本:1.0
 * @生成时期:2014年8月2日 下午3:23:52
 * @com.api
 */
public class RoundCornerImageView extends SmartImageView {

	/**
	 * @param arg0
	 */
	public RoundCornerImageView(Context arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	public RoundCornerImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}
	public RoundCornerImageView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		Path clipPath = new Path();
		int w = this.getWidth();
		int h = this.getHeight();
		clipPath.addRoundRect(new RectF(0, 0, w, h), 10.0f, 10.0f,
				Path.Direction.CW);
		canvas.clipPath(clipPath);
		super.onDraw(canvas);
		Rect rect=canvas.getClipBounds();
        rect.bottom--;
        rect.right--;
        Paint paint=new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        canvas.drawRect(rect, paint);
	}

}
