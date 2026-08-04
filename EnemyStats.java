
/**
 * 
 */
public class EnemyStats
{
    
    private double health;
    private double speed;
    private double attackRate;
    
    
    public EnemyStats(double chosenHealth, double chosenSpeed, double chosenAttackRate)
    {
        this.health = chosenHealth;
        this.speed = chosenSpeed;
        this.attackRate = chosenAttackRate;
    }
    
    public double getHealth() {
        return this.health;
    }
    
    public double getSpeed() {
        return this.speed;
    }
    
    public double getAttackRate() {
        return this.attackRate;
    }
}
