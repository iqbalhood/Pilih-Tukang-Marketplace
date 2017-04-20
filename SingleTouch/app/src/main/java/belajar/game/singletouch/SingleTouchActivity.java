package belajar.game.singletouch;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class SingleTouchActivity extends Activity {

GambarView gambarview ;   
private Paint       vPaint;
    public Bitmap  vBitmap;
    public Canvas  vCanvas;
    public Path    vPath;
    public  int width;
    public  int height;


    public Paint   vBitmapPaint;
    Context context;
    public Paint lingkaranPaint;
    public Path lingkaranPath;




@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);



    gambarview = new GambarView(this);
    FrameLayout.LayoutParams paramss = new FrameLayout.LayoutParams(
            700,
            700);
    paramss.gravity = Gravity.CENTER;
    addContentView(gambarview,paramss);

    int randomNum = 1 + (int)(Math.random() * 4);

    System.out.println("Randomnya " + randomNum);

    int grade = randomNum;

    switch(grade) {
        case 1 :
            System.out.println("Case 1 ");
            gambarview.setBackgroundResource(R.drawable.num1);
            break;
        case 2 :
            System.out.println("Case 2 ");
            gambarview.setBackgroundResource(R.drawable.num2);
            break;
        case 3 :
            System.out.println("Case 3 ");
            gambarview.setBackgroundResource(R.drawable.num3);
        case 4 :
            System.out.println("Case 4 ");
            gambarview.setBackgroundResource(R.drawable.num4);
            break;
        default :
            System.out.println("Case Default ");

            gambarview.setBackgroundResource(R.drawable.bg);
    }


    vPaint = new Paint();
    vPaint.setAntiAlias(true);
    vPaint.setDither(true);
    vPaint.setColor(Color.GREEN);
    vPaint.setStyle(Paint.Style.STROKE);
    vPaint.setStrokeJoin(Paint.Join.ROUND);
    vPaint.setStrokeCap(Paint.Cap.ROUND);
    vPaint.setStrokeWidth(12);

    Button button= new Button (this);
    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT);
    params.topMargin = 0;
    params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;

    ImageView imgLogo= new ImageView (this);
    FrameLayout.LayoutParams paramsimg = new FrameLayout.LayoutParams(
            300,
            300);
    paramsimg.topMargin = 0;
    paramsimg.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;




    TextView tv1= new TextView (this);
    FrameLayout.LayoutParams paramstxt = new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT);
    paramstxt.topMargin = 300;
    paramstxt.gravity = Gravity.TOP | Gravity.CENTER;




    imgLogo.setImageResource(R.drawable.logo);
    addContentView(imgLogo, paramsimg);

    tv1.setText("TEST ANGKA \n 1/10");
    tv1.setGravity(Gravity.CENTER);
    addContentView(tv1, paramstxt);

    button.setText("Next >>");
    addContentView(button, params);

    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            vBitmap.eraseColor(Color.TRANSPARENT);
            vCanvas.drawBitmap(vBitmap, 0, 0, vPaint);

            int randomNum = 1 + (int)(Math.random() * 10);

            System.out.println("Randomnya " + randomNum);

            int grade = randomNum;

            switch(grade) {
                case 1 :
                    System.out.println("Case 1 ");
                    gambarview.setBackgroundResource(R.drawable.num1);
                    break;
                case 2 :
                    System.out.println("Case 2 ");
                    gambarview.setBackgroundResource(R.drawable.num2);
                    break;
                case 3 :
                    System.out.println("Case 3 ");
                    gambarview.setBackgroundResource(R.drawable.num3);
                case 4 :
                    System.out.println("Case 4 ");
                    gambarview.setBackgroundResource(R.drawable.num4);
                    break;
                case 5 :
                    System.out.println("Case 3 ");
                    gambarview.setBackgroundResource(R.drawable.num5);
                case 6 :
                    System.out.println("Case 4 ");
                    gambarview.setBackgroundResource(R.drawable.num6);
                    break;
                case 7 :
                    System.out.println("Case 3 ");
                    gambarview.setBackgroundResource(R.drawable.num2);
                case 8 :
                    System.out.println("Case 4 ");
                    gambarview.setBackgroundResource(R.drawable.num6);
                    break;
                case 9 :
                    System.out.println("Case 3 ");
                    gambarview.setBackgroundResource(R.drawable.num3);
                case 10 :
                    System.out.println("Case 4 ");
                    gambarview.setBackgroundResource(R.drawable.num5);
                    break;
                default :
                    System.out.println("Case Default ");

                    gambarview.setBackgroundResource(R.drawable.bg);
            }
        }
    });
}

 public class GambarView extends View {



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
            System.out.println("DILEPAS");
        vPath.lineTo(vX, vY);
        lingkaranPath.reset();
       
        vCanvas.drawPath(vPath,  vPaint);
      
        vPath.reset();
//            vCanvas.restore();

//            vBitmap.eraseColor(Color.TRANSPARENT);
//
//            vCanvas.drawBitmap(vBitmap, 0, 0, vPaint);

        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

            System.out.println("X  " + x + "Y    "+y);

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