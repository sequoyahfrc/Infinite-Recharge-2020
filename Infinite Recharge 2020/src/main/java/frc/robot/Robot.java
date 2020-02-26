package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;

//WPILIB imports

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.UsbCameraInfo;

//CTRE imports

import com.ctre.phoenix.motorcontrol.can.*;

@SuppressWarnings("unused") // prevent annoying warnings
public class Robot extends TimedRobot {

  // constants
  private final double INTAKE_AIR_PULSE_TIME = 0.3;
  private final double INTAKE_SPEED = 1.0;
  private final double SHOOTER_SPEED = 1.0;
  private final double CONVEYOR_SPEED = 0.8;

  // robot
  private DifferentialDrive _robot;

  // motor controllers
  private WPI_VictorSPX masterLeft;
  private WPI_VictorSPX slaveLeft;
  private WPI_VictorSPX masterRight;
  private WPI_VictorSPX slaveRight;

  // speed controller groups
  private SpeedControllerGroup mGroupLeft;
  private SpeedControllerGroup mGroupRight;

  private WPI_TalonSRX intakeMotor;
  private WPI_TalonSRX conveyorMotor;
  private WPI_TalonSRX shooterMotorL;
  private WPI_TalonSRX shooterMotorR;

  // controllers
  private XboxController driver1;
  private XboxController driver2;

  // pneumatics
  private Compressor _compressor;
  private DoubleSolenoid intakeSol; // Value.kForward is extend Value.kReverse is retract

  // controls
  private MotorButtonBinding xButton;
  private MotorButtonBinding yButton;
  private MotorButtonBinding aButton;
  private MotorButtonBinding bButton;

  //timers
  private Timer goForwardTimer;

  //vision stuff
  private UsbCamera camera;

  @Override
  public void robotInit() {
    // victors are for drive system, talons are for accessories
    // rear motors are slaves front motors are masters
    // left motor controllers for drive
    masterLeft = new WPI_VictorSPX(0);
    slaveLeft = new WPI_VictorSPX(1);
    // right motor controllers for drive
    masterRight = new WPI_VictorSPX(2);
    slaveRight = new WPI_VictorSPX(3);
    // speed controllers
    mGroupLeft = new SpeedControllerGroup(masterLeft, slaveLeft);
    mGroupRight = new SpeedControllerGroup(masterRight, slaveRight);
    // talons
    intakeMotor = new WPI_TalonSRX(4);
    conveyorMotor = new WPI_TalonSRX(5);
    shooterMotorL = new WPI_TalonSRX(6); // shooter motor left
    shooterMotorR = new WPI_TalonSRX(7); // shooter motor right
    // pneumatics
    _compressor = new Compressor();
    intakeSol = new DoubleSolenoid(60, 0, 1);
    // robot
    _robot = new DifferentialDrive(mGroupLeft, mGroupRight);
    // controllers
    driver1 = new XboxController(0);
    driver2 = new XboxController(1);
    // invert shooter left because it goes counter clockwise
    shooterMotorR.setInverted(true);
    // controls
    xButton = new MotorButtonBinding(CONVEYOR_SPEED, conveyorMotor);
    yButton = new MotorButtonBinding(SHOOTER_SPEED, shooterMotorL, shooterMotorR);
    aButton = new MotorButtonBinding(INTAKE_SPEED, intakeMotor);
    //vision
    camera = CameraServer.getInstance().startAutomaticCapture();
    camera.setBrightness(50);
  }

  @Override
  public void autonomousInit() {
    if (goForwardTimer.get() != 0) {
      goForwardTimer.stop();
      goForwardTimer.reset();
    }
    goForwardTimer.start();
  }

  @Override
  public void autonomousPeriodic() {
    if (goForwardTimer.hasPeriodPassed(1)) {
      _robot.stopMotor();
    } else {
      _robot.tankDrive(0.4, 0.4);
    }
  }

  // only executes once when teleop starts
  @Override
  public void teleopInit() {
    // start compressor
    _compressor.start();
  }

  // loops over itself (every ~.02 seconds) until disabled
  @Override
  public void teleopPeriodic() {
    final double left = driver2.getY(Hand.kLeft);
    final double right = driver2.getY(Hand.kRight);
    _robot.tankDrive(left, right);
    // controls
        // xButton.SetMotor(driver1.getXButton()); // conveyor
    // yButton.SetMotor(driver1.getYButton()); // shooter
    // aButton.SetMotor(driver1.getAButton()); // intake

    if (driver1.getBButton()) {
      intakeSol.set(Value.kForward);
    } else {
      intakeSol.set(Value.kReverse);
    }
    if (driver1.getYButton()) {
      shooterMotorL.set(SHOOTER_SPEED);
      shooterMotorR.set(SHOOTER_SPEED);
    } else {
      shooterMotorL.stopMotor();
      shooterMotorR.stopMotor();
    }
    if (driver1.getXButton()) {
      conveyorMotor.set(CONVEYOR_SPEED);
    } else {
      conveyorMotor.stopMotor();
    }
    if (driver1.getAButton()) {
      intakeMotor.set(INTAKE_SPEED);
    } else {
      intakeMotor.stopMotor();
    }
  }
}