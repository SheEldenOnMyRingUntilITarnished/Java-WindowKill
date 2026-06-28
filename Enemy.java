
/**
 * Write a description of class Enemy here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Enemy
{    
    private Object object;
    private EnemyTypes type;
    private EnemyStats stats;
    private EnemyAI ai;
    
    public Enemy(Object chosenObject, EnemyTypes chosenType, EnemyStats chosenStats, EnemyAI chosenAI)
    {
        this.object = chosenObject;
        this.type = chosenType;
        this.stats = chosenStats;
        this.ai = chosenAI;
    }
    
    public void updateEnemy(int targetX, int targetY)
    {
        double speed = stats.getSpeed();
        double angle = Math.atan2(targetY - getYPosition(), targetX - getXPosition());
        
        this.object.updatePosition((int)Math.round(speed * Math.cos(angle)), (int)Math.round(speed * Math.sin(angle)));
    }
    
    //Returns the X position of the object held inside of the enemy
    public int getXPosition()
    {
        return this.object.getXPosition();
    }
    
    //Returns the Y position of the object held inside of the enemy
    public int getYPosition()
    {
        return this.object.getYPosition();
    }
}