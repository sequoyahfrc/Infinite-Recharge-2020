package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TowerSubsystem extends SubsystemBase {

  private final WPI_TalonSRX towerL, towerR;
  private final Timer myTimer;

  public TowerSubsystem() {
    towerR = new WPI_TalonSRX(7);
    towerR.setInverted(true);
    towerL = new WPI_TalonSRX(5);
    myTimer = new Timer();
  }

  public void setTower(double value) {
    towerL.set(value * 0.5);
    towerR.set(value);
  }

  public void oneBall(double value) {
    myTimer.reset();
    myTimer.start();
    while (myTimer.get() <0.3){
      setTower(value);
    }
  }

  public void purge(double value) {
    myTimer.reset();
    myTimer.start();
    while (myTimer.get() <6){
      setTower(value);
    }
  }
  public void stopTower() {
    towerL.stopMotor();
    towerR.stopMotor();
  }

}