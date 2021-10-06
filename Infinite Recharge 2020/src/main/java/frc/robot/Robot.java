package frc.robot;

//WPILIB imports
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

//CTRE imports
import com.ctre.phoenix.motorcontrol.can.*;

//Other imports
import frc.robot.handlers.*;
import java.util.*;

public class Robot extends TimedRobot {

  // --------------------CONSTANTS--------------------

  // robot
  private DifferentialDrive robot;

  // motor controllers
  private WPI_VictorSPX masterLeft;
  private WPI_VictorSPX slaveLeft;
  private WPI_VictorSPX masterRight;
  private WPI_VictorSPX slaveRight;
  private WPI_TalonSRX intakeMotor;
  private WPI_TalonSRX conveyorMotor;
  private WPI_VictorSPX shooterMotor;
  private WPI_TalonSRX climbMotor1;
  private WPI_TalonSRX climbMotor2;

  // speed controller groups
  private SpeedControllerGroup mGroupLeft;
  private SpeedControllerGroup mGroupRight;

  // controllers
  private XboxController driver1; // Utilities (Shooting, aiming, etc.)
  private XboxController driver2; // Tank Drive

  // pneumatics
  private Compressor compressor; // TODO: determine if used

  // Handlers
  private final ArrayList<IRobotEventHandler> EVENT_HANDLERS = new ArrayList<>();

  @Override
  public void robotInit() {
    // rear motors are slaves front motors are masters
    // Drive motors
    masterLeft = new WPI_VictorSPX(0);
    slaveLeft = new WPI_VictorSPX(1);
    masterRight = new WPI_VictorSPX(2);
    slaveRight = new WPI_VictorSPX(3);
    // speed controllers
    mGroupLeft = new SpeedControllerGroup(masterLeft, slaveLeft);
    mGroupRight = new SpeedControllerGroup(masterRight, slaveRight);
    // robot
    robot = new DifferentialDrive(mGroupLeft, mGroupRight);
    // other motors
    intakeMotor = new WPI_TalonSRX(7);
    conveyorMotor = new WPI_TalonSRX(5);
    shooterMotor = new WPI_VictorSPX(8);
    climbMotor1 = new WPI_TalonSRX(4);
    climbMotor2 = new WPI_TalonSRX(6);
    // pneumatics
    compressor = new Compressor(); // TODO: determine if used
    // controllers
    driver1 = new XboxController(0);
    driver2 = new XboxController(1);

    // Event Handlers
    //EVENT_HANDLERS.add(new CompressorHandler(compressor));
    //EVENT_HANDLERS.add(new GoForward(robot));
    EVENT_HANDLERS.add(new TankDriveHandler(robot, driver2));
    //EVENT_HANDLERS.add(new LimeLightHandler(robot, driver1));        // A button
    //EVENT_HANDLERS.add(new ShooterHandler(driver1, shooterMotor));   // Right trigger
    //EVENT_HANDLERS.add(new ConveyorHandler(conveyorMotor, driver1)); // Y button
    //EVENT_HANDLERS.add(new IntakeHandler(intakeMotor, driver1));     // B button

    EVENT_HANDLERS.forEach(IRobotEventHandler::robotInit);
  }

  // only exectes when disabled, use for cleanup
  @Override
  public void disabledInit() {
    EVENT_HANDLERS.forEach(IRobotEventHandler::disabledInit);
  }

  // only executes once when autonomous starts
  @Override
  public void autonomousInit() {
    EVENT_HANDLERS.forEach(IRobotEventHandler::autonomousInit);
  }

  // loops over itself (every ~.02 seconds) until disabled
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
  }
}