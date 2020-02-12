package frc.robot;

//WPILIB imports

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Timer;

//CTRE imports

import com.ctre.phoenix.motorcontrol.can.*;

public class Robot extends TimedRobot {

  //constants
  private final double INTAKE_AIR_PULSE_TIME = 0.3;

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
  private WPI_TalonSRX conveyorMotor;
  private WPI_TalonSRX shooterMotorL;
  private WPI_TalonSRX shooterMotorR;

  //controllers
  private XboxController driver1;
  private XboxController driver2;

  //pneumatics
  private Compressor _compressor;
  private Solenoid extendIntake;
  private Solenoid retractIntake;

  //timers
  private final Timer extTimer = new Timer();
  private final Timer retTimer = new Timer();

  //prevent breaking intake code
  private boolean canIntake = true;

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
    intakeMotor = new WPI_TalonSRX(0);
    conveyorMotor = new WPI_TalonSRX(1);
    shooterMotorL = new WPI_TalonSRX(2); //shooter motor left?
    shooterMotorR = new WPI_TalonSRX(3); //shooter motor right?
    //pneumatics
    _compressor = new Compressor();
    extendIntake = new Solenoid(60, 0);
    retractIntake = new Solenoid(60, 1);
    //robot
    _robot = new DifferentialDrive(mGroupLeft, mGroupRight);
    //controllers
    driver1 = new XboxController(0);
    driver2 = new XboxController(1);
  }

  @Override
  public void teleopInit() {
    //start compressor
    _compressor.start();
  }
 
  @Override
  public void teleopPeriodic() {
    double left = driver1.getY(Hand.kLeft);
    double right = driver1.getY(Hand.kRight);
    _robot.tankDrive(left, right);
    //intake
    if (driver1.getBumper(Hand.kRight)) {
      if (canIntake) {
        //when bumper not pressed on the last check but is on the current check
        if (driver1.getBumperPressed(Hand.kRight)) {
          extTimer.start();
          extendIntake.set(true);
        }
        intakeMotor.set(0.75);
        canIntake = false; //disable intake
      }
    }
    //after extTimer reaches INTAKE_AIR_PULSE_TIME
    if (extTimer.hasPeriodPassed(INTAKE_AIR_PULSE_TIME)) {
      extendIntake.set(false);
      //when bumper released
      if (driver1.getBumperReleased(Hand.kRight)) {
        retTimer.start();
        retractIntake.set(true);
        extTimer.stop();
        extTimer.reset();
      }
    }
    //after retTimer reaches INTAKE_AIR_PULSE_TIME
    if (retTimer.hasPeriodPassed(INTAKE_AIR_PULSE_TIME)) {
      retTimer.stop();
      retTimer.reset();
      retractIntake.set(false);
      intakeMotor.stopMotor();
      canIntake = true; //you can use intake again
    }
    //conveyor
    if (driver1.getBButton()) { //should i move this to driver 2
      conveyorMotor.set(0.75);
    } else {
      conveyorMotor.stopMotor();
    }
    //shooter
    double triggerR = (driver1.getRawAxis(5) + 1) / 2; //trigger goes from -1 to 1
    if (triggerR > 0) {
      shooterMotorL.set(triggerR);
      shooterMotorR.set(triggerR);
    } else {
      shooterMotorR.stopMotor();
      shooterMotorL.stopMotor();
    }
  }
} 