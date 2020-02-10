package frc.robot;

//WPILIB imports

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

//CTRE imports

import com.ctre.phoenix.motorcontrol.can.*;

public class Robot extends TimedRobot {

  //robot
  private DifferentialDrive _robot;

  //motor controllers
  private WPI_VictorSPX masterLeft;
  private WPI_VictorSPX slaveLeft;
  private WPI_VictorSPX masterRight;
  private WPI_VictorSPX slaveRight;

  //speed controller groups
  private SpeedControllerGroup mGroupLeft;
  private SpeedControllerGroup mGroupRight;

  //other motors TODO: refactor the talons for what they are used for
  private WPI_TalonSRX intakeMotor;
  private WPI_TalonSRX talon2;
  private WPI_TalonSRX talon3;
  private WPI_TalonSRX talon4;

  //controllers
  private XboxController driver1;
  private XboxController driver2;

  //pnuematics
  private Compressor _compressor;
  private Solenoid extendIntake;
  private Solenoid retractIntake;

  @Override
  public void robotInit() {

    //rear motors are slaves front motors are masters
    //left motor controllers for drive
    masterLeft = new WPI_VictorSPX(0);
    slaveLeft = new WPI_VictorSPX(1);
    //right motor controllers for drive
    masterRight = new WPI_VictorSPX(2);
    slaveRight = new WPI_VictorSPX(3);
    //speed controllers
    mGroupLeft = new SpeedControllerGroup(masterLeft, slaveLeft);
    mGroupRight = new SpeedControllerGroup(masterRight, slaveRight);
    //talons
    intakeMotor = new WPI_TalonSRX(4);
    talon2 = new WPI_TalonSRX(5);
    talon3 = new WPI_TalonSRX(6);
    talon4 = new WPI_TalonSRX(7);
    //pnuematics
    _compressor = new Compressor(8);
    extendIntake = new Solenoid(9);
    retractIntake = new Solenoid(10);
    //robot
    _robot = new DifferentialDrive(mGroupLeft, mGroupRight);
    //controllers
    driver1 = new XboxController(0);
    driver2 = new XboxController(1);

    //invert slave motors
    slaveLeft.setInverted(true);
    slaveRight.setInverted(true);
    //set solenoid pulse durations
    extendIntake.setPulseDuration(0.5);
    retractIntake.setPulseDuration(0.5);
    //start compressor
    _compressor.start();
  }

  @Override
  public void teleopPeriodic() {
    double left = driver1.getY(GenericHID.Hand.kLeft);
    double right = driver1.getY(GenericHID.Hand.kRight);
    _robot.tankDrive(left, right);
    if (driver1.getAButton()) {
      intakeMotor.set(0.5);
    } else {
      intakeMotor.stopMotor();
    }
    if (driver1.getXButton()) { //intake extend
      extendIntake.startPulse();
    }
    if (driver1.getYButton()) { //intake retract
      retractIntake.startPulse();
    }
  }
} 