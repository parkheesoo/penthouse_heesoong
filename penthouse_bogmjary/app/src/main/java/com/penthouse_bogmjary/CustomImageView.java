package com.penthouse_bogmjary;

import android.app.AlertDialog;
import com.penthouse_bogmjary.BuildConfig;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.InputDeviceCompat;
import androidx.core.view.ViewCompat;

import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class CustomImageView extends AppCompatImageView {
    static final int CLICK = 3;
    static final int DRAG = 1;
    static final int NONE = 0;
    static final int ZOOM = 2;
    boolean b_showmeter = false;
    private Bitmap canvasBitmap;
    private Paint canvasPaint;
    Context context;
    public mdata cur_data;
    public mdata cur_zoomdata;
    ArrayList<mdata> datas = new ArrayList<>();
    private Canvas drawCanvas;
    private Paint drawPaint;
    private Paint drawRect;
    public int height;
    float height_calibrate = 0.0f;
    float[] img_m;
    Matrix img_matrix;
    private boolean isEditable;
    PointF last = new PointF();
    float[] m;
    ScaleGestureDetector mScaleDetector;
    Matrix matrix;
    float maxScale = 5.0f;
    float minScale = 1.0f;
    int mode = 0;
    int oldMeasuredHeight;
    int oldMeasuredWidth;
    protected float origHeight;
    protected float origWidth;
    private int paintColor = SupportMenu.CATEGORY_MASK;
    public mdata predata = null;
    float saveScale = 1.0f;
    PointF start = new PointF();
    public float step;
    private Path temppath;
    private Rect temprect;
    public TextView txt_info;
    public int viewHeight;
    public int viewWidth;
    public int width;
    private boolean zoomEnable = true;

    /* access modifiers changed from: package-private */
    public float getFixDragTrans(float f, float f2, float f3) {
        if (f3 <= f2) {
            return 0.0f;
        }
        return f;
    }

    /* access modifiers changed from: package-private */
    public float getFixTrans(float f, float f2, float f3) {
        float f4;
        float f5;
        if (f3 <= f2) {
            f4 = f2 - f3;
            f5 = 0.0f;
        } else {
            f5 = f2 - f3;
            f4 = 0.0f;
        }
        if (f < f5) {
            return (-f) + f5;
        }
        if (f > f4) {
            return (-f) + f4;
        }
        return 0.0f;
    }

    public CustomImageView(Context context2) {
        super(context2);
        sharedConstructing(context2);
    }

    public void setZoomEnable(boolean z) {
        this.zoomEnable = z;
    }

    public CustomImageView(Context context2, AttributeSet attributeSet) {
        super(context2, attributeSet);
        sharedConstructing(context2);
        this.canvasPaint = new Paint(4);
        setupDrawing();
    }

    public void resetview(Bitmap bitmap) {
        this.datas = new ArrayList<>();
        this.cur_data = null;
        this.cur_zoomdata = null;
        this.height_calibrate = 0.0f;
        this.b_showmeter = false;
        this.saveScale = 1.0f;
        if (bitmap != null) {
            setImageBitmap(bitmap);
        }
        this.canvasBitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888);
        this.drawCanvas = new Canvas(this.canvasBitmap);
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.height = i2;
        this.width = i;
        this.canvasBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        this.drawCanvas = new Canvas(this.canvasBitmap);
    }

    private void setupDrawing() {
        this.drawPaint = new Paint();
        this.drawPaint.setColor(this.paintColor);
        this.drawPaint.setAntiAlias(true);
        this.drawPaint.setDither(true);
        this.drawPaint.setStyle(Paint.Style.STROKE);
        this.drawPaint.setStrokeJoin(Paint.Join.ROUND);
        this.drawPaint.setStrokeCap(Paint.Cap.ROUND);
        this.drawPaint.setStrokeWidth(4.0f);
        this.drawRect = new Paint();
        this.drawRect.setColor(-16776961);
        this.drawRect.setAntiAlias(true);
        this.drawRect.setDither(true);
        this.drawRect.setStyle(Paint.Style.STROKE);
        this.drawRect.setStrokeJoin(Paint.Join.ROUND);
        this.drawRect.setStrokeCap(Paint.Cap.ROUND);
        this.drawRect.setStrokeWidth(4.0f);
    }

    private void sharedConstructing(Context context2) {
        super.setClickable(true);
        this.context = context2;
        this.mScaleDetector = new ScaleGestureDetector(context2, new ScaleListener());
        this.matrix = new Matrix();
        this.img_matrix = new Matrix();
        this.m = new float[9];
        this.img_m = new float[9];
        setImageMatrix(this.img_matrix);
        setScaleType(ImageView.ScaleType.MATRIX);
        setOnTouchListener(new View.OnTouchListener() {
            /* class com.shadow_sim.CustomImageView.AnonymousClass1 */

            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!CustomImageView.this.zoomEnable) {
                    return false;
                }
                CustomImageView.this.mScaleDetector.onTouchEvent(motionEvent);
                PointF pointF = new PointF(motionEvent.getX(), motionEvent.getY());
                int action = motionEvent.getAction();
                if (action == 0) {
                    float x = motionEvent.getX();
                    float y = motionEvent.getY();
                    float f = (x / CustomImageView.this.m[0]) - (CustomImageView.this.m[2] / CustomImageView.this.m[0]);
                    float f2 = (y / CustomImageView.this.m[4]) - (CustomImageView.this.m[5] / CustomImageView.this.m[4]);
                    int i = 0;
                    while (true) {
                        if (i >= CustomImageView.this.datas.size()) {
                            break;
                        }
                        mdata mdata = CustomImageView.this.datas.get(i);
                        if (((float) (CustomImageView.this.datas.get(i).points.get(0).x + 50)) <= f || f <= ((float) (CustomImageView.this.datas.get(i).points.get(0).x - 10)) || ((float) (CustomImageView.this.datas.get(i).points.get(0).y + 50)) <= f2 || f2 <= ((float) (CustomImageView.this.datas.get(i).points.get(0).y - 10))) {
                            i++;
                        } else {
                            CustomImageView customImageView = CustomImageView.this;
                            customImageView.cur_zoomdata = mdata;
                            customImageView.drawalldata(SupportMenu.CATEGORY_MASK);
                            for (int i2 = 0; i2 < CustomImageView.this.cur_zoomdata.points.size(); i2++) {
                                Path path = new Path();
                                path.moveTo((float) CustomImageView.this.cur_zoomdata.points.get(i2).x, (float) CustomImageView.this.cur_zoomdata.points.get(i2).y);
                                if (i2 == CustomImageView.this.cur_zoomdata.points.size() - 1) {
                                    path.lineTo((float) CustomImageView.this.cur_zoomdata.points.get(0).x, (float) CustomImageView.this.cur_zoomdata.points.get(0).y);
                                } else {
                                    int i3 = i2 + 1;
                                    path.lineTo((float) CustomImageView.this.cur_zoomdata.points.get(i3).x, (float) CustomImageView.this.cur_zoomdata.points.get(i3).y);
                                }
                                CustomImageView.this.drawPaint.setColor(InputDeviceCompat.SOURCE_ANY);
                                CustomImageView.this.drawCanvas.drawPath(path, CustomImageView.this.drawPaint);
                                CustomImageView.this.drawPaint.setColor(SupportMenu.CATEGORY_MASK);
                            }
                        }
                    }
                    CustomImageView.this.last.set(pointF);
                    CustomImageView.this.start.set(CustomImageView.this.last);
                    CustomImageView.this.mode = 1;
                } else if (action == 1) {
                    CustomImageView.this.mode = 0;
                    int abs = (int) Math.abs(pointF.x - CustomImageView.this.start.x);
                    int abs2 = (int) Math.abs(pointF.y - CustomImageView.this.start.y);
                    if (abs < 3 && abs2 < 3) {
                        CustomImageView.this.performClick();
                    }
                } else if (action != 2) {
                    if (action == 6) {
                        CustomImageView.this.mode = 0;
                    }
                } else if (CustomImageView.this.mode == 1) {
                    float f3 = pointF.x - CustomImageView.this.last.x;
                    float f4 = pointF.y - CustomImageView.this.last.y;
                    CustomImageView customImageView2 = CustomImageView.this;
                    float fixDragTrans = customImageView2.getFixDragTrans(f3, (float) customImageView2.viewWidth, CustomImageView.this.origWidth * CustomImageView.this.saveScale);
                    CustomImageView customImageView3 = CustomImageView.this;
                    float fixDragTrans2 = customImageView3.getFixDragTrans(f4, (float) customImageView3.viewHeight, CustomImageView.this.origHeight * CustomImageView.this.saveScale);
                    CustomImageView.this.matrix.postTranslate(fixDragTrans, fixDragTrans2);
                    CustomImageView.this.img_matrix.postTranslate(fixDragTrans, fixDragTrans2);
                    CustomImageView.this.fixTrans();
                    CustomImageView.this.last.set(pointF.x, pointF.y);
                }
                CustomImageView customImageView4 = CustomImageView.this;
                customImageView4.setImageMatrix(customImageView4.img_matrix);
                CustomImageView.this.invalidate();
                return true;
            }
        });
    }

    public void setMaxZoom(float f) {
        this.maxScale = f;
    }

    /* access modifiers changed from: private */
    public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        private ScaleListener() {
        }

        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            CustomImageView.this.mode = 2;
            return true;
        }

        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            float f;
            float scaleFactor = scaleGestureDetector.getScaleFactor();
            float f2 = CustomImageView.this.saveScale;
            CustomImageView.this.saveScale *= scaleFactor;
            if (CustomImageView.this.saveScale > CustomImageView.this.maxScale) {
                CustomImageView customImageView = CustomImageView.this;
                customImageView.saveScale = customImageView.maxScale;
                f = CustomImageView.this.maxScale;
            } else {
                if (CustomImageView.this.saveScale < CustomImageView.this.minScale) {
                    CustomImageView customImageView2 = CustomImageView.this;
                    customImageView2.saveScale = customImageView2.minScale;
                    f = CustomImageView.this.minScale;
                }
                if (CustomImageView.this.origWidth * CustomImageView.this.saveScale > ((float) CustomImageView.this.viewWidth) || CustomImageView.this.origHeight * CustomImageView.this.saveScale <= ((float) CustomImageView.this.viewHeight)) {
                    CustomImageView.this.matrix.postScale(scaleFactor, scaleFactor, (float) (CustomImageView.this.viewWidth / 2), (float) (CustomImageView.this.viewHeight / 2));
                    CustomImageView.this.img_matrix.postScale(scaleFactor, scaleFactor, (float) (CustomImageView.this.viewWidth / 2), (float) (CustomImageView.this.viewHeight / 2));
                } else {
                    CustomImageView.this.matrix.postScale(scaleFactor, scaleFactor, scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY());
                    CustomImageView.this.img_matrix.postScale(scaleFactor, scaleFactor, scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY());
                }
                CustomImageView.this.fixTrans();
                return true;
            }
            scaleFactor = f / f2;
            if (CustomImageView.this.origWidth * CustomImageView.this.saveScale > ((float) CustomImageView.this.viewWidth)) {
            }
            CustomImageView.this.matrix.postScale(scaleFactor, scaleFactor, (float) (CustomImageView.this.viewWidth / 2), (float) (CustomImageView.this.viewHeight / 2));
            CustomImageView.this.img_matrix.postScale(scaleFactor, scaleFactor, (float) (CustomImageView.this.viewWidth / 2), (float) (CustomImageView.this.viewHeight / 2));
            CustomImageView.this.fixTrans();
            return true;
        }
    }

    /* access modifiers changed from: package-private */
    public void fixTrans() {
        this.matrix.getValues(this.m);
        this.img_matrix.getValues(this.img_m);
        float[] fArr = this.img_m;
        float f = fArr[2];
        float f2 = fArr[5];
        float fixTrans = getFixTrans(f, (float) this.viewWidth, this.origWidth * this.saveScale);
        float fixTrans2 = getFixTrans(f2, (float) this.viewHeight, this.origHeight * this.saveScale);
        if (fixTrans != 0.0f || fixTrans2 != 0.0f) {
            this.matrix.postTranslate(fixTrans, fixTrans2);
            this.img_matrix.postTranslate(fixTrans, fixTrans2);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int i3;
        int i4;
        super.onMeasure(i, i2);
        this.viewWidth = View.MeasureSpec.getSize(i);
        this.viewHeight = View.MeasureSpec.getSize(i2);
        if (getDrawable() == null) {
            Paint paint = new Paint();
            paint.setTextSize(50.0f);
            paint.setAntiAlias(true);
            paint.setARGB(255, 0, 0, 0);
            Bitmap createBitmap = Bitmap.createBitmap(this.viewWidth, this.viewHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            canvas.drawColor(-3355444);
            canvas.drawBitmap(createBitmap, 0.0f, 0.0f, (Paint) null);
            canvas.drawText(this.context.getResources().getString(R.string.description), 20.0f, 100.0f, paint);
            canvas.drawText(this.context.getResources().getString(R.string.description1), 20.0f, 170.0f, paint);
            setImageBitmap(createBitmap);
        }
        int i5 = this.oldMeasuredHeight;
        if ((i5 != this.viewWidth || i5 != this.viewHeight) && (i3 = this.viewWidth) != 0 && (i4 = this.viewHeight) != 0) {
            this.oldMeasuredHeight = i4;
            this.oldMeasuredWidth = i3;
            if (this.saveScale == 1.0f) {
                Drawable drawable = getDrawable();
                if (drawable != null && drawable.getIntrinsicWidth() != 0 && drawable.getIntrinsicHeight() != 0) {
                    int intrinsicWidth = drawable.getIntrinsicWidth();
                    int intrinsicHeight = drawable.getIntrinsicHeight();
                    Log.d("bmSize", "bmWidth: " + intrinsicWidth + " bmHeight : " + intrinsicHeight);
                    float f = (float) intrinsicWidth;
                    float f2 = (float) intrinsicHeight;
                    float min = Math.min(((float) this.viewWidth) / f, ((float) this.viewHeight) / f2);
                    this.img_matrix.setScale(min, min);
                    float f3 = (((float) this.viewHeight) - (f2 * min)) / 2.0f;
                    float f4 = (((float) this.viewWidth) - (min * f)) / 2.0f;
                    this.img_matrix.postTranslate(f4, f3);
                    this.origWidth = ((float) this.viewWidth) - (f4 * 2.0f);
                    this.origHeight = ((float) this.viewHeight) - (f3 * 2.0f);
                    setImageMatrix(this.img_matrix);
                    int i6 = this.viewWidth;
                    int i7 = this.viewHeight;
                    Log.d("bmSize", "bmWidth: " + i6 + " bmHeight : " + i7);
                    float min2 = Math.min(((float) this.viewWidth) / ((float) i6), ((float) this.viewHeight) / ((float) i7));
                    this.matrix.setScale(min2, min2);
                    this.matrix.postTranslate(0.0f, 0.0f);
                } else {
                    return;
                }
            }
            fixTrans();
        }
    }

    public void drawtext(float f, int i, int i2) {
        Paint paint = new Paint();
        paint.setTextSize(20.0f);
        Paint.FontMetrics fontMetrics = new Paint.FontMetrics();
        paint.setColor(-1);
        paint.getFontMetrics(fontMetrics);
        float f2 = (float) i2;
        float f3 = (float) 5;
        float f4 = (float) i;
        this.drawCanvas.drawRect((float) (i - 5), (fontMetrics.top + f2) - f3, paint.measureText(Float.toString(f)) + f4 + f3, f3 + fontMetrics.bottom + f2, paint);
        paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.drawCanvas.drawText(Float.toString(f), f4, f2, paint);
    }

    public void drawalldata(int i) {
        this.canvasBitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888);
        this.drawCanvas = new Canvas(this.canvasBitmap);
        if (this.datas != null) {
            for (int i2 = 0; i2 < this.datas.size(); i2++) {
                mdata mdata = this.datas.get(i2);
                for (int i3 = 0; i3 < mdata.points.size(); i3++) {
                    Path path = new Path();
                    path.moveTo((float) mdata.points.get(i3).x, (float) mdata.points.get(i3).y);
                    if (i3 == mdata.points.size() - 1) {
                        path.lineTo((float) mdata.points.get(0).x, (float) mdata.points.get(0).y);
                    } else {
                        int i4 = i3 + 1;
                        path.lineTo((float) mdata.points.get(i4).x, (float) mdata.points.get(i4).y);
                    }
                    this.drawPaint.setColor(i);
                    this.drawCanvas.drawPath(path, this.drawPaint);
                }
                drawtext((float) mdata.height, mdata.points.get(0).x, mdata.points.get(0).y);
            }
            invalidate();
        }
    }

    public void deletedata() {
        mdata mdata = this.cur_zoomdata;
        if (mdata != null) {
            this.datas.remove(mdata);
            this.cur_zoomdata = null;
            drawalldata(SupportMenu.CATEGORY_MASK);
        }
        if (this.datas.size() == 0) {
            resetview(null);
        }
    }

    public void up() {
        mdata mdata = this.cur_zoomdata;
        if (mdata != null) {
            mdata.height++;
            drawtext((float) this.cur_zoomdata.height, this.cur_zoomdata.points.get(0).x, this.cur_zoomdata.points.get(0).y);
        }
    }

    public void down() {
        mdata mdata = this.cur_zoomdata;
        if (mdata != null) {
            mdata.height--;
            drawtext((float) this.cur_zoomdata.height, this.cur_zoomdata.points.get(0).x, this.cur_zoomdata.points.get(0).y);
        }
    }

    public void setDrawingEnabled(boolean z) {
        this.isEditable = z;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        Point point;
        boolean z;
        Point point2;
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        float[] fArr = this.m;
        float f = (y / fArr[4]) - (fArr[5] / fArr[4]);
        float f2 = this.saveScale;
        float f3 = ((x / fArr[0]) - (fArr[2] / fArr[0])) - (70.0f / f2);
        float f4 = f - (70.0f / f2);
        if (f3 < 0.0f) {
            f3 = 0.0f;
        }
        if (f4 < 0.0f) {
            f4 = 0.0f;
        }
        Point point3 = new Point((int) f3, (int) f4);
        if (this.isEditable && !this.b_showmeter) {
            int action = motionEvent.getAction();
            if (action != 0) {
                if (action == 1) {
                    mdata mdata = this.cur_data;
                    if (mdata != null) {
                        if (mdata.points.size() >= 1) {
                            if (this.cur_data.points.get(0).x + 15 <= point3.x || point3.x <= this.cur_data.points.get(0).x - 15 || this.cur_data.points.get(0).y + 15 <= point3.y || point3.y <= this.cur_data.points.get(0).y - 15) {
                                this.cur_data.points.add(new Point(point3.x, point3.y));
                                point = this.cur_data.points.get(this.cur_data.points.size() - 2);
                                point2 = this.cur_data.points.get(this.cur_data.points.size() - 1);
                                z = false;
                            } else {
                                point = this.cur_data.points.get(this.cur_data.points.size() - 1);
                                point2 = this.cur_data.points.get(0);
                                this.datas.add(this.cur_data);
                                z = true;
                            }
                            this.temppath = new Path();
                            this.temppath.moveTo((float) point.x, (float) point.y);
                            this.temppath.lineTo((float) point2.x, (float) point2.y);
                            this.drawCanvas.drawPath(this.temppath, this.drawPaint);
                            if (z) {
                                drawtext((float) this.cur_data.height, point2.x, point2.y);
                                this.cur_data = null;
                            }
                            mdata mdata2 = this.cur_data;
                            if (mdata2 != null && this.height_calibrate == 0.0f && mdata2.points.size() >= 2) {
                                this.b_showmeter = true;
                                showmeter();
                            }
                        } else {
                            this.cur_data.points.add(new Point(point3.x, point3.y));
                            this.temprect = new Rect(point3.x - 5, point3.y - 5, point3.x + 5, point3.y + 5);
                            this.drawCanvas.drawRect(this.temprect, this.drawRect);
                        }
                        this.temprect = null;
                        this.temppath = null;
                    }
                } else if (action != 2) {
                    return false;
                }
            }
            if (this.cur_data == null) {
                this.cur_data = new mdata(10, this.viewWidth, this.viewHeight);
            }
            if (this.cur_data.points.size() >= 1) {
                ArrayList<Point> arrayList = this.cur_data.points;
                Point point4 = arrayList.get(arrayList.size() - 1);
                this.temppath = new Path();
                this.temppath.moveTo((float) point4.x, (float) point4.y);
                this.temppath.lineTo((float) point3.x, (float) point3.y);
            } else {
                this.temprect = new Rect(point3.x - 5, point3.y - 5, point3.x + 5, point3.y + 5);
            }
        }
        invalidate();
        return true;
    }

    public void showmeter() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        View inflate = ((LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dialog_login, (ViewGroup) null);
        builder.setView(inflate);
        final EditText editText = (EditText) inflate.findViewById(R.id.txt_height);
        final EditText editText2 = (EditText) inflate.findViewById(R.id.txt_step);
        editText2.setText(String.valueOf(this.step));
        final AlertDialog create = builder.create();
        create.setCancelable(false);
        ((Button) inflate.findViewById(R.id.buttonSubmit)).setOnClickListener(new View.OnClickListener() {
            /* class com.shadow_sim.CustomImageView.AnonymousClass2 */

            public void onClick(View view) {
                String obj = editText.getText().toString();
                String obj2 = editText2.getText().toString();
                if (!obj.equals(BuildConfig.FLAVOR) && !obj2.equals(BuildConfig.FLAVOR) && Integer.parseInt(obj) > 0 && Float.parseFloat(obj2) > 0.0f) {
                    TextView textView = CustomImageView.this.txt_info;
                    textView.setText("height : " + obj + " step : " + obj2);
                    float parseInt = ((float) Integer.parseInt(obj)) / Float.parseFloat(obj2);
                    CustomImageView.this.height_calibrate = ((float) Math.sqrt(Math.pow((double) Math.abs(CustomImageView.this.cur_data.points.get(0).x - CustomImageView.this.cur_data.points.get(1).x), 2.0d) + Math.pow((double) Math.abs(CustomImageView.this.cur_data.points.get(0).y - CustomImageView.this.cur_data.points.get(1).y), 2.0d))) / parseInt;
                    create.dismiss();
                    CustomImageView.this.b_showmeter = false;
                }
            }
        });
        create.show();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setMatrix(this.matrix);
        canvas.drawBitmap(this.canvasBitmap, 0.0f, 0.0f, this.canvasPaint);
        Path path = this.temppath;
        if (path != null) {
            canvas.drawPath(path, this.drawPaint);
        }
        Rect rect = this.temprect;
        if (rect != null) {
            canvas.drawRect(rect, this.drawRect);
        }
    }
}