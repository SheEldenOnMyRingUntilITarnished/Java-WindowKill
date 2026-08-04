/**
 * 
 */
public class EnemyAI
{
    private Timer actionTimer = new Timer();
    private boolean isDashing = false;
    private int dashPhaseCount = 0;

    public EnemyAI()
    {
        actionTimer.setTimer(0);
    }

    public void updateAI(Enemy enemy, GameSystem gameSystem)
    {
        if (enemy == null || gameSystem == null) return;
        
        actionTimer.update();
        
        EnemyTypes type = enemy.getType();
        int targetX = enemy.getTargetX();
        int targetY = enemy.getTargetY();
        int enemyX = enemy.getXPosition();
        int enemyY = enemy.getYPosition();

        double dx = targetX - enemyX;
        double dy = targetY - enemyY;
        double distance = Math.hypot(dx, dy);
        double angle = Math.atan2(dy, dx);

        if (type == EnemyTypes.SQUARE)
        {
            double baseAccel = enemy.getStats().getSpeed() * 0.35;
            
            if (isDashing)
            {

                double dashSpeed = enemy.getStats().getSpeed() * 1.8;
                enemy.accelerate(Math.cos(angle) * dashSpeed, Math.sin(angle) * dashSpeed);
                
                if (actionTimer.timerHasPassed())
                {
                    dashPhaseCount++;
                    if (dashPhaseCount < 2)
                    {
                        actionTimer.setTimer(8);
                    }
                    else
                    {
                        isDashing = false;
                        dashPhaseCount = 0;
                        actionTimer.setTimer(180);
                    }
                }
            }
            else
            {
                enemy.accelerate(Math.cos(angle) * baseAccel, Math.sin(angle) * baseAccel);
                
                if (actionTimer.timerHasPassed())
                {
                    isDashing = true;
                    dashPhaseCount = 0;
                    actionTimer.setTimer(8);
                }
            }
        }
        else if (type == EnemyTypes.TRIANGLE)
        {
            double accel = enemy.getStats().getSpeed() * 0.4;
            if (distance < 120)
            {
                accel *= 0.35;
            }
            enemy.accelerate(Math.cos(angle) * accel, Math.sin(angle) * accel);
        }
        else if (type == EnemyTypes.CIRCLE)
        {
            if (isDashing)
            {
                double dashSpeed = enemy.getStats().getSpeed() * 2.2;
                enemy.accelerate(Math.cos(angle) * dashSpeed, Math.sin(angle) * dashSpeed);
                
                if (actionTimer.timerHasPassed())
                {
                    isDashing = false;
                    actionTimer.setTimer(40);
                }
            }
            else
            {
                if (actionTimer.timerHasPassed())
                {
                    isDashing = true;
                    actionTimer.setTimer(12);
                }
            }
        }
    }
}
