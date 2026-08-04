
/**
 * Write a description of class Timer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Timer extends Object
{
    private double currentTime;
    public Timer()
    {
        super(0,0);
    }
    
    @Override
    public void update()
    {
        currentTime--;
    }
    
    public void setTimer(double setTime)
    {
        this.currentTime = setTime;
    }
    
    public boolean timerHasPassed()
    {
        return currentTime < 0;
    }
    
    public double getCurrentTime()
    {
        return this.currentTime;
    }
}