import java.util.ArrayList;

/**
 * Write a description of class EnemyManager here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class EnemyManager extends Object
{
    private Player player = null;
    
    public EnemyManager(Player chosenPlayer)
    {
        super(0,0);
        this.player = chosenPlayer;
    }
    
    @Override
    public void awake()
    {
        
    }
    
    @Override
    public void update()
    {
        
    }
    
    public Enemy SpawnEnemy()
    {
        EnemyTypes testEnemyType = EnemyTypes.SQUARE;//Sets up the enemyType
        EnemyStats testEnemyStats = new EnemyStats(1,1,1);//Sets up the stats of the enemy
        EnemyAI testEnemyAI = new EnemyAI();//Sets up a AI
        Enemy testEnemy = new Enemy(testEnemyType,testEnemyStats,testEnemyAI);
        return testEnemy;
    }
}