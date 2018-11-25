/*
 * Project: Pathfinder
 * Author: Austin Rovge
 * Date: 8/4/2018
 */

package algorithms;

import javafx.scene.control.Label;
import node.Node;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

/**
 * This is an abstract class for an algorithms object.
 * It has two methods, one for path finding and another for returning the operation time.
 */
abstract public class Algorithm {
    protected long time;

    abstract public void runPath(Node currentNode, boolean useDiagonalNodes, Label timeLabel);

    /**
     * This is a helper method used for helping format time in the time Label object.
     */
    protected void formatTime(Label timeLabel) {

        // used for unit conversion
        final double siUnitDiff = 1000;
        final int minInHour = 60;
        final int secInMin = 60;
        final int milliInSec = 1000;

        long time = this.getLastOperationTime();

        // custom string added to time label object
        String timeStr = "";

        DecimalFormat threeDigitFormat = new DecimalFormat("###");
        DecimalFormat multipleUnitFormat = new DecimalFormat("00");

        // if statements to determine how to display time in what units
        if (time < siUnitDiff) {

            // nanoseconds
            timeStr = threeDigitFormat.format(time) + " nanoseconds";
        } else if (time / siUnitDiff < siUnitDiff) {

            // microseconds
            timeStr = TimeUnit.NANOSECONDS.toMicros(time) + "." +
            threeDigitFormat.format(time % siUnitDiff) + " microseconds";
        } else if (time / (siUnitDiff * siUnitDiff) < siUnitDiff) {

            // milliseconds
            timeStr = TimeUnit.NANOSECONDS.toMillis(time) + "." +
            threeDigitFormat.format(TimeUnit.NANOSECONDS.toMicros((long)
            (time % (siUnitDiff * siUnitDiff)))) + " milliseconds";
        } else if (time / (siUnitDiff * siUnitDiff * siUnitDiff) < siUnitDiff) {

            // larger than nano, micro, and milli
            timeStr = multipleUnitFormat.format(TimeUnit.NANOSECONDS.toMinutes(time)
            % minInHour) + ":" +
            multipleUnitFormat.format(TimeUnit.NANOSECONDS.toSeconds(time)
            % secInMin) + "." +
            multipleUnitFormat.format(TimeUnit.NANOSECONDS.toMillis(time) % milliInSec);

        }

        timeLabel.setText("Time elapsed: " + timeStr);
    }

    /**
     * This method returns the last operation time. It is calculated when the runPath() method is called.
     * @return long of the time taken to run the path in nanoseconds
     */
    private long getLastOperationTime() {
        return this.time;
    }
}
