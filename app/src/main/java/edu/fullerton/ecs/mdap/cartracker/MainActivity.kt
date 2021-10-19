package edu.fullerton.ecs.mdap.cartracker

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import edu.fullerton.ecs.mdap.cartracker.databinding.ActivityMainBinding
import androidx.activity.viewModels

/** Class for creating the main view **/
class MainActivity : AppCompatActivity() {

    /** Crete a CarTracker object that implements ViewModels. by ViewModels()
     * uses property delegation. This might look familiar as it shares some concepts
     * with interface delegation. In this case, the viewModels() property delegate
     * provides the getter and setter implementations for tracker. Specifically, it
     * stores and restores a copy of the tracker object so it retains values even
     * when the application is destroyed either when it crashes or rotates.
     */
    val tracker: CarTracker by viewModels()

    @RequiresApi(Build.VERSION_CODES.O) // Required annotation to use LocalTime
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Create view binding to the layout
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /** We attach an observer to the count LiveData property. We pass
         * this to indicate that the activity is the observer. Whenever
         * count's value changes, it will call the lambda function. As a
         * result, the text in all three views (total cars, cars per minute,
         * and minutes elapsed) will be updated according to the tracker
         * object.
         */
        tracker.count.observe(this) { newCount ->
            binding.totalCar.text = newCount.toString()
        }
        /** TODO: Create observers for countPerMin and minElapsed to update
         * the corresponding text views.
         */

        binding.addCar.setOnClickListener {
            /** Calling increment will update the value of count and therefore
             * triggers the lambda function we declared above.
             */
            tracker.increment()
        }
    }
}