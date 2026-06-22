import java.util.ArrayList;

/**
 * Write a description of class EnemyManager here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class EnemyManager
{
    private ArrayList<Enemy> enemyHolder = new ArrayList<Enemy>();
    
    public void SpawnEnemy()
    {
        EnemyTypes testEnemyType = EnemyTypes.SQUARE;
        EnemyStats testEnemyStats = new EnemyStats(1,1,1);
        EnemyAI testEnemyAI = new EnemyAI();
        Enemy testEnemy = new Enemy(testEnemyType,testEnemyStats,testEnemyAI);
        enemyHolder.add(testEnemy);
    }
}