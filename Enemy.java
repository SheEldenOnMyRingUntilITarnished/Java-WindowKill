
/**
 * Write a description of class Enemy here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Enemy
{
    private int xPos;
    private int yPos;
    
    private EnemyTypes type;
    private EnemyStats stats;
    private EnemyAI ai;
    
    public Enemy(EnemyTypes chosenType, EnemyStats chosenStats, EnemyAI chosenAI)
    {
        this.type = chosenType;
        this.stats = chosenStats;
        this.ai = chosenAI;
    }
    
    public void updateEnemy()
    {
        xPos += 1;
        yPos += 1;
    }
    
    public int getXPosition()
    {
        return this.xPos;
    }
    
    public int getYPosition()
    {
        return this.yPos;
    }
}