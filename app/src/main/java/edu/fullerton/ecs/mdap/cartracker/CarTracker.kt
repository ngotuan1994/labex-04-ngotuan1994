package edu.fullerton.ecs.mdap.cartracker

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalTime

/** Class that keeps track of cars that pass through a crosswalk. **/
class CarTracker() {

    /** Tracks the time that a car first passed through a crosswalk and the
     * most recent time it passed through a crosswalk.
     */
    private var firstLog: LocalTime?
    private var lastLog: LocalTime?

    /** A backing property for the count property that stores the total number
     * of cars that passed through a crosswalk. Backing properties often begin
     * with an _. It is private so it is only editable from inside the class.
     */
    private var _count: Int
    private var _minElapsed: Int // Minutes passed since the first entry.
    private var _countPerMin: Double // Number of cars that pass through the
    // crosswalk every minute.

    /** The count property is backed by _count. This property can only return a
     * value because it has a getter (get()), but no setter (set()). Specifically,
     * the property returns the value of _count. We use this design to ensure
     * that the _count only changes when the increment function is called (see below).
     */
    var count: Int
        get() { // getter function
            return _count
        }

    var minElapsed: Int
        get() {
            return _minElapsed
        }

    var countPerMin: Double
        get() {
            return _countPerMin
        }

    init {
        firstLog = null
        lastLog = null

        // Initialize properties and their backing properties
        _count = 0
        _minElapsed = 0
        _countPerMin = 0.0

        count = 0
        minElapsed = 0
        countPerMin = 0.0
    }

    /** Function that increments the total car count. It also updates the values of
     * related properties including minElapsed and countPerMin.
     */
    @RequiresApi(Build.VERSION_CODES.O) // Required annotation to use LocalTime
    fun increment() {
        // Set time of the first log
        if (firstLog == null) {
            firstLog = LocalTime.now()
        }
        // The most recent log is the last log
        lastLog = LocalTime.now()

        // Increase car count by one and updated related properties.
        _count++
        _minElapsed = minElapsed()
        _countPerMin = countPerMin()
    }

    // Number of minutes passed since the first logged car.
    @RequiresApi(Build.VERSION_CODES.O) // Required annotation to use LocalTime
    private fun minElapsed(): Int {
        if (firstLog == null || lastLog === null) { // No data logged.
            return 0
        }
        // Compute minutes duration from the last log to the first log.
        return lastLog!!.minute - firstLog!!.minute
    }

    // Average number of cars that pass through the crosswalk
    @RequiresApi(Build.VERSION_CODES.O) // Required annotation to use LocalTime
    private fun countPerMin(): Double {
        if (minElapsed() < 1) {
            // Return the total number of cars if time elapsed is less than a minute
            return count.toDouble()
        } else {
            return count.toDouble() / minElapsed()
        }
    }
}