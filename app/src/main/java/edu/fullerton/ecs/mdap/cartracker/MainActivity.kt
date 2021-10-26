package edu.fullerton.ecs.mdap.cartracker

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import edu.fullerton.ecs.mdap.cartracker.databinding.ActivityMainBinding
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil

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
        /** Create data binding to the layout. The first parameter is the activity class
         * bound to the layout. The second parameter is the ID of the layout file.
         */
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        /** We assign our tracker object to the tracker variable inside the layout.
         * We also assign this class as the lifecyle owner so the system knows when to store
         * and retrieve the data in cases when the application crashes or rotates.
         */
        binding.tracker = tracker
        binding.lifecycleOwner = this

        /** TODO: Create observers for countPerMin and minElapsed to update
         * the corresponding text views.
         */
        tracker.minElapsed.observe(this){ newMinElapsed ->
            binding.minElapsed.text = newMinElapsed.toString()
        }
        tracker.countPerMin.observe(this){ newCountPerMin ->
            binding.carPerMinute.text = newCountPerMin.toString()
        }
        binding.addCar.setOnClickListener {
            /** Calling increment will update the value of count and therefore
             * triggers the lambda function we declared above.
             */
            tracker.increment()
        }
    }
}