import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

fun Fragment.startCountdownTimer(
    durationMillis: Long,
    onTick: (Long) -> Unit,
    onFinish: () -> Unit
) {
    var isTimerFinished = false

    return viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {

        private var countdownJob: Job? = null

        fun onCreate() {
            countdownJob = viewLifecycleOwner.lifecycleScope.launch {
                for (millisUntilFinished in durationMillis downTo 0 step 1000) {
                    onTick(millisUntilFinished)
                    delay(1000)
                }
                isTimerFinished = true
                onFinish()
            }
        }

        fun onDestroy() {
            countdownJob?.cancel()
        }
    })
}
