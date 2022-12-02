/**
   State.java
   
   Holds the variables that pertain to the state of the game.
   
   @author Peter Olson
   @version 06/11/19
*/

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class State {
   
   public static boolean changeScreens;
   public static Screen currentScreen;
   public static ArrayList<Screen> screenHistory;
   
   //Displayable variables --> have lambda getter functions
   public static int level;
   public static String className;
   public static String title;
   public static int HP;
   public static int MP; //#5
   public static int fatigue;
   public static int reputation;
   public static int points;
   public static int reductionPiercing;
   public static int reductionSlashing; //#10
   public static int reductionBlunt;
   public static int reductionMagic;
   public static int reductionFire;
   public static int reductionPoison;
   public static int reductionIce;    //#15
   public static ArrayList<String> skills;
   public static ArrayList<String> equipped;
   public static int exp;
   public static ArrayList<String> inventory;
   public static ArrayList<String> tempInventory; //#20
   public static ArrayList<String> quest;
   public static Coord location;
   public static String jobName;
   public static String currentDungeon;
   public static int optionNumber; //#25
   public static char rank;
   public static int strength;
   public static int agility;
   public static int sense;
   public static int vitality; //#30
   public static int intelligence;
   public static int attack;
   public static int defense;
   public static double attackMod;
   public static double defenseMod; //#35
   public static double strengthMod;
   public static double agilityMod;
   public static double senseMod;
   public static double vitalityMod;
   public static double intelligenceMod; //#40
   public static double HPMod;
   public static double MPMod;
   public static double expMod;
   public static int gold;
   public static double goldMod; //#45
   public static int luck;
   public static double luckMod;
   public static double reputationMod;
   public static double reductionPiercingMod;
   public static double reductionSlashingMod; //#50
   public static double reductionBluntMod;
   public static double reductionMagicMod;
   public static double reductionFireMod;
   public static double reductionPoisonMod;
   public static double reductionIceMod; //#55
   public static int regeneration;
   public static double regenerationMod;
   public static boolean canFly;
   public static boolean hasTelekineticSight;
   public static boolean isImmuneToDragonBreath; //#60
   public static boolean canBreatheUnderwater;
   public static int swimmingSpeed;
   public static double swimmingSpeedMod;
   public static boolean isImmuneToEffects;
   public static boolean isInvisible; //#65
   public static boolean canDetectMonsters;
   public static boolean hasNightVision;
   //end
   
   public static Map<String, Object> variables;
   private static final String VARIABLES_FILE = "variables.txt";
   
   /**
      Resets the state to its starting values and sets the variable map of getter functions
      
      @see GameWindow.init()
      @see setVariablesMap()
   */
   public State() {
      changeScreens = false;
      currentScreen = null;
      screenHistory = new ArrayList<Screen>();
      
      //Displayable variables
      level = 1;
      className = "Hunter";
      title = "<None>";
      HP = 100;
      MP = 100;
      fatigue = 0;
      reputation = 0;
      points = 0;
      reductionPiercing = 0;
      reductionSlashing = 0;
      reductionBlunt = 0;
      reductionMagic = 0;
      reductionFire = 0;
      reductionPoison = 0;
      reductionIce = 0;
      skills = new ArrayList<String>();
      equipped = new ArrayList<String>();
      exp = 0;
      inventory = new ArrayList<String>();
      tempInventory = new ArrayList<String>();
      quest = new ArrayList<String>();
      location = new Coord(7, 9);
      jobName = "None";
      currentDungeon = "None";
      optionNumber = 0;
      rank = '\u0000';
      strength = 0;
      agility = 0;
      sense = 0;
      vitality = 0;
      intelligence = 0;
      attack = 0;
      defense = 0;
      attackMod = 1.0;
      defenseMod = 1.0;
      strengthMod = 1.0;
      agilityMod = 1.0;
      senseMod = 1.0;
      vitalityMod = 1.0;
      intelligenceMod = 1.0;
      HPMod = 1.0;
      MPMod = 1.0;
      expMod = 1.0;
      gold = 0;
      goldMod = 1.0;
      luck = 0;
      luckMod = 1.0;
      reputationMod = 1.0;
      reductionPiercingMod = 1.0;
      reductionSlashingMod = 1.0;
      reductionBluntMod = 1.0;
      reductionMagicMod = 1.0;
      reductionFireMod = 1.0;
      reductionPoisonMod = 1.0;
      reductionIceMod = 1.0;
      regeneration = 1;
      regenerationMod = 1.0;
      canFly = false;
      hasTelekineticSight = false;
      isImmuneToDragonBreath = false;
      canBreatheUnderwater = false;
      swimmingSpeed = 1;
      swimmingSpeedMod = 1.0;
      isImmuneToEffects = false;
      isInvisible = false;
      canDetectMonsters = false;
      hasNightVision = false;
      
      variables = setVariablesMap();
   }
   
   /**
      Sets the map of Strings to their function calls using lambda expressions
      
      @return HashMap<String, Object> The maps of Strings pointing to their functions
      @see variables.txt
      @see lambda_interfaces
      @see Scanner.hasNextLine()
      @see Scanner.nextLine()
      @see List.get( int index )
   */
   private static HashMap<String, Object> setVariablesMap() {
      List<String> varNames = new ArrayList<String>();
      Scanner scanner = null;
      
      //@@TEMP
      System.out.println( System.getProperty("user.dir") );

      //Add variable names to list
      try {
         scanner = new Scanner( new File( VARIABLES_FILE ) );
         while( scanner.hasNextLine() )
            varNames.add( scanner.nextLine() );
      } catch( FileNotFoundException e ) {
         e.printStackTrace();
      } finally {
         scanner.close();
      }
      
      int i = 0;
      HashMap<String, Object> map = new HashMap<String, Object>();
      /* @@DO NOT CHANGE ORDER vv */
      FuncLevel f1 = () -> level;
      map.put( varNames.get(i++), f1.getLevel() );
      
      FuncClass f2 = () -> className;
      map.put( varNames.get(i++), f2.getClassName() );
      
      FuncTitle f3 = () -> title;
      map.put( varNames.get(i++), f3.getTitle() );
      
      FuncHP f4 = () -> HP;
      map.put( varNames.get(i++), f4.getHP() );
      
      FuncMP f5 = () -> MP;
      map.put( varNames.get(i++), f5.getMP() );
      
      FuncFatigue f6 = () -> fatigue;
      map.put( varNames.get(i++), f6.getFatigue() );
      
      FuncReputation f7 = () -> reputation;
      map.put( varNames.get(i++), f7.getReputation() );
      
      FuncPoints f8 = () -> points;
      map.put( varNames.get(i++), f8.getPoints() );
      
      FuncReductionPiercing f9 = () -> reductionPiercing;
      map.put( varNames.get(i++), f9.getReductionPiercing() );
      
      FuncReductionSlashing f10 = () -> reductionSlashing;
      map.put( varNames.get(i++), f10.getReductionSlashing() );
      
      FuncReductionBlunt f11 = () -> reductionBlunt;
      map.put( varNames.get(i++), f11.getReductionBlunt() );
      
      FuncReductionMagic f12 = () -> reductionMagic;
      map.put( varNames.get(i++), f12.getReductionMagic() );
      
      FuncReductionFire f13 = () -> reductionFire;
      map.put( varNames.get(i++), f13.getReductionFire() );
      
      FuncReductionPoison f14 = () -> reductionPoison;
      map.put( varNames.get(i++), f14.getReductionPoison() );
      
      FuncReductionIce f15 = () -> reductionIce;
      map.put( varNames.get(i++), f15.getReductionIce() );
      
      FuncSkills f16 = () -> skills;
      map.put( varNames.get(i++), f16.getSkills() );
      
      FuncEquipped f17 = () -> equipped;
      map.put( varNames.get(i++), f17.getEquipped() );
      
      FuncExp f18 = () -> exp;
      map.put( varNames.get(i++), f18.getExp() );
      
      FuncInventory f19 = () -> inventory;
      map.put( varNames.get(i++), f19.getInventory() );
      
      FuncTempInventory f20 = () -> tempInventory;
      map.put( varNames.get(i++), f20.getTempInventory() );
      
      FuncQuest f21 = () -> quest;
      map.put( varNames.get(i++), f21.getQuest() );
      
      FuncLocation f22 = () -> location;
      map.put( varNames.get(i++), f22.getLocation() );
      
      FuncJob f23 = () -> jobName;
      map.put( varNames.get(i++), f23.getJobName() );
      
      FuncCurrentDungeon f24 = () -> currentDungeon;
      map.put( varNames.get(i++), f24.getCurrentDungeon() );
      
      FuncOptionNum f25 = () -> optionNumber;
      map.put( varNames.get(i++), f25.getOptionNumber() );
      
      FuncRank f26 = () -> rank;
      map.put( varNames.get(i++), f26.getRank() );
      
      FuncStrength f27 = () -> strength;
      map.put( varNames.get(i++), f27.getStrength() );
      
      FuncAgility f28 = () -> agility;
      map.put( varNames.get(i++), f28.getAgility() );
      
      FuncSense f29 = () -> sense;
      map.put( varNames.get(i++), f29.getSense() );
      
      FuncVitality f30 = () -> vitality;
      map.put( varNames.get(i++), f30.getVitality() );
      
      FuncIntelligence f31 = () -> intelligence;
      map.put( varNames.get(i++), f31.getIntelligence() );
      
      FuncAttack f32 = () -> attack;
      map.put( varNames.get(i++), f32.getAttack() );
      
      FuncDefense f33 = () -> defense;
      map.put( varNames.get(i++), f33.getDefense() );
      
      FuncAttackMod f34 = () -> attackMod;
      map.put( varNames.get(i++), f34.getAttackMod() );
      
      FuncDefenseMod f35 = () -> defenseMod;
      map.put( varNames.get(i++), f35.getDefenseMod() );
      
      FuncStrengthMod f36 = () -> strengthMod;
      map.put( varNames.get(i++), f36.getStrengthMod() );
      
      FuncAgilityMod f37 = () -> agilityMod;
      map.put( varNames.get(i++), f37.getAgilityMod() );
      
      FuncSenseMod f38 = () -> senseMod;
      map.put( varNames.get(i++), f38.getSenseMod() );
      
      FuncVitalityMod f39 = () -> vitalityMod;
      map.put( varNames.get(i++), f39.getVitalityMod() );
      
      FuncIntelligenceMod f40 = () -> intelligenceMod;
      map.put( varNames.get(i++), f40.getIntelligenceMod() );
      
      FuncHPMod f41 = () -> HPMod;
      map.put( varNames.get(i++), f41.getHPMod() );
      
      FuncMPMod f42 = () -> MPMod;
      map.put( varNames.get(i++), f42.getMPMod() );
      
      FuncExpMod f43 = () -> expMod;
      map.put( varNames.get(i++), f43.getExpMod() );
      
      FuncGold f44 = () -> gold;
      map.put( varNames.get(i++), f44.getGold() );
      
      FuncGoldMod f45 = () -> goldMod;
      map.put( varNames.get(i++), f45.getGoldMod() );
      
      FuncLuck f46 = () -> luck;
      map.put( varNames.get(i++), f46.getLuck() );
      
      FuncLuckMod f47 = () -> luckMod;
      map.put( varNames.get(i++), f47.getLuckMod() );
      
      FuncReputationMod f48 = () -> reputationMod;
      map.put( varNames.get(i++), f48.getReputationMod() );
      
      FuncReductionPiercingMod f49 = () -> reductionPiercingMod;
      map.put( varNames.get(i++), f49.getReductionPiercingMod() );
      
      FuncReductionSlashingMod f50 = () -> reductionSlashingMod;
      map.put( varNames.get(i++), f50.getReductionSlashingMod() );
      
      FuncReductionBluntMod f51 = () -> reductionBluntMod;
      map.put( varNames.get(i++), f51.getReductionBluntMod() );
      
      FuncReductionMagicMod f52 = () -> reductionMagicMod;
      map.put( varNames.get(i++), f52.getReductionMagicMod() );
      
      FuncReductionFireMod f53 = () -> reductionFireMod;
      map.put( varNames.get(i++), f53.getReductionFireMod() );
      
      FuncReductionPoisonMod f54 = () -> reductionPoisonMod;
      map.put( varNames.get(i++), f54.getReductionPoisonMod() );
      
      FuncReductionIceMod f55 = () -> reductionIceMod;
      map.put( varNames.get(i++), f55.getReductionIceMod() );
      
      FuncRegeneration f56 = () -> regeneration;
      map.put( varNames.get(i++), f56.getRegeneration() );
      
      FuncRegenerationMod f57 = () -> regenerationMod;
      map.put( varNames.get(i++), f57.getRegenerationMod() );
      
      FuncCanFly f58 = () -> canFly;
      map.put( varNames.get(i++), f58.getCanFly() );
      
      FuncHasTelekineticSight f59 = () -> hasTelekineticSight;
      map.put( varNames.get(i++), f59.getHasTelekineticSight() );
      
      FuncIsImmuneToDragonBreath f60 = () -> isImmuneToDragonBreath;
      map.put( varNames.get(i++), f60.getIsImmuneToDragonBreath() );
      
      FuncCanBreatheUnderwater f61 = () -> canBreatheUnderwater;
      map.put( varNames.get(i++), f61.getCanBreatheUnderwater() );
      
      FuncSwimmingSpeed f62 = () -> swimmingSpeed;
      map.put( varNames.get(i++), f62.getSwimmingSpeed() );
      
      FuncSwimmingSpeedMod f63 = () -> swimmingSpeedMod;
      map.put( varNames.get(i++), f63.getSwimmingSpeedMod() );
      
      FuncIsImmuneToEffects f64 = () -> isImmuneToEffects;
      map.put( varNames.get(i++), f64.getIsImmuneToEffects() );
      
      FuncIsInvisible f65 = () -> isInvisible;
      map.put( varNames.get(i++), f65.getIsInvisible() );
      
      FuncCanDetectMonsters f66 = () -> canDetectMonsters;
      map.put( varNames.get(i++), f66.getCanDetectMonsters() );
      
      FuncHasNightVision f67 = () -> hasNightVision;
      map.put( varNames.get(i++), f67.getHasNightVision() );
      
      /* @@DO NOT CHANGE ORDER ^^ */
      
      return map;
   }
   
   public interface FuncLevel { int getLevel(); }
   public interface FuncClass { String getClassName(); }
   public interface FuncTitle { String getTitle(); }
   public interface FuncHP { int getHP(); }
   public interface FuncMP { int getMP(); }
   public interface FuncFatigue { int getFatigue(); }
   public interface FuncReputation { int getReputation(); }
   public interface FuncPoints { int getPoints(); }
   public interface FuncReductionPiercing { int getReductionPiercing(); }
   public interface FuncReductionSlashing { int getReductionSlashing(); }
   public interface FuncReductionBlunt { int getReductionBlunt(); }
   public interface FuncReductionMagic { int getReductionMagic(); }
   public interface FuncReductionFire { int getReductionFire(); }
   public interface FuncReductionPoison { int getReductionPoison(); }
   public interface FuncReductionIce { int getReductionIce(); }
   public interface FuncSkills { ArrayList<String> getSkills(); }
   public interface FuncEquipped { ArrayList<String> getEquipped(); }
   public interface FuncExp { int getExp(); }
   public interface FuncInventory { ArrayList<String> getInventory(); }
   public interface FuncTempInventory { ArrayList<String> getTempInventory(); }
   public interface FuncQuest { ArrayList<String> getQuest(); }
   public interface FuncLocation { Coord getLocation(); }
   public interface FuncJob { String getJobName(); }
   public interface FuncCurrentDungeon { String getCurrentDungeon(); }
   public interface FuncOptionNum { int getOptionNumber(); }
   public interface FuncRank { char getRank(); }
   public interface FuncStrength { int getStrength(); }
   public interface FuncAgility { int getAgility(); }
   public interface FuncSense { int getSense(); }
   public interface FuncVitality { int getVitality(); }
   public interface FuncIntelligence { int getIntelligence(); }
   public interface FuncAttack { int getAttack(); }
   public interface FuncDefense { int getDefense(); }
   public interface FuncAttackMod { double getAttackMod(); }
   public interface FuncDefenseMod { double getDefenseMod(); }
   public interface FuncStrengthMod { double getStrengthMod(); }
   public interface FuncAgilityMod { double getAgilityMod(); }
   public interface FuncSenseMod { double getSenseMod(); }
   public interface FuncVitalityMod { double getVitalityMod(); }
   public interface FuncIntelligenceMod { double getIntelligenceMod(); }
   public interface FuncHPMod { double getHPMod(); }
   public interface FuncMPMod { double getMPMod(); }
   public interface FuncExpMod { double getExpMod(); }
   public interface FuncGold { int getGold(); }
   public interface FuncGoldMod { double getGoldMod(); }
   public interface FuncLuck { int getLuck(); }
   public interface FuncLuckMod { double getLuckMod(); }
   public interface FuncReputationMod { double getReputationMod(); }
   public interface FuncReductionPiercingMod { double getReductionPiercingMod(); }
   public interface FuncReductionSlashingMod { double getReductionSlashingMod(); }
   public interface FuncReductionBluntMod { double getReductionBluntMod(); }
   public interface FuncReductionMagicMod { double getReductionMagicMod(); }
   public interface FuncReductionFireMod { double getReductionFireMod(); }
   public interface FuncReductionPoisonMod { double getReductionPoisonMod(); }
   public interface FuncReductionIceMod { double getReductionIceMod(); }
   public interface FuncRegeneration { int getRegeneration(); }
   public interface FuncRegenerationMod { double getRegenerationMod(); }
   public interface FuncCanFly { boolean getCanFly(); }
   public interface FuncHasTelekineticSight { boolean getHasTelekineticSight(); }
   public interface FuncIsImmuneToDragonBreath { boolean getIsImmuneToDragonBreath(); }
   public interface FuncCanBreatheUnderwater { boolean getCanBreatheUnderwater(); }
   public interface FuncSwimmingSpeed { int getSwimmingSpeed(); }
   public interface FuncSwimmingSpeedMod { double getSwimmingSpeedMod(); }
   public interface FuncIsImmuneToEffects { boolean getIsImmuneToEffects(); }
   public interface FuncIsInvisible { boolean getIsInvisible(); }
   public interface FuncCanDetectMonsters { boolean getCanDetectMonsters(); }
   public interface FuncHasNightVision { boolean getHasNightVision(); }

}