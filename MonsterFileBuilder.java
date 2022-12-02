/**
   MonsterFileBuilder.java
   
   Creates starting text files for monsters based off of template files, monster type, and rank.
   
   @@NOTE: Uses the following text files to create new text files of the form monster_<monsterName>.txt:
   
   monsterList.txt, Monster ARCHER.txt, Monster ATK.txt, Monster BOSS.txt, Monster DARK.txt, Monster DEF.txt,
   Monster DEFENSIVE.txt, Monster FIGHTER.txt, Monster HP.txt, Monster MAGE.txt, Monster MP.txt, Monster NORMAL.txt,
   Monster STATS.txt, Monster TANK.txt, Monster Template.txt, Monster WEAK.txt, see more below in fields
   
   
   @author Peter Olson
   @version 07/06/19
*/

import java.util.Scanner;
import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.IOException;

public class MonsterFileBuilder {

   //Item category templates
   private static final String ITEM_DEFAULT_TEMPLATE = "Item Default Template.txt";
   private static final String ITEM_ORB_TEMPLATE = "Item Orb Template.txt";
   private static final String ITEM_POTIONS_TEMPLATE = "Item Potions Template.txt";
   private static final String ITEM_ACCESSORY_TEMPLATE = "Item Accessory Template.txt";
   private static final String ITEM_LENGTH_MODIFIER_TEMPLATE = "Item Length Modifier Template.txt";
   private static final String ITEM_ARMOR_TEMPLATE = "Item Armor Template.txt";
   private static final String ITEM_WEAPON_TEMPLATE = "Item Weapon Template.txt";
   private static final String ITEM_STATS_TEMPLATE = "Item Stats Template.txt";
   private static final String ITEM_ATK_TEMPLATE = "Item ATK.txt";
   private static final String ITEM_DEF_TEMPLATE = "Item DEF.txt";
   private static final String ITEM_SPEED_TEMPLATE = "Item SPEED.txt";
   private static final String ITEM_BONUS_PERCENTAGE_TEMPLATE = "Item Bonus Percentage Template.txt";

   //Item type templates...
   //Armor
   private static final String ITEM_BOOTS_TEMPLATE = "Boot Template.txt";
   private static final String ITEM_CAPES_TEMPLATE = "Cape Template.txt";
   private static final String ITEM_GLOVES_TEMPLATE = "Glove Template.txt";
   private static final String ITEM_HELMETS_TEMPLATE = "Helmet Template.txt";
   private static final String ITEM_KARAMJARS_TEMPLATE = "Karamjar Template.txt";
   private static final String ITEM_PANTS_TEMPLATE = "Pants Template.txt";
   private static final String ITEM_PLATEBODIES_TEMPLATE = "Platebody Template.txt";
   private static final String ITEM_PLATELEGS_TEMPLATE = "Platelegs Template.txt";
   private static final String ITEM_ROBES_TEMPLATE = "Robe Template.txt";
   private static final String ITEM_SHIELDS_TEMPLATE = "Shield Template.txt";
   
   //Weapon
   private static final String ITEM_BATTLEAXES_TEMPLATE = "Battleaxe Template.txt";
   private static final String ITEM_BOWS_TEMPLATE = "Bow Template.txt";
   private static final String ITEM_CANNONS_TEMPLATE = "Cannon Template.txt";
   private static final String ITEM_CLAWS_TEMPLATE = "Claw Template.txt";
   private static final String ITEM_CLUBS_TEMPLATE = "Club Template.txt";
   private static final String ITEM_CUTLASSES_TEMPLATE = "Cutlass Template.txt";
   private static final String ITEM_DAGGERS_TEMPLATE = "Dagger Template.txt";
   private static final String ITEM_HALBERDS_TEMPLATE = "Halberd Template.txt";
   private static final String ITEM_HAMMERS_TEMPLATE = "Hammer Template.txt";
   private static final String ITEM_KNUCKLES_TEMPLATE = "Knuckle Template.txt";
   private static final String ITEM_LANCES_TEMPLATE = "Lance Template.txt";
   private static final String ITEM_LONGSWORDS_TEMPLATE = "Longsword Template.txt";
   private static final String ITEM_MACES_TEMPLATE = "Mace Template.txt";
   private static final String ITEM_SCIMITARS_TEMPLATE = "Scimitar Template.txt";
   private static final String ITEM_SCYTHES_TEMPLATE = "Scythe Template.txt";
   private static final String ITEM_SHORTSWORDS_TEMPLATE = "Shortsword Template.txt";
   private static final String ITEM_SPEARS_TEMPLATE = "Spear Template.txt";
   private static final String ITEM_SPECIALTY_WEAPONS_TEMPLATE = "Specialty Weapon Template.txt";
   private static final String ITEM_STAFFS_TEMPLATE = "Staff Template.txt";
   private static final String ITEM_TRIDENTS_TEMPLATE = "Trident Template.txt";
   private static final String ITEM_TWO_HANDED_SWORDS_TEMPLATE = "Two-Handed Sword Template.txt";
   private static final String ITEM_WANDS_TEMPLATE = "Wand Template.txt";
   private static final String ITEM_WHIPS_TEMPLATE = "Whip Template.txt";
   //...END

   //Accessory items
   private static final String ITEM_EARRINGS = "Earrings.txt";
   private static final String ITEM_PENDANTS = "Pendants.txt";
   private static final String ITEM_RINGS = "Rings.txt";
   
   //Other items
   private static final String ITEM_MATERIALS = "Materials.txt";
   private static final String ITEM_ORBS = "Orbs.txt";
   private static final String ITEM_OTHERS = "Others.txt";
   private static final String ITEM_POTIONS = "Potions.txt";
   private static final String ITEM_RUNESTONES = "Runestones.txt";
   
   //Armor items
   private static final String ITEM_BOOTS = "Boots.txt";
   private static final String ITEM_CAPES = "Capes.txt";
   private static final String ITEM_GLOVES = "Gloves.txt";
   private static final String ITEM_HELMETS = "Helmets.txt";
   private static final String ITEM_KARAMJARS = "Karamjars.txt";
   private static final String ITEM_PANTS = "Pants.txt";
   private static final String ITEM_PLATEBODIES = "Platebodies.txt";
   private static final String ITEM_PLATELEGS = "Platelegs.txt";
   private static final String ITEM_ROBES = "Robes.txt";
   private static final String ITEM_SHIELDS = "Shields.txt";
   
   //Weapon items
   private static final String ITEM_BATTLEAXES = "Battleaxes.txt";
   private static final String ITEM_BOWS = "Bows.txt";
   private static final String ITEM_CANNONS = "Cannons.txt";
   private static final String ITEM_CLAWS = "Claws.txt";
   private static final String ITEM_CLUBS = "Clubs.txt";
   private static final String ITEM_CUTLASSES = "Cutlasses.txt";
   private static final String ITEM_DAGGERS = "Daggers.txt";
   private static final String ITEM_HALBERDS = "Halberds.txt";
   private static final String ITEM_HAMMERS = "Hammers.txt";
   private static final String ITEM_KNUCKLES = "Knuckles.txt";
   private static final String ITEM_LANCES = "Lances.txt";
   private static final String ITEM_LONGSWORDS = "Longswords.txt";
   private static final String ITEM_MACES = "Maces.txt";
   private static final String ITEM_SCIMITARS = "Scimitars.txt";
   private static final String ITEM_SCYTHES = "Scythes.txt";
   private static final String ITEM_SHORTSWORDS = "Shortswords.txt";
   private static final String ITEM_SPEARS = "Spears.txt";
   private static final String ITEM_SPECIALTY_WEAPONS = "Specialty Weapons.txt";
   private static final String ITEM_STAFFS = "Staffs.txt";
   private static final String ITEM_TRIDENTS = "Tridents.txt";
   private static final String ITEM_TWO_HANDED_SWORDS = "Two-Handed Swords.txt";
   private static final String ITEM_WANDS = "Wands.txt";
   private static final String ITEM_WHIPS = "Whips.txt";

   //Monster templates and lists
   private static final String MONSTER_FILE = "monsterList.txt";
   private static final String MONSTER_TEMPLATE = "Monster Template.txt";
   
   //Monster stat templates
   private static final String MONSTER_ATK = "Monster ATK.txt";
   private static final String MONSTER_DEF = "Monster DEF.txt";
   private static final String MONSTER_HP = "Monster HP.txt";
   private static final String MONSTER_MP = "Monster MP.txt";
   private static final String MONSTER_STATS = "Monster STATS.txt";
  
   //Monster type templates
   private static final String MONSTER_ARCHER = "Monster ARCHER.txt";
   private static final String MONSTER_BOSS = "Monster BOSS.txt";
   private static final String MONSTER_DARK = "Monster DARK.txt";
   private static final String MONSTER_DEFENSIVE = "Monster DEFENSIVE.txt";
   private static final String MONSTER_FIGHTER = "Monster FIGHTER.txt";
   private static final String MONSTER_MAGE = "Monster MAGE.txt";
   private static final String MONSTER_NORMAL = "Monster NORMAL.txt";
   private static final String MONSTER_TANK = "Monster TANK.txt";
   private static final String MONSTER_WEAK = "Monster WEAK.txt";

   //File name tags
   private static final String NEW_MONSTER_FILE_PREFIX = "monster_";
   private static final String NEW_ITEM_FILE_PREFIX = "item_";
   private static final String TEMPLATE_FILE_PREFIX = "Monster ";

   public static void main( String[] args ) {
      /* UNCOMMENT TO GENERATE ITEM FILE TYPE */
      //createMonsterFiles();
      //createDefaultItemFiles(); //Default files are any files that have just a type and a description
      //createOrbItemFiles();
      //createPotionItemFiles();
      //createAccessoryItemFiles();
      //createWeaponItemFiles();
      //createArmorItemFiles();
   }
   
   /**
      Write the armor text files using a ton of templates. Specifically, the main templates used are listed below:
      
      @see ITEM_ARMOR_TEMPLATE text file
      @see ITEM_STATS_TEMPLATE text file
      @see ITEM_DEF_TEMPLATE text file
      @see ITEM_BONUS_PERCENTAGE_TEMPLATE text file
      @see ITEM_LENGTH_MODIFIER_TEMPLATE text file
      @see ITEM_BOOTS, ITEM_CAPES, ... , ITEM_SHIELDS item lists text files (10 total)
      @see ITEM_BOOTS_TEMPLATE, ITEM_CAPES_TEMPLATE, ... , ITEM_SHIELDS item template text files (10 total)
      
      @see writeNewWeaponItemFile( String type, String templateFile, String rank, double lengthModifier, PrintStream ps )
   */
   private static void createArmorItemFiles() {
      Scanner bootsFileScanner = null; Scanner capesFileScanner = null; Scanner glovesFileScanner = null; Scanner helmetsFileScanner = null; Scanner karamjarsFileScanner = null;
      Scanner pantsFileScanner = null; Scanner platebodiesFileScanner = null; Scanner platelegsFileScanner = null; Scanner robesFileScanner = null; Scanner shieldsFileScanner = null; 
      try {
         bootsFileScanner = new Scanner( new File( ITEM_BOOTS ) ); capesFileScanner = new Scanner( new File( ITEM_CAPES ) ); glovesFileScanner = new Scanner( new File( ITEM_GLOVES ) );
         helmetsFileScanner = new Scanner( new File( ITEM_HELMETS ) ); karamjarsFileScanner = new Scanner( new File( ITEM_KARAMJARS ) ); pantsFileScanner = new Scanner( new File( ITEM_PANTS ) );
         platebodiesFileScanner = new Scanner( new File( ITEM_PLATEBODIES ) ); platelegsFileScanner = new Scanner( new File( ITEM_PLATELEGS ) ); robesFileScanner = new Scanner( new File( ITEM_ROBES ) );
         shieldsFileScanner = new Scanner( new File( ITEM_SHIELDS ) );
      } catch( FileNotFoundException e ) {
         e.printStackTrace();
      }
      
      String[] types = { "boots", "cape", "gloves", "helmet", "karamjar", "pants", "platebody", "platelegs", "robe", "shield" };
      Scanner[] scannerList = { bootsFileScanner, capesFileScanner, glovesFileScanner, helmetsFileScanner, karamjarsFileScanner, pantsFileScanner, platebodiesFileScanner, platelegsFileScanner,
                                robesFileScanner, shieldsFileScanner };
      String[] templates = { ITEM_BOOTS_TEMPLATE, ITEM_CAPES_TEMPLATE, ITEM_GLOVES_TEMPLATE, ITEM_HELMETS_TEMPLATE, ITEM_KARAMJARS_TEMPLATE, ITEM_PANTS_TEMPLATE, ITEM_PLATEBODIES_TEMPLATE,
                             ITEM_PLATELEGS_TEMPLATE, ITEM_ROBES_TEMPLATE, ITEM_SHIELDS_TEMPLATE };
      
      for( int i = 0; i < scannerList.length; i++ ) {
         Scanner currentScanner = scannerList[i];
         while( currentScanner.hasNextLine() ) {
            Scanner itemLengthModifierTempScanner = null;
            try {
               itemLengthModifierTempScanner = new Scanner( new File( ITEM_LENGTH_MODIFIER_TEMPLATE ) );
            } catch( FileNotFoundException e ) {
               e.printStackTrace();
            }
         
            String line = currentScanner.nextLine();
            line = line.replaceAll( "[^a-zA-Z0-9 \\-',\\.@:\\|]", "" ); //Gets rid of beginning of text file junk values
            int lastIndexSpace = line.lastIndexOf(" ");
            String itemName = line.substring( 0, lastIndexSpace );
            String itemRanks = line.substring( lastIndexSpace + 1 );
            String rank = itemRanks.split(",")[0];
            
            //Get length modifier
            double lengthModifier = 1.0;
            int numTerms = itemName.split(" ").length + itemName.length() - itemName.replaceAll("\\-", "").length();
            while( --numTerms > 0 ) itemLengthModifierTempScanner.nextLine();
            lengthModifier = Double.parseDouble( itemLengthModifierTempScanner.nextLine().split(" ")[1] );
            
            File newItemFile = null;
            PrintStream ps = null;
            try {
      			newItemFile = new File( NEW_ITEM_FILE_PREFIX + itemName.toLowerCase() + ".txt" );
      		   ps = new PrintStream( newItemFile );
      		} catch( IOException e ) {
      			System.out.println(e);
      		}
            
            writeNewArmorItemFile( types[i], templates[i], rank, lengthModifier, ps );
            
            ps.close();
            itemLengthModifierTempScanner.close();
         }
      }

      bootsFileScanner.close(); capesFileScanner.close(); glovesFileScanner.close(); helmetsFileScanner.close(); karamjarsFileScanner.close(); pantsFileScanner.close(); platebodiesFileScanner.close();
      platelegsFileScanner.close(); robesFileScanner.close(); shieldsFileScanner.close();
   }
   
   /**
      Write the text file for the given armor item. To see all used text files, see above method
      
      @param type The type of the armor, eg. "platebody", "helmet", "boots", "shield", etc
      @param templateFile The template for this armor, eg. "Platebody Template.txt", "Shield Template.txt", etc
      @param rank The rank of this item, eg. A3, E2, C1, D4, S2, etc
      @param lengthModifier The modifier based on the length of the name of the item-- items with longer names receive large stat modifiers
      @param ps The file writer for this item's text file
      
      @see createArmorItemFiles() -- see text files
   */
   private static void writeNewArmorItemFile( String type, String templateFile, String rank, double lengthModifier, PrintStream ps ) {
      Scanner armorTempScanner = null;
      Scanner itemArmorTempScanner = null;
      Scanner itemStatsTempScanner = null;
      Scanner itemDEFTempScanner = null;
      Scanner itemBonusPercentageTempScanner = null;
      try {
         armorTempScanner = new Scanner( new File( ITEM_ARMOR_TEMPLATE ) );
         itemArmorTempScanner = new Scanner( new File( templateFile ) );
         itemStatsTempScanner = new Scanner( new File( ITEM_STATS_TEMPLATE ) );
         itemDEFTempScanner = new Scanner( new File( ITEM_DEF_TEMPLATE ) );
         itemBonusPercentageTempScanner = new Scanner( new File( ITEM_BONUS_PERCENTAGE_TEMPLATE ) );
      } catch( FileNotFoundException e ) {
         e.printStackTrace();
      }
      
      String rankLetter = String.valueOf( rank.charAt(0) );
      int rankNumber = Integer.valueOf( String.valueOf( rank.charAt(1) ) );
      
      //Type: / Rank:
      ps.println( armorTempScanner.nextLine() + ": " + type );
      ps.println( armorTempScanner.nextLine() + ": " + rank );
      
      //Defense:
      while( itemDEFTempScanner.hasNextLine() ) {
         if( itemDEFTempScanner.nextLine().split(" ")[0].equals( rankLetter ) ) {
            int value = Integer.valueOf( itemDEFTempScanner.nextLine().split(" ")[ rankNumber - 1 ] );
            double armorMod = Double.parseDouble( itemArmorTempScanner.nextLine().split(" ")[1] );
            ps.println( armorTempScanner.nextLine() + ": " + (int)(value*armorMod*lengthModifier) );
            break;
         } else {
            itemDEFTempScanner.nextLine();
         }
      }
      
      //Effects:
      ps.println( armorTempScanner.nextLine() + ": none" );
      
      //BonusStrength: / BonusAgility: / BonusSense: / BonusVitality: / BonusIntelligence: / BonusHP: / BonusMP:
      Random random = new Random();
      final int OUT_OF_ONE_HUNDRED = 100;
      int bonusPercent = 0;
      while( itemBonusPercentageTempScanner.hasNextLine() ) {
         if( itemBonusPercentageTempScanner.nextLine().split(" ")[0].equals( rankLetter ) ) {
            bonusPercent = Integer.parseInt( itemBonusPercentageTempScanner.nextLine().split(" ")[ rankNumber - 1] );
            break;
         } else {
            itemBonusPercentageTempScanner.nextLine();
         }
      }
      while( itemStatsTempScanner.hasNextLine() ) {
         if( itemStatsTempScanner.nextLine().split(" ")[0].equals( rankLetter ) ) {
            int value = Integer.valueOf( itemStatsTempScanner.nextLine().split(" ")[ rankNumber - 1 ] );
            
            double armorModStrength = Double.parseDouble( itemArmorTempScanner.nextLine().split(" ")[1] );
            double armorModAgility = Double.parseDouble( itemArmorTempScanner.nextLine().split(" ")[1] );
            double armorModSense = Double.parseDouble( itemArmorTempScanner.nextLine().split(" ")[1] );
            double armorModVitality = Double.parseDouble( itemArmorTempScanner.nextLine().split(" ")[1] );
            double armorModIntelligence = Double.parseDouble( itemArmorTempScanner.nextLine().split(" ")[1] );
            double armorModHP = Double.parseDouble( itemArmorTempScanner.nextLine().split(" ")[1] );
            double armorModMP = Double.parseDouble( itemArmorTempScanner.nextLine().split(" ")[1] );
            
            if( random.nextInt( OUT_OF_ONE_HUNDRED ) < bonusPercent ) ps.println( armorTempScanner.nextLine() + ": " + (int)(value*armorModStrength*lengthModifier) );
            else                                                      ps.println( armorTempScanner.nextLine() + ": 0" );
            if( random.nextInt( OUT_OF_ONE_HUNDRED ) < bonusPercent ) ps.println( armorTempScanner.nextLine() + ": " + (int)(value*armorModAgility*lengthModifier) );
            else                                                      ps.println( armorTempScanner.nextLine() + ": 0" );
            if( random.nextInt( OUT_OF_ONE_HUNDRED ) < bonusPercent ) ps.println( armorTempScanner.nextLine() + ": " + (int)(value*armorModSense*lengthModifier) );
            else                                                      ps.println( armorTempScanner.nextLine() + ": 0" );
            if( random.nextInt( OUT_OF_ONE_HUNDRED ) < bonusPercent ) ps.println( armorTempScanner.nextLine() + ": " + (int)(value*armorModVitality*lengthModifier) );
            else                                                      ps.println( armorTempScanner.nextLine() + ": 0" );
            if( random.nextInt( OUT_OF_ONE_HUNDRED ) < bonusPercent ) ps.println( armorTempScanner.nextLine() + ": " + (int)(value*armorModIntelligence*lengthModifier) );
            else                                                      ps.println( armorTempScanner.nextLine() + ": 0" );
            if( random.nextInt( OUT_OF_ONE_HUNDRED ) < bonusPercent ) ps.println( armorTempScanner.nextLine() + ": " + (int)(value*armorModHP*lengthModifier) );
            else                                                      ps.println( armorTempScanner.nextLine() + ": 0" );
            if( random.nextInt( OUT_OF_ONE_HUNDRED ) < bonusPercent ) ps.println( armorTempScanner.nextLine() + ": " + (int)(value*armorModMP*lengthModifier) );
            else                                                      ps.println( armorTempScanner.nextLine() + ": 0" );
            break;
         } else {
            itemStatsTempScanner.nextLine();
         }
      }
      
      //Description:
      ps.println( armorTempScanner.nextLine() + ": " );
      
      armorTempScanner.close();
      itemArmorTempScanner.close();
      itemStatsTempScanner.close();
      itemDEFTempScanner.close();
      itemBonusPercentageTempScanner.close();
   }
   
   /**
      Write the weapon text files using a ton of templates. Specifically, the main templates used are listed below:
      
      @see ITEM_WEAPON_TEMPLATE text file
      @see ITEM_STATS_TEMPLATE text file
      @see ITEM_ATK_TEMPLATE text file
      @see ITEM_SPEED_TEMPLATE text file
      @see ITEM_BONUS_PERCENTAGE_TEMPLATE text file
      @see ITEM_LENGTH_MODIFIER_TEMPLATE text file
      @see ITEM_BATTLEAXES, ITEM_BOWS, ... , ITEM_WHIPS item lists text files (23 total)
      @see ITEM_BATTLEAXES_TEMPLATE, ITEM_BOWS_TEMPLATE, ... , ITEM_WHIPS item template text files (23 total)
      
      @see writeNewWeaponItemFile( String type, String templateFile, String rank, double lengthModifier, PrintStream ps )
   */
   private static void createWeaponItemFiles() {
      Scanner battleaxesFileScanner = null; Scanner bowsFileScanner = null; Scanner cannonsFileScanner = null; Scanner clawsFileScanner = null; Scanner clubsFileScanner = null;
      Scanner cutlassesFileScanner = null; Scanner daggersFileScanner = null; Scanner halberdsFileScanner = null; Scanner hammersFileScanner = null; Scanner knucklesFileScanner = null;
      Scanner lancesFileScanner = null; Scanner longswordsFileScanner = null; Scanner macesFileScanner = null; Scanner scimitarsFileScanner = null; Scanner scythesFileScanner = null;
      Scanner shortswordsFileScanner = null; Scanner spearsFileScanner = null; Scanner specialtyWeaponsFileScanner = null; Scanner staffsFileScanner = null; Scanner tridentsFileScanner = null;
      Scanner twoHandedSwordsFileScanner = null; Scanner wandsFileScanner = null; Scanner whipsFileScanner = null;
      try {
         battleaxesFileScanner = new Scanner( new File( ITEM_BATTLEAXES ) ); bowsFileScanner = new Scanner( new File( ITEM_BOWS ) ); cannonsFileScanner = new Scanner( new File( ITEM_CANNONS ) );
         clawsFileScanner = new Scanner( new File( ITEM_CLAWS ) ); clubsFileScanner = new Scanner( new File( ITEM_CLUBS ) ); cutlassesFileScanner = new Scanner( new File( ITEM_CUTLASSES ) );
         daggersFileScanner = new Scanner( new File( ITEM_DAGGERS ) ); halberdsFileScanner = new Scanner( new File( ITEM_HALBERDS ) ); hammersFileScanner = new Scanner( new File( ITEM_HAMMERS ) );
         knucklesFileScanner = new Scanner( new File( ITEM_KNUCKLES ) ); lancesFileScanner = new Scanner( new File( ITEM_LANCES ) ); longswordsFileScanner = new Scanner( new File( ITEM_LONGSWORDS ) );
         macesFileScanner = new Scanner( new File( ITEM_MACES ) ); scimitarsFileScanner = new Scanner( new File( ITEM_SCIMITARS ) ); scythesFileScanner = new Scanner( new File( ITEM_SCYTHES ) );
         shortswordsFileScanner = new Scanner( new File( ITEM_SHORTSWORDS ) ); spearsFileScanner = new Scanner( new File( ITEM_SPEARS ) );
         specialtyWeaponsFileScanner = new Scanner( new File( ITEM_SPECIALTY_WEAPONS ) ); staffsFileScanner = new Scanner( new File( ITEM_STAFFS ) );
         tridentsFileScanner = new Scanner( new File( ITEM_TRIDENTS ) ); twoHandedSwordsFileScanner = new Scanner( new File( ITEM_TWO_HANDED_SWORDS ) );
         wandsFileScanner = new Scanner( new File( ITEM_WANDS ) ); whipsFileScanner = new Scanner( new File( ITEM_WHIPS ) );
      } catch( FileNotFoundException e ) {
         e.printStackTrace();
      }
      
      String[] types = { "battleaxe", "bow", "cannon", "claw", "club", "cutlass", "dagger", "halberd", "hammer", "knuckles", "lance", "longsword", "mace", "scimitar", "scythe", "shortsword",
                         "spear", "specialty weapon", "staff", "trident", "two-handed sword", "wand", "whip" };
      Scanner[] scannerList = { battleaxesFileScanner, bowsFileScanner, cannonsFileScanner, clawsFileScanner, clubsFileScanner, cutlassesFileScanner, daggersFileScanner, halberdsFileScanner,
                                hammersFileScanner, knucklesFileScanner, lancesFileScanner, longswordsFileScanner, macesFileScanner, scimitarsFileScanner, scythesFileScanner,
                                shortswordsFileScanner, spearsFileScanner, specialtyWeaponsFileScanner, staffsFileScanner, tridentsFileScanner, twoHandedSwordsFileScanner, wandsFileScanner,
                                whipsFileScanner };
      String[] templates = { ITEM_BATTLEAXES_TEMPLATE, ITEM_BOWS_TEMPLATE, ITEM_CANNONS_TEMPLATE, ITEM_CLAWS_TEMPLATE, ITEM_CLUBS_TEMPLATE, ITEM_CUTLASSES_TEMPLATE, ITEM_DAGGERS_TEMPLATE,
                             ITEM_HALBERDS_TEMPLATE, ITEM_HAMMERS_TEMPLATE, ITEM_KNUCKLES_TEMPLATE, ITEM_LANCES_TEMPLATE, ITEM_LONGSWORDS_TEMPLATE, ITEM_MACES_TEMPLATE, ITEM_SCIMITARS_TEMPLATE,
                             ITEM_SCYTHES_TEMPLATE, ITEM_SHORTSWORDS_TEMPLATE, ITEM_SPEARS_TEMPLATE, ITEM_SPECIALTY_WEAPONS_TEMPLATE, ITEM_STAFFS_TEMPLATE, ITEM_TRIDENTS_TEMPLATE,
                             ITEM_TWO_HANDED_SWORDS_TEMPLATE, ITEM_WANDS_TEMPLATE, ITEM_WHIPS_TEMPLATE };
      
      for( int i = 0; i < scannerList.length; i++ ) {
         Scanner currentScanner = scannerList[i];
         while( currentScanner.hasNextLine() ) {
            Scanner itemLengthModifierTempScanner = null;
            try {
               itemLengthModifierTempScanner = new Scanner( new File( ITEM_LENGTH_MODIFIER_TEMPLATE ) );
            } catch( FileNotFoundException e ) {
               e.printStackTrace();
            }
         
            String line = currentScanner.nextLine();
            line = line.replaceAll( "[^a-zA-Z0-9 \\-',\\.@:\\|]", "" ); //Gets rid of beginning of text file junk values
            int lastIndexSpace = line.lastIndexOf(" ");
            String itemName = line.substring( 0, lastIndexSpace );
            String itemRanks = line.substring( lastIndexSpace + 1 );
            String rank = itemRanks.split(",")[0];
            
            //Get length modifier
            double lengthModifier = 1.0;
            int numTerms = itemName.split(" ").length + itemName.length() - itemName.replaceAll("\\-", "").length();
            while( --numTerms > 0 ) itemLengthModifierTempScanner.nextLine();
            lengthModifier = Double.parseDouble( itemLengthModifierTempScanner.nextLine().split(" ")[1] );
            
            File newItemFile = null;
            PrintStream ps = null;
            try {
      			newItemFile = new File( NEW_ITEM_FILE_PREFIX + itemName.toLowerCase() + ".txt" );
      		   ps = new PrintStream( newItemFile );
      		} catch( IOException e ) {
      			System.out.println(e);
      		}
            
            writeNewWeaponItemFile( types[i], templates[i], rank, lengthModifier, ps );
            
            ps.close();
            itemLengthModifierTempScanner.close();
         }
      }
      
      battleaxesFileScanner.close(); bowsFileScanner.close(); cannonsFileScanner.close(); clawsFileScanner.close(); clubsFileScanner.close(); cutlassesFileScanner.close(); daggersFileScanner.close();
      halberdsFileScanner.close(); hammersFileScanner.close(); knucklesFileScanner.close(); lancesFileScanner.close(); longswordsFileScanner.close(); macesFileScanner.close(); scimitarsFileScanner.close();
      scythesFileScanner.close(); shortswordsFileScanner.close(); spearsFileScanner.close(); specialtyWeaponsFileScanner.close(); staffsFileScanner.close(); tridentsFileScanner.close();
      twoHandedSwordsFileScanner.close(); wandsFileScanner.close(); whipsFileScanner.close();
   }
   
   /**
      Write the text file for the given weapon item. To see all used text files, see above method
      
      @param type The type of the weapon, eg. "dagger", "bow", "cutlass", "wand", etc
      @param templateFile The template for this weapon, eg. "Halberd Template.txt", "Dagger Template.txt", etc
      @param rank The rank of this item, eg. A3, E2, C1, D4, S2, etc
      @param lengthModifier The modifier based on the length of the name of the item-- items with longer names receive large stat modifiers
      @param ps The file writer for this item's text file
      
      @see createWeaponItemFiles() -- see text files
   */
   private static void writeNewWeaponItemFile( String type, String templateFile, String rank, double lengthModifier, PrintStream ps ) {
      Scanner weaponTempScanner = null;
      Scanner itemWeaponTempScanner = null;
      Scanner itemStatsTempScanner = null;
      Scanner itemATKTempScanner = null;
      Scanner itemSPEEDTempScanner = null;
      Scanner itemBonusPercentageTempScanner = null;
      try {
         weaponTempScanner = new Scanner( new File( ITEM_WEAPON_TEMPLATE ) );
         itemWeaponTempScanner = new Scanner( new File( templateFile ) );
         itemStatsTempScanner = new Scanner( new File( ITEM_STATS_TEMPLATE ) );
         itemATKTempScanner = new Scanner( new File( ITEM_ATK_TEMPLATE ) );
         itemSPEEDTempScanner = new Scanner( new File( ITEM_SPEED_TEMPLATE ) );
         itemBonusPercentageTempScanner = new Scanner( new File( ITEM_BONUS_PERCENTAGE_TEMPLATE ) );
      } catch( FileNotFoundException e ) {
         e.printStackTrace();
      }
      
      String rankLetter = String.valueOf( rank.charAt(0) );
      int rankNumber = Integer.valueOf( String.valueOf( rank.charAt(1) ) );
      
      //Type: / Rank:
      ps.println( weaponTempScanner.nextLine() + ": " + type );
      ps.println( weaponTempScanner.nextLine() + ": " + rank );
      
      //Attack:
      while( itemATKTempScanner.hasNextLine() ) {
         if( itemATKTempScanner.nextLine().split(" ")[0].equals( rankLetter ) ) {
            int value = Integer.valueOf( itemATKTempScanner.nextLine().split(" ")[ rankNumber - 1 ] );
            double weaponMod = Double.parseDouble( itemWeaponTempScanner.nextLine().split(" ")[1] );
            ps.println( weaponTempScanner.nextLine() + ": " + (int)(value*weaponMod*lengthModifier) );
            break;
         } else {
            itemATKTempScanner.nextLine();
         }
      }
      
      //Speed:
      while( itemSPEEDTempScanner.hasNextLine() ) {
         String line = itemSPEEDTempScanner.nextLine();
         if( line.contains( type ) ) {
            ps.println( weaponTempScanner.nextLine() + ": " + line.split(": ")[0].toLowerCase() );
            break;
         }
      }
      
      //Effects:
      ps.println( weaponTempScanner.nextLine() + ": none" );
      
      //BonusStrength: / BonusAgility: / BonusSense: / BonusVitality: / BonusIntelligence: / BonusHP: / BonusMP:
      Random random = new Random();
      final int OUT_OF_ONE_HUNDRED = 100;
      int bonusPercent = 0;
      while( itemBonusPercentageTempScanner.hasNextLine() ) {
         if( itemBonusPercentageTempScanner.nextLine().split(" ")[0].equals( rankLetter ) ) {
            bonusPercent = Integer.parseInt( itemBonusPercentageTempScanner.nextLine().split(" ")[ rankNumber - 1] );
            break;
         } else {
            itemBonusPercentageTempScanner.nextLine();
         }
      }
      while( itemStatsTempScanner.hasNextLine() ) {
         if( itemStatsTempScanner.nextLine().split(" ")[0].equals( rankLetter ) ) {
            int value = Integer.valueOf( itemStatsTempScanner.nextLine().split(" ")[ rankNumber - 1 ] );
            
            double weaponModStrength = Double.parseDouble( itemWeaponTempScanner.nextLine().split(" ")[1] );
            double weaponModAgility = Double.parseDouble( itemWeaponTempScanner.nextLine().split(" ")[1] );
            double weaponModSense = Double.parseDouble( itemWeaponTempScanner.nextLine().split(" ")[1] );
            double weaponModVitality = Double.parseDouble( itemWeaponTempScanner.nextLine().split(" ")[1] );
            double weaponModIntelligence = Double.parseDouble( itemWeaponTempScanner.nextLine().split(" ")[1] );
            double weaponModHP = Double.parseDouble( itemWeaponTempScanner.nextLine().split(" ")[1] );
            double weaponModMP = Double.parseDouble( itemWeaponTempScanner.nextLine().split(" ")[1] );
            
            if( random.nextInt( OUT_OF_ONE_HUNDRED ) < bonusPercent ) ps.println( weaponTempScanner.nextLine() + ": " + (int)(value*weaponModStrength*lengthModifier) );
            else                                                      ps.println( weaponTempScanner.nextLine() + ": 0" );
            if( random.nextInt( OUT_OF_ONE_HUNDRED ) < bonusPercent ) ps.println( weaponTempScanner.nextLine() + ": " + (int)(value*weaponModAgility*lengthModifier) );
            else                                                      ps.println( weaponTempScanner.nextLine() + ": 0" );
            if( random.nextInt( OUT_OF_ONE_HUNDRED ) < bonusPercent ) ps.println( weaponTempScanner.nextLine() + ": " + (int)(value*weaponModSense*lengthModifier) );
            else                                                      ps.println( weaponTempScanner.nextLine() + ": 0" );
            if( random.nextInt( OUT_OF_ONE_HUNDRED ) < bonusPercent ) ps.println( weaponTempScanner.nextLine() + ": " + (int)(value*weaponModVitality*lengthModifier) );
            else                                                      ps.println( weaponTempScanner.nextLine() + ": 0" );
            if( random.nextInt( OUT_OF_ONE_HUNDRED ) < bonusPercent ) ps.println( weaponTempScanner.nextLine() + ": " + (int)(value*weaponModIntelligence*lengthModifier) );
            else                                                      ps.println( weaponTempScanner.nextLine() + ": 0" );
            if( random.nextInt( OUT_OF_ONE_HUNDRED ) < bonusPercent ) ps.println( weaponTempScanner.nextLine() + ": " + (int)(value*weaponModHP*lengthModifier) );
            else                                                      ps.println( weaponTempScanner.nextLine() + ": 0" );
            if( random.nextInt( OUT_OF_ONE_HUNDRED ) < bonusPercent ) ps.println( weaponTempScanner.nextLine() + ": " + (int)(value*weaponModMP*lengthModifier) );
            else                                                      ps.println( weaponTempScanner.nextLine() + ": 0" );
            break;
         } else {
            itemStatsTempScanner.nextLine();
         }
      }
      
      //Description:
      ps.println( weaponTempScanner.nextLine() + ": " );
      
      weaponTempScanner.close();
      itemWeaponTempScanner.close();
      itemStatsTempScanner.close();
      itemATKTempScanner.close();
      itemSPEEDTempScanner.close();
      itemBonusPercentageTempScanner.close();
   }
   
   /**
      Write the accessory item files (includes earrings, pendants, and rings) using the item accessory template and the accessory text files
      
      @see ITEM_EARRINGS text file
      @see ITEM_PENDANTS text file
      @see ITEM_RINGS text file
      
      @see writeNewAccessoryItemFile( String type, String rank, String modifiers, String bonuses, PrintStream ps )
   */
   private static void createAccessoryItemFiles() {
      Scanner earringsFileScanner = null;
      Scanner pendantsFileScanner = null;
      Scanner ringsFileScanner = null;
      try {
         earringsFileScanner = new Scanner( new File( ITEM_EARRINGS ) );
         pendantsFileScanner = new Scanner( new File( ITEM_PENDANTS ) );
         ringsFileScanner = new Scanner( new File( ITEM_RINGS ) );
      } catch( FileNotFoundException e ) {
         e.printStackTrace();
      }
      
      String[] types = { "earring", "pendant", "ring" };
      
      Scanner[] scannerList = { earringsFileScanner, pendantsFileScanner, ringsFileScanner }; //list of scanners to go through for accessory items

      for( int i = 0; i < scannerList.length; i++ ) {
         Scanner currentScanner = scannerList[i];
         while( currentScanner.hasNextLine() ) {
            String line = currentScanner.nextLine();
            line = line.replaceAll( "[^a-zA-Z0-9 \\-',\\.@:\\|]", "" ); //Gets rid of beginning of text file junk values
            String[] pieces = line.split("@");
            String itemName = pieces[0];
            String[] modsAndEffects = pieces[1].split( "\\|", -1 );
            String modifiers = modsAndEffects[0];
            
            //@@DEBUG
            //System.out.println("Line: " + line );
            
            String bonuses = modsAndEffects[1];
            String rank = pieces[2].split(",")[0];
            
            File newItemFile = null;
            PrintStream ps = null;
            try {
      			newItemFile = new File( NEW_ITEM_FILE_PREFIX + itemName.toLowerCase() + ".txt" );
      		   ps = new PrintStream( newItemFile );
      		} catch( IOException e ) {
      			System.out.println(e);
      		}
            
            writeNewAccessoryItemFile( types[i], rank, modifiers, bonuses, ps );
            
            ps.close();
         }
      }
      
      earringsFileScanner.close();
      pendantsFileScanner.close();
      ringsFileScanner.close();
   
   }
   
   /**
      Write the text file for the given accessory item
      
      @param type The type of the accessory, either "earring", "pendant", or "ring"
      @param rank The rank of the item, eg. A3, E2, B1, C3, C5, D4
      @param modifiers The list of modifiers and their values, eg. AttackMod:2.0, ReductionPiercingMod:1.5, RegenerationMod:0.5, etc
      @param bonuses The list of bonuses and their values, eg. BonusAttack:10,BonusHP:100,BonusVitality:10, etc
      @param ps The file printer for this item's text file
      
      @see ITEM_ACCESSORY_TEMPLATE text file
   */
   private static void writeNewAccessoryItemFile( String type, String rank, String modifiers, String bonuses, PrintStream ps ) {
      Scanner itemAccessoryTempScanner = null;
      try {
         itemAccessoryTempScanner = new Scanner( new File( ITEM_ACCESSORY_TEMPLATE ) );
      } catch( FileNotFoundException e ) {
         e.printStackTrace();
      }
      
      ps.println( itemAccessoryTempScanner.nextLine() + ": " + type );
      ps.println( itemAccessoryTempScanner.nextLine() + ": " + rank );
      
      if( modifiers.equals("") )
         modifiers = "none";
      ps.println( itemAccessoryTempScanner.nextLine() + ": " + modifiers );
      
      //Need to break bonuses into pieces to follow the template
      String[] bonusHeaders = { "BonusAttack", "BonusDefense", "BonusHP", "BonusMP", "BonusStrength", "BonusAgility", "BonusSense", "BonusVitality", "BonusIntelligence" };
      for( int i = 0; i < 9; i++ ) {
         int bonusValue = 0;
         if( bonuses.contains( bonusHeaders[i] ) ) {
            int bonusHeaderIndex = bonuses.indexOf( bonusHeaders[i] );
            int commaIndex = bonuses.indexOf( ",", bonusHeaderIndex );
            if( commaIndex == -1 )
               bonusValue = Integer.parseInt( bonuses.substring( bonusHeaderIndex + bonusHeaders[i].length() + 1 ) );
            else
               bonusValue = Integer.parseInt( bonuses.substring( bonusHeaderIndex + bonusHeaders[i].length() + 1, commaIndex ) );
         }
         
         ps.println( itemAccessoryTempScanner.nextLine() + ": " + bonusValue );
      }
      
      ps.println( itemAccessoryTempScanner.nextLine() + ": " );
      
      itemAccessoryTempScanner.close();
   }
   
   /**
      Writes the potions text files, using the potions template
      
      @see ITEM_POTIONS text file
      @see ITEM_POTIONS_TEMPLATE text file
      
      @see writeNewPotionItemFile( String type, String rank, String effects, String duration, PrintStream ps )
   */
   private static void createPotionItemFiles() {
      Scanner potionsFileScanner = null;
      try {
         potionsFileScanner = new Scanner( new File( ITEM_POTIONS ) );
      } catch( FileNotFoundException e ) {
         e.printStackTrace();
      }
      
      while( potionsFileScanner.hasNextLine() ) {
         String line = potionsFileScanner.nextLine();
         line = line.replaceAll( "[^a-zA-Z0-9 \\-',\\.@:\\|%]", "" ); //Gets rid of beginning of text file junk values
         String[] pieces = line.split("@");
         String itemName = pieces[0];
         String[] itemEffectsAndDuration = pieces[1].split("\\|");
         String itemEffects = itemEffectsAndDuration[0];
         String itemDuration = itemEffectsAndDuration[1];
         String rank = pieces[2].split(",")[0];
         
         File newItemFile = null;
         PrintStream ps = null;
         try {
   			newItemFile = new File( NEW_ITEM_FILE_PREFIX + itemName.toLowerCase() + ".txt" );
   		   ps = new PrintStream( newItemFile );
   		} catch( IOException e ) {
   			System.out.println(e);
   		}
         
         writeNewPotionItemFile( rank, itemEffects, itemDuration, ps );
         
         ps.close();
      }
      
      potionsFileScanner.close();
   }
   
   /**
      Write the potion's text file, using the potions template
      
      @param rank The rank of this potion
      @param effects The effects that this potion gives when consumed eg. HP + 50%, Strength + 30%, IsInvisible: true, etc
      @param ps The file printer for this item's text file
      
      @see ITEM_POTIONS_TEMPLATE text file
   */
   private static void writeNewPotionItemFile( String rank, String effects, String duration, PrintStream ps ) {
      Scanner itemPotionsTempScanner = null;
      try {
         itemPotionsTempScanner = new Scanner( new File( ITEM_POTIONS_TEMPLATE ) );
      } catch( FileNotFoundException e ) {
         e.printStackTrace();
      }
      
      ps.println( itemPotionsTempScanner.nextLine() + ": potion" );
      ps.println( itemPotionsTempScanner.nextLine() + ": " + rank );
      ps.println( itemPotionsTempScanner.nextLine() + ": " + effects );
      ps.println( itemPotionsTempScanner.nextLine() + ": " + duration );
      ps.println( itemPotionsTempScanner.nextLine() + ": " );
      
      itemPotionsTempScanner.close();
   }
   
   /**
      Writes the orb text files, using the orb template
      
      @see ITEM_ORBS text file
      @see ITEM_ORB_TEMPLATE text file
      
      @see writeNewOrbItemFile( String type, String rank, String effects, PrintStream ps )
   */
   private static void createOrbItemFiles() {
      Scanner orbFileScanner = null;
      try {
         orbFileScanner = new Scanner( new File( ITEM_ORBS ) );
      } catch( FileNotFoundException e ) {
         e.printStackTrace();
      }
      
      while( orbFileScanner.hasNextLine() ) {
         String line = orbFileScanner.nextLine();
         line = line.replaceAll( "[^a-zA-Z0-9 \\-',\\.@:]", "" ); //Gets rid of beginning of text file junk values
         String[] pieces = line.split("@");
         String itemName = pieces[0];
         String itemEffects = pieces[1];
         String rank = pieces[2].split(",")[0];
         
         File newItemFile = null;
         PrintStream ps = null;
         try {
   			newItemFile = new File( NEW_ITEM_FILE_PREFIX + itemName.toLowerCase() + ".txt" );
   		   ps = new PrintStream( newItemFile );
   		} catch( IOException e ) {
   			System.out.println(e);
   		}
         
         writeNewOrbItemFile( rank, itemEffects, ps );
         
         ps.close();
      }
      
      orbFileScanner.close();
   }
   
   /**
      Write the orb's text file, using the orb template
      
      @param rank The rank of this orb
      @param effects The effects that this orb gives when equipped eg. AttackMod: 2.0, ReputationMod: 0.5, MPMod: 1.5
      @param ps The file printer for this item's text file
      
      @see ITEM_ORB_TEMPLATE text file
   */
   private static void writeNewOrbItemFile( String rank, String effects, PrintStream ps ) {
      Scanner itemOrbTempScanner = null;
      try {
         itemOrbTempScanner = new Scanner( new File( ITEM_ORB_TEMPLATE ) );
      } catch( FileNotFoundException e ) {
         e.printStackTrace();
      }
      
      ps.println( itemOrbTempScanner.nextLine() + ": orb" );
      ps.println( itemOrbTempScanner.nextLine() + ": " + rank );
      ps.println( itemOrbTempScanner.nextLine() + ": " + effects );
      ps.println( itemOrbTempScanner.nextLine() + ": " );
      
      itemOrbTempScanner.close();
   }
   
   /**
      Create all the default item files based on the template for default items
      
      @see writeNewDefaultItemFile( String type, String rank, PrintStream ps )
   */
   private static void createDefaultItemFiles() {
      Scanner materialsFileScanner = null;
      Scanner runestonesFileScanner = null;
      Scanner othersFileScanner = null;
      try {
         materialsFileScanner = new Scanner( new File( ITEM_MATERIALS ) );
         runestonesFileScanner = new Scanner( new File( ITEM_RUNESTONES ) );
         othersFileScanner = new Scanner( new File( ITEM_OTHERS ) );
      } catch( FileNotFoundException e ) {
         e.printStackTrace();
      }
      
      String[] types = { "material", "runestone", "other" };
      
      Scanner[] scannerList = { materialsFileScanner, runestonesFileScanner, othersFileScanner }; //list of scanners to go through for default items (might be more later)
      
      for( int i = 0; i < scannerList.length; i++ ) {
         Scanner currentScanner = scannerList[i];
         while( currentScanner.hasNextLine() ) {
            String line = currentScanner.nextLine();
            line = line.replaceAll( "[^a-zA-Z0-9 \\-',]", "" ); //Gets rid of beginning of text file junk values
            int lastIndexSpace = line.lastIndexOf(" ");
            String itemName = line.substring( 0, lastIndexSpace );
            String itemRanks = line.substring( lastIndexSpace + 1 );
            String rank = itemRanks.split(",")[0];
            
            File newItemFile = null;
            PrintStream ps = null;
            try {
      			newItemFile = new File( NEW_ITEM_FILE_PREFIX + itemName.toLowerCase() + ".txt" );
      		   ps = new PrintStream( newItemFile );
      		} catch( IOException e ) {
      			System.out.println(e);
      		}
            
            writeNewDefaultItemFile( types[i], rank, ps );
            
            ps.close();
         }
      }
      
      materialsFileScanner.close();
      runestonesFileScanner.close();
      othersFileScanner.close();
   }
   
   /**
      Writes the item's text file, using the default item template
      
      Note that these files are left with empty descriptions
      
      @param type The type of the item
      @param rank The rank of the item. egs. E2, E5, B3, S3, C5
      @param ps The file printer for this item's text file
   */
   private static void writeNewDefaultItemFile( String type, String rank, PrintStream ps ) {
      Scanner itemDefTempScanner = null;
      try {
         itemDefTempScanner = new Scanner( new File( ITEM_DEFAULT_TEMPLATE ) );
      } catch( FileNotFoundException e ) {
         e.printStackTrace();
      }
      
      ps.println( itemDefTempScanner.nextLine() + ": " + type );
      ps.println( itemDefTempScanner.nextLine() + ": " + rank );
      ps.println( itemDefTempScanner.nextLine() + ": " );
      
      itemDefTempScanner.close();
   }
   
   /**
      Creates all the monster text files and defines their values by using the templates for the monster files
      and monster stats files based on rank and monster type
      
      @see writeNewMonsterFile( Scanner monsterTempScanner, String monsterName, String monsterType, String rank, PrintStream ps )
      
      @see MONSTER_FILE text file
      @see MONSTER_TEMPLATE text file
   */
   private static void createMonsterFiles() {
      Scanner monsterFileScanner = null;
      Scanner monsterTempScanner = null;
      try {
         monsterFileScanner = new Scanner( new File( MONSTER_FILE ) );
         monsterTempScanner = new Scanner( new File( MONSTER_TEMPLATE ) );
      } catch( FileNotFoundException e ) {
         e.printStackTrace();
      }
      
      String rank = monsterFileScanner.nextLine();
      rank = rank.replaceAll( "[^a-zA-Z0-9]", "" ); //Gets rid of beginning of text file junk values. Keep in mind this also replaces spaces
      while( monsterFileScanner.hasNextLine() ) {
         String monsterLine = monsterFileScanner.nextLine();
         if( !monsterLine.equals("@@END") ) {
            String[] monsterAndType = monsterLine.split("@");
            String monsterName = monsterAndType[0];
            String monsterType = monsterAndType[1];
            
            File newMonsterFile = null;
            PrintStream ps = null;
            try {
      			newMonsterFile = new File( NEW_MONSTER_FILE_PREFIX + monsterName.toLowerCase() + ".txt" );
      		   ps = new PrintStream( newMonsterFile );
      		} catch( IOException e ) {
      			System.out.println(e);
      		}
            
            writeNewMonsterFile( monsterTempScanner, monsterType, rank, ps );
            
            ps.close();
            monsterTempScanner.close();
            
            //Restart temp scanner from the top
            try {
               monsterTempScanner = new Scanner( new File( MONSTER_TEMPLATE ) );
            } catch( FileNotFoundException e ) {
               e.printStackTrace();
            }
         } else if( monsterFileScanner.hasNextLine() ) {
            rank = monsterFileScanner.nextLine();
            rank = rank.replaceAll( "[^a-zA-Z0-9]", "" ); //Gets rid of beginning of text file junk values. Keep in mind this also replaces spaces
         }
      }
      
      monsterTempScanner.close();
      monsterFileScanner.close();
   }
   
   /**
      Write this monster's text file, using its rank to determine its stats as well as the monster's type
      
      @param monsterTempScanner Scans the text file that holds the labels for the various monster values
      @param monsterType Used to grab the text file that holds the value modifiers
      @param rank The rank of the monster. egs. E2, E5, B3, S3, C5
      @param ps The file printer for this monster's text file
      
      @see getMonsterValueFromFile( Scanner monsterValueScanner, String valueName, char rankLetter, int rankDivision, Scanner monsterTypeScanner )
      
      @see MONSTER_ATK text file
      @see MONSTER_DEF text file
      @see MONSTER_HP text file
      @see MONSTER_MP text file
      @see MONSTER_STATS text file
      @see MONSTER_ARCHER text file
      @see MONSTER_BOSS text file
      @see MONSTER_DARK text file
      @see MONSTER_DEFENSIVE text file
      @see MONSTER_FIGHTER text file
      @see MONSTER_MAGE text file
      @see MONSTER_NORMAL text file
      @see MONSTER_TANK text file
      @see MONSTER_WEAK text file
   */
   private static void writeNewMonsterFile( Scanner monsterTempScanner, String monsterType, String rank, PrintStream ps ) {
      Scanner monsterATKScanner = null;
      Scanner monsterDEFScanner = null;
      Scanner monsterHPScanner = null;
      Scanner monsterMPScanner = null;
      Scanner monsterSTATSScanner = null;
      Scanner monsterTypeScanner = null;
      try {
         monsterATKScanner = new Scanner( new File( MONSTER_ATK ) );
         monsterDEFScanner = new Scanner( new File( MONSTER_DEF ) );
         monsterHPScanner = new Scanner( new File( MONSTER_HP ) );
         monsterMPScanner = new Scanner( new File( MONSTER_MP ) );
         monsterSTATSScanner = new Scanner( new File( MONSTER_STATS ) );
         monsterTypeScanner = new Scanner( new File( TEMPLATE_FILE_PREFIX + monsterType.toUpperCase() + ".txt" ) );
      } catch( FileNotFoundException e ) {
         e.printStackTrace();
      }
      
      char rankLetter = rank.charAt(0);
      int rankDivision = Character.getNumericValue( rank.charAt(1) );
      
      //Type: / Rank: 
      ps.println( monsterTempScanner.nextLine() + ": " + monsterType );
      ps.println( monsterTempScanner.nextLine() + ": " + rank );

      //Attack: / Defense: / HP: / MP:
      int nextValue = getMonsterValueFromFile( monsterATKScanner, "ATK", rankLetter, rankDivision, monsterTypeScanner );
      ps.println( monsterTempScanner.nextLine() + ": " + nextValue );
      nextValue = getMonsterValueFromFile( monsterDEFScanner, "DEF", rankLetter, rankDivision, monsterTypeScanner );
      ps.println( monsterTempScanner.nextLine() + ": " + nextValue );
      nextValue = getMonsterValueFromFile( monsterHPScanner, "HP", rankLetter, rankDivision, monsterTypeScanner );
      ps.println( monsterTempScanner.nextLine() + ": " + nextValue );
      nextValue = getMonsterValueFromFile( monsterMPScanner, "MP", rankLetter, rankDivision, monsterTypeScanner );
      ps.println( monsterTempScanner.nextLine() + ": " + nextValue );
      
      //Strength: / Agility: / Sense: / Vitality: / Intelligence:
      int oldValue = getMonsterValueFromFile( monsterSTATSScanner, "STATS", rankLetter, rankDivision, monsterTypeScanner );   //Make sure to use old value for rest of stats
      nextValue = variateValue( oldValue, monsterTypeScanner );
      ps.println( monsterTempScanner.nextLine() + ": " + nextValue );
      
      nextValue = variateValue( oldValue, monsterTypeScanner );
      ps.println( monsterTempScanner.nextLine() + ": " + nextValue );
      nextValue = variateValue( oldValue, monsterTypeScanner );
      ps.println( monsterTempScanner.nextLine() + ": " + nextValue );
      nextValue = variateValue( oldValue, monsterTypeScanner );
      ps.println( monsterTempScanner.nextLine() + ": " + nextValue );
      nextValue = variateValue( oldValue, monsterTypeScanner );
      ps.println( monsterTempScanner.nextLine() + ": " + nextValue );
      
      //Description:    --> must be done manually
      ps.println( monsterTempScanner.nextLine() + ": " );
      
      //Close scanners
      monsterATKScanner.close();
      monsterDEFScanner.close();
      monsterHPScanner.close();
      monsterMPScanner.close();
      monsterSTATSScanner.close();
      monsterTypeScanner.close();
   }
   
   /**
      Gets the next value of the monster and modifies the value based on monster type and randomness (+-10%)
      
      @param monsterValueScanner The current value text file scanner
      @param valueName The name of the value currently being pulled
      @param rankLetter The letter of the monster's rank
      @param rankDivision The number division of the monster's rank
      @param monsterTypeScanner The scanner used to find the value modifiers for the different stats
      @return int The modified value of the monster
      
      @see variateValue( int value, Scanner monsterTypeScanner )
   */
   private static int getMonsterValueFromFile( Scanner monsterValueScanner, String valueName, char rankLetter, int rankDivision, Scanner monsterTypeScanner ) {
      int value = -1;
      while( monsterValueScanner.hasNextLine() ) {
         String nextValueLine = monsterValueScanner.nextLine();
         nextValueLine = nextValueLine.replaceAll( "[^a-zA-Z0-9]", "" );            //Gets rid of beginning of text file junk values. Keep in mind this also replaces spaces
         if( nextValueLine.equals( String.valueOf( rankLetter ) + valueName ) ) {
            String[] divisionValues = monsterValueScanner.nextLine().split(" ");
            value = Integer.valueOf( divisionValues[ rankDivision - 1 ] );
            
            //Variate value
            if( !valueName.equals("STATS") )
               return variateValue( value, monsterTypeScanner );
            else
               return value;
         }
      }
      
      System.out.println(" ERROR: Should not be reachable -- MonsterFileBuilder -- getMonsterValueFromFile(..)");
      return value;
   }
   
   /**
      Applies the monster type modifier to the value and randomizes the value (+-10%)
      
      @param value The value to be modified
      @param monsterTypeScanner The scanner used to find the value modifiers for the different stats
      @return int The modified value of the monster
   */
   private static int variateValue( int value, Scanner monsterTypeScanner ) {
      Random random = new Random();
   
      //Process type changes
      String typeLine = monsterTypeScanner.nextLine();
      typeLine = typeLine.replaceAll( "[^a-zA-Z0-9\\. ]", "" ); //Gets rid of beginning of text file junk values. Does not replace spaces or periods
      value *= Double.valueOf( typeLine.split(" ")[1] );
      
      //Add variability
      double variability = value*0.20;
      int range = (int)(variability*2.0);
      if( range > 0 )
         value = value - (int)variability + random.nextInt( range );
      
      return value;
   }

}