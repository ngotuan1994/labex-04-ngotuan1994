package edu.fullerton.ecs.mdap.cartracker

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalTime

/** Class that keeps track of cars that pass through a crosswalk. **/
class CarTracker(): ViewModel() {

    /** Tracks the time that a car first passed through a crosswalk and the
     * most recent time it passed through a crosswalk.
     */
    private var firstLog: LocalTime?
    private var lastLog: LocalTime?

    /** A backing property for the count property that stores the total number
     * of cars that passed through a crosswalk. Backing properties often begin
     * with an _. It is private so it is only editable from inside the class.
     *
     * LiveData allow external entities to observe changes to their values so
     * they can react to such changes. They are often declared constant because
     * the object does not change. It is their internal value that changes.
     * MutableLiveData is a type of LiveData that allows changes.
     */
    private val _count = MutableLiveData(0)

    // TODO: Convert _minElapsed and _countPerMin into MutableLiveData objects
    private var _minElapsed: Int // Minutes passed since the first entry.
    private var _countPerMin: Double // Number of cars that pass per minute.

    /** The count property is backed by _count. This property can only return a
     * value because it has a getter (get()), but no setter (set()). Specifically,
     * the property returns the value of _count. We use this design to ensure
     * that the _count only changes when the increment function is called (see below).
     *
     * Count is declared as LiveData so that external entities cannot change its value.
     * Take note that although _count's type is MutableLiveData, it can be treated as
     * LiveData because MutableLiveData is a subclass of LiveData.
     */
    val count: LiveData<Int>
        get() { // getter function
            return _count
        }

    // TODO: Change minElapsed to use LiveData
    var minElapsed: Int
        get() {
            return _minElapsed
        }

    // TODO: Change countPerMin to use LiveData
    var countPerMin: Double
        get() {
            return _countPerMin
        }

    init {
        firstLog = null
        lastLog = null

        // Initialize properties and their backing properties
        _count.value = 0
        _minElapsed = 0
        _countPerMin = 0.0

        // No need to initialize count because it returns _count
        // TODO: remove unneessary initialization
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

        /** Increase car count by one. LiveData provides a value property
         * that holds the actual data. However, it is nullable because it
         * may not have an initial value. We know that value in fact has
         * a value because we initialized it. We us the !! (double bang)
         * notation to explicitly tell the compiler that we are absolutely
         * sure _count's value has been initialized.
         *
         * We call the inc() method to retrieve its value plus 1. We store
         * this value back to the property. This syntax follows the more
         * explicit increment pattern: x = x + 1
         */
            _count.value = _count.value!!.inc()

        // Updated related properties.
        /** TODO: Update the assignment statement to provide values from a
         * MutableLiveData object.
         */
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
            /** Return the total number of cars if time elapsed is less than a minute.
             * We us the !! (double bang) notation to explicitly tell the compiler
             * that we are absolutely sure count's value has been initialized.
             */
            return count.value!!.toDouble()
        } else {
            // Return the number of cars per minute elapsed
            // TODO: Update minElapsed to retrieve the value of a LiveData object
            return count.value!!.toDouble() / minElapsed
        }
    }
}