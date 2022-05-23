package rafalwojcik.prm.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.bumptech.glide.Glide

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
        strokeWidth = 15f
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
    }

    override fun onDraw(canvas: Canvas) {
        drawPhoto(canvas);
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

    private fun drawPhoto(canvas: Canvas) {
        val rect = Rect(0, 0, width, height)
        photo?.copy(Bitmap.Config.ARGB_8888, false)?.let {
            canvas.drawBitmap(it, null, rect, null)
        } ?: let {
            canvas.drawRect(rect, whitePaint)
        }
    }

    fun getBitmap(): Bitmap {
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888, false)
        Canvas(bmp).let {
            drawPhoto(it)
            drawPaths(it)
        }
        return bmp
    }

}