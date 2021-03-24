package frc.robot;

//WPILIB imports
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

//CTRE imports
import com.ctre.phoenix.motorcontrol.can.*;

//Other imports
import frc.robot.handlers.*;
import java.util.*;

public class Robot extends TimedRobot {

  // --------------------CONSTANTS--------------------
  private final double INTAKE_SPEED = 0.75;
  private final double SHOOTER_SPEED = 1.0;
  private final double CONVEYOR_SPEED = -0.6;
  private int REVERSE = 1;

  // robot
  private DifferentialDrive robot;

  // motor controllers
  private WPI_VictorSPX masterLeft;
  private WPI_VictorSPX slaveLeft;
  private WPI_VictorSPX masterRight;
  private WPI_VictorSPX slaveRight;
  private WPI_TalonSRX intakeMotor; // TODO: determine if used
  private WPI_TalonSRX conveyorMotor; // TODO: determine if used
  private WPI_TalonSRX shooterMotorL; // TODO: determine if used
  private WPI_TalonSRX shooterMotorR; // TODO: determine if used
  private WPI_VictorSPX conveyorFront; // TODO: determine if used

  // speed controller groups
  private SpeedControllerGroup mGroupLeft;
  private SpeedControllerGroup mGroupRight;

  // controllers
  private XboxController driver1; // Utilities (Shooting, aiming, etc.)
  private XboxController driver2; // Tank Drive

  // pneumatics
  private Compressor compressor; // TODO: determine if used
  private DoubleSolenoid intakeSol; // TODO: determine if used 
  private DoubleSolenoid stopperSol; // TODO: determine if used

  // Handlers
  private final ArrayList<IRobotEventHandler> EVENT_HANDLERS = new ArrayList<>();

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
    intakeMotor = new WPI_TalonSRX(4); // TODO: determine if used
    conveyorMotor = new WPI_TalonSRX(5); // TODO: determine if used
    shooterMotorL = new WPI_TalonSRX(6); // shooter motor left // TODO: determine if used
    shooterMotorR = new WPI_TalonSRX(7); // shooter motor right // TODO: determine if used
    conveyorFront = new WPI_VictorSPX(8); // TODO: determine if used
    // pneumatics
    compressor = new Compressor(); // TODO: determine if used
    intakeSol = new DoubleSolenoid(60, 0, 1); // TODO: determine if used
    stopperSol = new DoubleSolenoid(60, 2, 3); // TODO: determine if used
    // robot
    robot = new DifferentialDrive(mGroupLeft, mGroupRight);
    // controllers
    driver1 = new XboxController(0);
    driver2 = new XboxController(1);
    // invert shooter left because it goe s counter clockwise
    shooterMotorR.setInverted(true);

    // Event Handlers
    EVENT_HANDLERS.add(new CompressorHandler(compressor));
    EVENT_HANDLERS.add(new GoForward(robot));
    EVENT_HANDLERS.add(new TankDriveHandler(robot, driver2));
    EVENT_HANDLERS.add(new LimeLightHandler(robot, driver1));

    EVENT_HANDLERS.forEach(IRobotEventHandler::robotInit);
  }

  @Override
  public void disabledInit() {
    EVENT_HANDLERS.forEach(IRobotEventHandler::disabledInit);
  }

  @Override
  public void autonomousInit() {
    EVENT_HANDLERS.forEach(IRobotEventHandler::autonomousInit);
  }

  @Override
  public void autonomousPeriodic() {
    EVENT_HANDLERS.forEach(IRobotEventHandler::autonomousPeriodic);
  }

  // only executes once when teleop starts
  @Override
  public void teleopInit() {
    EVENT_HANDLERS.forEach(IRobotEventHandler::teleopInit);
  }

  // loops over itself (every ~.02 seconds) until disabled
  @Override
  public void teleopPeriodic() {
    EVENT_HANDLERS.forEach(IRobotEventHandler::teleopPeriodic);
    // TODO: Determine if this is used
    if (driver1.getBumperPressed(Hand.kLeft)) {
      REVERSE *= -1;
    }
    if (driver1.getBButton()) {
      stopperSol.set(Value.kForward);
    } else {
      stopperSol.set(Value.kReverse);
    }
    if (driver1.getAButton()) {
      intakeSol.set(Value.kForward);
      intakeMotor.set(INTAKE_SPEED * REVERSE);
      conveyorFront.set(INTAKE_SPEED * REVERSE);
    } else {
      intakeSol.set(Value.kReverse);
      intakeMotor.stopMotor();
      conveyorFront.stopMotor();
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
  }
}