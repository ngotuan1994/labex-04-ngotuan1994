package edu.fullerton.ecs.mdap.cartracker

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import edu.fullerton.ecs.mdap.cartracker.databinding.ActivityMainBinding

/** Class for creating the main view **/
class MainActivity : AppCompatActivity() {

    // Crete a CarTracker object
    var tracker = CarTracker()

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