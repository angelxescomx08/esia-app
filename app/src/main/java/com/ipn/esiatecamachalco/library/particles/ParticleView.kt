package com.ipn.esiatecamachalco.library.particles

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlin.math.min
import kotlin.math.sqrt
import kotlin.random.Random


class ParticleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0
) : SurfaceView(context, attrs, defStyleAttr), SurfaceHolder.Callback {

    //private val TAG: String = this::class.java.simpleName

    companion object {
        private const val particleCount = 8
        //private const val particleMinRadius = 3
        //private const val particleMaxRadius = 5
        private const val particlesBackgroundColor = Color.BLACK
        private const val particleLinesEnabled = true
    }

    //private val particlesBackgroundColor = Color.parseColor("#08055C")

    data class Particle (
        var radius: Float,
        var x: Float,
        var y: Float,
        var vx: Int,
        var vy: Int,
        var alpha: Int
    )

    private val particles = mutableListOf<Particle>()
    private var surfaceViewThread: SurfaceViewThread? = null
    private var hasSurface: Boolean = false
    private var hasSetup = false

    private val path = Path()

    // Paints
    private val paintParticles: Paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        strokeWidth = 2F
        color = Color.WHITE
    }

    private val paintLines: Paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = 2F
        color = Color.WHITE
    }

    init {
        if (holder != null) holder.addCallback(this)
        hasSurface = false
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        hasSurface = true

        if (surfaceViewThread == null) {
            surfaceViewThread = SurfaceViewThread()
        }

        surfaceViewThread?.start()
    }

    fun start() {
        if (surfaceViewThread == null) {
            surfaceViewThread = SurfaceViewThread()

            if (hasSurface) {
                surfaceViewThread?.start()
            }
        }
    }

    fun stop() {
        surfaceViewThread?.requestExitAndWait()
        surfaceViewThread = null
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        hasSurface = false
        surfaceViewThread?.requestExitAndWait()
        surfaceViewThread = null
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, w: Int, h: Int) {}

    private fun setupParticles() {
        if (!hasSetup) {

            hasSetup = true
            particles.clear()

            val particleMinRadius:Int
            val particleMaxRadius:Int

            val metrics = resources.displayMetrics
            /*val num = metrics.densityDpi
            Log.d(TAG, "DEBUG: La densidad de pantalla es: $num");
            Toast.makeText(context,"La densidad de pantalla es: $num",Toast.LENGTH_LONG).show()*/

            metrics.densityDpi.apply {
                if (this <= DisplayMetrics.DENSITY_LOW ) {
                    particleMinRadius = 1
                    particleMaxRadius = 3
                }
                else if (this <= DisplayMetrics.DENSITY_MEDIUM) {
                    particleMinRadius = 3
                    particleMaxRadius = 5
                }
                else if (this <= DisplayMetrics.DENSITY_HIGH) {
                    particleMinRadius = 6
                    particleMaxRadius = 8
                }
                else if (this <= DisplayMetrics.DENSITY_XHIGH) {
                    particleMinRadius = 7
                    particleMaxRadius = 10
                }
                else if (this <= DisplayMetrics.DENSITY_XXHIGH) {
                    particleMinRadius = 9
                    particleMaxRadius = 12
                }
                else if (this <= DisplayMetrics.DENSITY_XXXHIGH) {
                    particleMinRadius = 11
                    particleMaxRadius = 15
                }
                else {
                    particleMinRadius = 1
                    particleMaxRadius = 1
                }
            }

            for (i in 0 until particleCount) {
                particles.add(
                    Particle(
                        Random.nextInt(particleMinRadius, particleMaxRadius).toFloat(),
                        Random.nextInt(0, width).toFloat(),
                        Random.nextInt(0, height).toFloat(),
                        Random.nextInt(-1, 1),
                        Random.nextInt(-1, 1),
                        Random.nextInt(150, 255)
                    )
                )
            }
        }
    }

    private inner class SurfaceViewThread : Thread() {

        private var running = true
        private var canvas: Canvas? = null

        override fun run() {
            setupParticles()

            while (running) {
                try {
                    canvas = holder.lockCanvas()

                    synchronized (holder) {
                        // Clear screen every frame
                        canvas?.drawColor(particlesBackgroundColor, PorterDuff.Mode.SRC)

                        for (i in 0 until particleCount) {
                            particles[i].x += particles[i].vx
                            particles[i].y += particles[i].vy

                            if (particles[i].x < 0) {
                                particles[i].x = width.toFloat()
                            } else if (particles[i].x > width) {
                                particles[i].x = 0F
                            }

                            if (particles[i].y < 0) {
                                particles[i].y = height.toFloat()
                            } else if (particles[i].y > height) {
                                particles[i].y = 0F
                            }

                            canvas?.let {
                                if (particleLinesEnabled) {
                                    for (j in 0 until particleCount) {
                                        if (i != j) {
                                            linkParticles(it, particles[i], particles[j])
                                        }
                                    }
                                }
                            }

                            paintParticles.alpha = particles[i].alpha
                            canvas?.drawCircle(particles[i].x, particles[i].y, particles[i].radius, paintParticles)
                        }
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    if (canvas != null) {
                        holder.unlockCanvasAndPost(canvas)
                    }
                }
            }
        }

        fun requestExitAndWait() {
            running = false

            try {
                join()
            } catch (e: InterruptedException) {
                // ignored
            }
        }
    }

    private var dx: Float = 0f
    private var dy: Float = 0f
    private var dist: Float = 0f
    private var distRatio: Float = 0f

    private fun linkParticles(canvas: Canvas, p1: Particle, p2: Particle) {
        dx = p1.x - p2.x
        dy = p1.y - p2.y
        dist = sqrt(dx * dx + dy * dy)

        if (dist < 220) {
            path.moveTo(p1.x, p1.y)
            path.lineTo(p2.x, p2.y)
            distRatio = (220 - dist) / 220

            paintLines.alpha = (min(p1.alpha, p2.alpha) * distRatio / 2).toInt()
            canvas.drawPath(path, paintLines)

            path.reset()
        }
    }
}