package belajar.game.singletouch;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

public class SingleTouchActivity extends Activity {

GambarView gambarview ;   
private Paint       vPaint;


@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    gambarview = new GambarView(this);
    setContentView(gambarview);
    vPaint = new Paint();
    vPaint.setAntiAlias(true);
    vPaint.setDither(true);
    vPaint.setColor(Color.GREEN);
    vPaint.setStyle(Paint.Style.STROKE);
    vPaint.setStrokeJoin(Paint.Join.ROUND);
    vPaint.setStrokeCap(Paint.Cap.ROUND);
    vPaint.setStrokeWidth(12);  
}

 public class GambarView extends View {

        public int width;
        public  int height;
        private Bitmap  vBitmap;
        private Canvas  vCanvas;
        private Path    vPath;
        private Paint   vBitmapPaint;
        Context context;
        private Paint lingkaranPaint;
        private Path lingkaranPath;

        public GambarView(Context c) {
        	super(c);
        	context=c;
        	vPath = new Path();
        	vBitmapPaint = new Paint(Paint.DITHER_FLAG);  
        	lingkaranPaint = new Paint();
        	lingkaranPath = new Path();
        	lingkaranPaint.setAntiAlias(true);
        	lingkaranPaint.setColor(Color.BLUE);
        	lingkaranPaint.setStyle(Paint.Style.STROKE);
        	lingkaranPaint.setStrokeJoin(Paint.Join.MITER);
        	lingkaranPaint.setStrokeWidth(4f); 
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        	super.onSizeChanged(w, h, oldw, oldh);

        	vBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        	vCanvas = new Canvas(vBitmap);

        }
        
        @Override
        protected void onDraw(Canvas canvas) {
        	super.onDraw(canvas);

        	canvas.drawBitmap( vBitmap, 0, 0, vBitmapPaint);

        	canvas.drawPath( vPath,  vPaint);

        	canvas.drawPath( lingkaranPath,  lingkaranPaint);
        }

        private float vX, vY;
        private static final float TOUCH_TOLERANCE = 4;

        private void sentuhanmulai(float x, float y) {
        	vPath.reset();
        	vPath.moveTo(x, y);
        	vX = x;
        	vY = y;
        }
        
        private void sentuhanbergerak(float x, float y) {
        	float dx = Math.abs(x - vX);
        	float dy = Math.abs(y - vY);
        	if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
        		vPath.quadTo(vX, vY, (x + vX)/2, (y + vY)/2);
        		vX = x;
        		vY = y;

        		lingkaranPath.reset();
        		lingkaranPath.addCircle(vX, vY, 30, Path.Direction.CW);
        	}
        }
        private void sentuhanlepas() {
        vPath.lineTo(vX, vY);
        lingkaranPath.reset();
       
        vCanvas.drawPath(vPath,  vPaint);
      
        vPath.reset();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                sentuhanmulai(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                sentuhanbergerak(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                sentuhanlepas();
                invalidate();
                break;
        }
        return true;
        }  
        }
  }