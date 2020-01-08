package se.hig.dvg329.algomaze.control;

/**
 * The {@code Timer} class is used to measure elapsed time between two points using the {@link Timer#startTimer()} and
 * {@link Timer#endTimer()} methods.
 * @author Thomas Lundgren
 * @version 1.0.0
 * @since 1.0.0
 */
class Timer {
	
	private long startTime, endTime;
	private double executionTime;
	
	/**
	 * Starts the timer.
	 */
	public void startTimer() {
		startTime = System.nanoTime();
	}
	
	/**
	 * Stops the timer and returns the time elapsed since this {@code Timer} was started.
	 * @return the time elapsed since this {@code Timer} was started.
	 */
	public double endTimer() {
		endTime = System.nanoTime();
		executionTime = (double) (endTime - startTime)/1000000;
		return executionTime;
	}
	
}
