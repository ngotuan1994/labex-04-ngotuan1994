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

        // Assign initial value to views
        binding.totalCar.text = tracker.count.toString()
        binding.carPerMinute.text = tracker.countPerMin.toString()
        binding.minElapsed.text = tracker.minElapsed.toString()

        // Increment car count when Add Car is clicked
        binding.addCar.setOnClickListener {
            tracker.increment()
            // Update view values using data from the CarTracker object
            binding.totalCar.text = tracker.count.toString()
            binding.carPerMinute.text = tracker.countPerMin.toString()
            binding.minElapsed.text = tracker.minElapsed.toString()
        }
    }
}