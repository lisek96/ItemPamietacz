package rafalwojcik.prm.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class PaintView (
    context: Context,
    attributeSet: AttributeSet):
    View(context, attributeSet) {
    var photo : Bitmap? = null
    private val paths = mutableListOf<Path>()
    private val whitePaint = Paint().apply{
        color = Color.WHITE
    }
    private val blackPaint = Paint().apply{
        color=Color.BLACK
        strokeWidth = 40f
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
    }

    override fun onDraw(canvas: Canvas) {
        drawCanvas(canvas);
        drawPaths(canvas)
    }

    private fun drawPaths(canvas: Canvas) {
        paths.forEach {
            canvas.drawPath(it, blackPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                Path().apply {
                    moveTo(event.x, event.y)
                    lineTo(event.x, event.y)
                }.let {
                    paths.add(it)
                }
            }
            MotionEvent.ACTION_MOVE -> {
                paths.last().lineTo(event.x, event.y)
            }
        }
        invalidate()
        return true
    }

    private fun drawCanvas(canvas: Canvas) {
        val dst = Rect(0, 0, width, height)
        val photo = this.photo;
        if (photo != null) {
            canvas.drawBitmap(photo, null, dst, null);
        }else{
            canvas.drawRect(dst, whitePaint)
        }
    }

    fun getBitmap(): Bitmap {
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        Canvas(bmp).let {
            drawCanvas(it)
            drawPaths(it)
        }
        return bmp
    }

}