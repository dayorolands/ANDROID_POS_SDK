package com.interswitchng.smartpos.shared.utils

import android.animation.Animator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.util.TypedValue
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.interswitchng.smartpos.shared.models.core.IswLocal
import com.interswitchng.smartpos.shared.utilities.DeviceUtils
import com.interswitchng.smartpos.shared.utilities.DialogUtils
import com.interswitchng.smartpos.shared.utilities.Logger

object DisplayUtils {

    inline val Int.dp: Int
        get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            Resources.getSystem().displayMetrics
        ).toInt()



    internal inline fun getValueAnimator(
        forward: Boolean,
        duration: Long,
        interpolator: TimeInterpolator,
        crossinline updateListener: (progress: Float) -> Unit
    ): ValueAnimator {

        val a =
            if (forward) ValueAnimator.ofFloat(0f, 1f)
            else ValueAnimator.ofFloat(1f, 0f)

        a.addUpdateListener { updateListener(it.animatedValue as Float) }
        a.duration = duration
        a.interpolator = interpolator
        return a
    }

    /**
     * Add an action which will be invoked when the animation has ended.
     *
     * @return the [Animator.AnimatorListener] added to the Animator
     * @see Animator.end
     */
    inline fun Animator.doOnEnd(crossinline action: (animator: Animator) -> Unit) =
        addListener(onEnd = action)

    /**
     * Add an action which will be invoked when the animation has started.
     *
     * @return the [Animator.AnimatorListener] added to the Animator
     * @see Animator.start
     */
    inline fun Animator.doOnStart(crossinline action: (animator: Animator) -> Unit) =
        addListener(onStart = action)


    /**
     * Add a listener to this Animator using the provided actions.
     *
     * @return the [Animator.AnimatorListener] added to the Animator
     */
    inline fun Animator.addListener(
        crossinline onEnd: (animator: Animator) -> Unit = {},
        crossinline onStart: (animator: Animator) -> Unit = {},
        crossinline onCancel: (animator: Animator) -> Unit = {},
        crossinline onRepeat: (animator: Animator) -> Unit = {}
    ): Animator.AnimatorListener {
        val listener = object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animator: Animator) = onRepeat(animator)
            override fun onAnimationEnd(animator: Animator) = onEnd(animator)
            override fun onAnimationCancel(animator: Animator) = onCancel(animator)
            override fun onAnimationStart(animator: Animator) = onStart(animator)
        }
        addListener(listener)
        return listener
    }

    internal fun Fragment.toast(message: String, length: Int = Toast.LENGTH_LONG) {
        val ctx = requireContext()
        Toast.makeText(ctx, message, length).show()
    }

    internal fun Fragment.runWithInternet(handler: () -> Unit) {
        // ensure that device is connected to internet
        if (!DeviceUtils.isConnectedToInternet(requireContext())) {
            toast("Device is not connected to internet")
            // show no-network dialog
            DialogUtils.getNetworkDialog(requireContext()) {
                // trigger handler
                handler()
            }.show()
        } else {
            // trigger handler
            handler()
        }
    }
}
