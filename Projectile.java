
/**
 * Write a description of class Projectile here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Projectile
{
    private Object object;
    
    private double speed;
    private double direction;
    private boolean targetPlayer;
    
    public Projectile(double projectileSpeed, double projectileDirection, boolean projectileTargetPlayer)
    {
        this.object = new Object(0,0,10,10);
        this.speed = projectileSpeed;
        this.direction = projectileDirection;
        this.targetPlayer = projectileTargetPlayer;
    }
    
    public int getXPosition()
    {
        return this.object.getXPosition();
    }
    
    public int getYPosition()
    {
        return this.object.getYPosition();
    }
    
    public Object getObject()
    {
        return this.object;
    }
    
    public double getSpeed()
    {
        return this.speed;
    }
    
    public double getDirection()
    {
        return this.direction;
    }
    
    public void setPosition(int chosenXPos, int chosenYPos)
    {
        this.object.setPosition(chosenXPos,chosenYPos);
    }
    
    public void updatePosition(double chosenXPos, double chosenYPos)
    {
        this.object.updatePosition((int) chosenXPos, (int) chosenYPos);
    }
}