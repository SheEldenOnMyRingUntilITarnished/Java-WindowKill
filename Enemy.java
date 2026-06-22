
/**
 * Write a description of class Enemy here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Enemy
{
    private int xPosition;
    private int yPosition;
    
    private EnemyTypes type;
    private EnemyStats stats;
    private EnemyAI ai;
    
    public Enemy(EnemyTypes chosenType, EnemyStats chosenStats, EnemyAI chosenAI)
    {
        this.type = chosenType;
        this.stats = chosenStats;
        this.ai = chosenAI;
    }
}