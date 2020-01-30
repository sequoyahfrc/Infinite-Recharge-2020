package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;

public class Robot extends TimedRobot {
  private DifferentialDrive _robot;
  private XboxController driver1;

  //motor controllers
  private VictorSPX masterLeft;
  private VictorSPX slaveLeft;
  private VictorSPX masterRight;
  private VictorSPX slaveRight;
  private TalonSRX talon1;
  private TalonSRX talon2;
  private TalonSRX talon3;
  private TalonSRX talon4;

  @Override
  public void robotInit() {

    //TODO: ask shane what ports things go in
    //left motor controllers for drive
    masterLeft = new VictorSPX(0);
    slaveLeft = new VictorSPX(1);
    //right motor controllers for drive
    masterRight = new VictorSPX(2);
    slaveRight = new VictorSPX(3);
    //talons
    talon1 = new TalonSRX(4);
    talon2 = new TalonSRX(5);
    talon3 = new TalonSRX(6);
    talon4 = new TalonSRX(7);
    //robot
    _robot = new DifferentialDrive(new PWMVictorSPX(0), new PWMVictorSPX(1)); //TODO: change this to support our Victor SPX
    //controllers
    driver1 = new XboxController(0);
  }

  @Override
  public void teleopPeriodic() {
    double left = driver1.getY(GenericHID.Hand.kLeft);
    double right = driver1.getY(GenericHID.Hand.kRight);
    _robot.tankDrive(left, right);
  }
} 