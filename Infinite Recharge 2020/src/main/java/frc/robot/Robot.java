package frc.robot;

//WPILIB imports

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.cscore.*;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.vision.VisionPipeline;
import edu.wpi.first.vision.VisionThread;

//CTRE imports

import com.ctre.phoenix.motorcontrol.can.*;

//OpenCV/CSCore imports

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

//Other imports

import java.awt.image.BufferedImage;

@SuppressWarnings("unused") // prevent annoying warnings
public class Robot extends TimedRobot {

  // constants
  private final double INTAKE_AIR_PULSE_TIME = 0.3;
  private final double INTAKE_SPEED = 1.0;
  private final double SHOOTER_SPEED = 1.0;
  private final double CONVEYOR_SPEED = 0.4;
  private final int[] CAMERA_RES = new int[] { 640, 480 };
  private final double AUTOAIM_TURN_SPEED = 0.1;

  // robot
  private DifferentialDrive _robot;

  // motor controllers
  private WPI_VictorSPX masterLeft;
  private WPI_VictorSPX slaveLeft;
  private WPI_VictorSPX masterRight;
  private WPI_VictorSPX slaveRight;
  private WPI_TalonSRX intakeMotor;
  private WPI_TalonSRX conveyorMotor;
  private WPI_TalonSRX shooterMotorL;
  private WPI_TalonSRX shooterMotorR;

  // speed controller groups
  private SpeedControllerGroup mGroupLeft;
  private SpeedControllerGroup mGroupRight;

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
  private Timer goForwardTimer = new Timer();

  //vision stuff
  private UsbCamera camera;
  private CameraServer server;
  private boolean lookingForTarget = false;
  private int targetDir = 0;
  private GRIPVisionProcessor processor;

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
    server = CameraServer.getInstance();
    camera = server.startAutomaticCapture();
    camera.setBrightness(50);
    camera.setExposureManual(40);
    camera.setExposureHoldCurrent();
    camera.setResolution(CAMERA_RES[0], CAMERA_RES[1]);
    processor = new GRIPVisionProcessor();
  }

  public Mat getFrame() {
    Mat m = new Mat();
    //get frame
    do {/* run grabFrame() at least once*/ } while (server.getVideo().grabFrame(m) == 0);
    return m;
  }

  public double getBlobX() {
    KeyPoint[] blobs = processor.findBlobsOutput().toArray();
    if (blobs.length > 0) {
      return blobs[0].pt.x;
    } else {
      return -1;
    }
  }

  public boolean isWithinMargin(double v, double target, double range) {
    return (v >= target - range) && (v <= target + range);
  }

  public void runGRIPPipeLine() {
    processor.process(getFrame());
  }

  @Override
  public void disabledInit() {
    goForwardTimer.stop();
    goForwardTimer.reset();
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
    //GRIP pipeline
    runGRIPPipeLine();

    //drive
    final double left = driver2.getY(Hand.kLeft);
    final double right = driver2.getY(Hand.kRight);
    _robot.tankDrive(left, right); //made a poll on this

    // controls
    if (driver1.getAButton()) {
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

    //auto aim
    if (driver1.getPOV() > -1) {
      switch (driver1.getPOV()) {
        case 90:
          // right
          targetDir = 1;
          break;
        case 270:
          // left
          targetDir = -1;
          break;
        case 0:
        case 180:
          lookingForTarget = false;
          targetDir = 0;
          break;
      }
    }
    if (lookingForTarget) {
      //if target is in front of robot, stop looking for a target
      double bx = getBlobX();
      if (isWithinMargin(bx, CAMERA_RES[0] / 2, 25)) {
        _robot.stopMotor();
        lookingForTarget = false;
        targetDir = 0;
      } else {
        _robot.tankDrive(targetDir * AUTOAIM_TURN_SPEED, -targetDir * AUTOAIM_TURN_SPEED);
      }
    }
  }
}