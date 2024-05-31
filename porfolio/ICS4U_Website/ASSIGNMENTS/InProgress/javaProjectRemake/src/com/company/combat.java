package com.company;
import java.util.*;
import java.io.*;
public class combat {

    public static boolean floor(int[] player1Stats, int[] player2Stats, int[] mob1Stats, int[] mob2Stats, String[][] participants, String name) {
        int turnCount = 1;
        System.out.println("You have encounter two monsters! \nThe first monster is called "+participants[1][0]+" and has the stats of HP: " + mob1Stats[0] + ", Attack: " + mob1Stats[1] + ", Magic: " + mob1Stats[2] + ", Defense: " + mob1Stats[3] + ", Magic Resist: " + mob1Stats[4] + ", Speed: " + mob1Stats[5]);
        System.out.println("The second monster is called "+participants[1][1]+" and has the stats of HP: " + mob2Stats[0] + ", Attack: " + mob2Stats[1] + ", Magic: " + mob2Stats[2] + ", Defense: " + mob2Stats[3] + ", Magic Resist: " + mob2Stats[4] + ", Speed: " + mob2Stats[5]);
        //Checks if your party is faster than both the mobs
        if (combatTurn(player1Stats[5], player2Stats[5], mob1Stats[5], mob2Stats[5])) {
            while (player1Stats[0] + player2Stats[0] > 0 && mob1Stats[0] + mob2Stats[0] > 0) {
                System.out.println("/////////////Round " + turnCount + " is now underway!/////////////");
                System.out.println("It is now your party's turn\nPlease decide what you are going to do!");
                //print current stats
                printStats(player1Stats,player2Stats,mob1Stats,mob2Stats,participants);
                //player phase
                if(player1Stats[0]>0){
                    yourTurn(playerDecision(participants[0][0]), player1Stats, player2Stats, mob1Stats, mob2Stats, participants[0][0], participants[0][1], participants, name);
                }

                if(player2Stats[0]>0){
                    yourTurn(playerDecision(participants[0][1]), player2Stats, player1Stats, mob1Stats, mob2Stats, participants[0][1], participants[0][0], participants, name);
                }

                //enemy phase
                System.out.println("Now, it is your enemies turn");

                if(mob1Stats[0]>0){
                    enemyTurn(player1Stats, player2Stats, mob1Stats, mob2Stats, participants[1][0], participants[1][1], name);
                }

                if(mob2Stats[0]>0){
                    enemyTurn(player1Stats, player2Stats, mob2Stats, mob1Stats, participants[1][1], participants[1][0], name);
                }

                turnCount++;
            }

        } else {
            while (player1Stats[0] + player2Stats[0] > 0 && mob1Stats[0] + mob2Stats[0] > 0) {
                System.out.println("/////////////Round " + turnCount + " is now underway!/////////////");
                System.out.println("It is the enemies' turn!");
                //print stats again
                printStats(player1Stats,player2Stats,mob1Stats,mob2Stats,participants);
                //enemy phase
                if(mob1Stats[0]>0){
                    enemyTurn(player1Stats, player2Stats, mob1Stats, mob2Stats, participants[1][0], participants[1][1], name);
                }

                if(mob2Stats[0]>0){
                    enemyTurn(player1Stats, player2Stats, mob2Stats, mob1Stats, participants[1][1], participants[1][0], name);
                }
                System.out.println("Your turn now!");
                //player phase
                if(player1Stats[0]>0){
                    yourTurn(playerDecision(participants[0][0]), player1Stats, player2Stats, mob1Stats, mob2Stats, participants[0][0], participants[0][1], participants, name);
                }

                if(player2Stats[0]>0){
                    yourTurn(playerDecision(participants[0][1]), player2Stats, player1Stats, mob1Stats, mob2Stats, participants[0][1], participants[0][1], participants, name);
                }

                turnCount++;
            }
        }
        
        if(player1Stats[0] + player2Stats[0] <=0){
            //this is intentional so that it sticks out
            System.out.println("                     Your party has been wiped!\n                          Off the the grave you go!");
            return true;
        }
        
        else{
            System.out.println("                       The enemy party has fainted!\n                          Moving on to the next floor!");
            return false;
        }
    }


    public static void yourTurn(int decision, int[] player1Stats, int[] player2Stats, int[] mob1Stats, int[] mob2Stats, String caster, String partner, String[][] participants, String name) {
        //insert parameters
        //1 for attack 2 for magic 3 for heal
        if (decision == 1) {
            if (attackDecision(participants[1][0], participants[1][1],false) == 1) {
                mob1Stats[0]=attack(participants[1][0], mob1Stats[0], mob1Stats[3], player1Stats[1]);
            }
            else {
                mob2Stats[0]=attack(participants[1][1], mob2Stats[0], mob2Stats[3], player1Stats[1]);
            }
        } else if (decision == 2) {
            if (attackDecision(participants[1][0], participants[1][1],false) == 1) {
                mob1Stats[0]=magicAttack(participants[1][0], mob1Stats[0], mob1Stats[4], player1Stats[2]);
            }
            else {
                mob2Stats[0]=magicAttack(participants[1][1], mob2Stats[0], mob2Stats[4], player1Stats[2]);
            }
            
        } else if (decision == 3) {
            if(attackDecision(participants[0][0], participants[0][1],true)==1){
                player1Stats[0]= heal(player1Stats[0], player1Stats[2], caster);
            }

            else{
                player2Stats[0]=heal(player2Stats[0], player1Stats[2], partner);
            }

        }
    }

    //gets the players decision
    public static int playerDecision(String caster) {
        System.out.println("For " + caster +"'s action, please type 1 for attack, 2 for magic attack, and 3 for heal");
        Scanner scan = new Scanner(System.in);
        int choice = scan.nextInt();
        return choice;
    }
    //Target which ally or enemy
    public static int attackDecision(String mob1Name, String mob2Name,boolean heal) {
        Scanner scan = new Scanner(System.in);
        int choice;
        if(heal){
            System.out.println("Who to heal? Enter 1 to 'self heal', 2 to 'friendly heal'.");
            choice=scan.nextInt();
            return choice;
        }

        else{
            System.out.println("Press 1 to hit " + mob1Name + ", 2 for " + mob2Name);
            choice = scan.nextInt();
            return choice;
        }
    }

    //The function that checks which party is faster
    public static boolean combatTurn(int player1Spd, int player2Spd, int mob1Spd, int mob2Spd) {
        if (player1Spd + player2Spd > mob1Spd + mob2Spd) {
            return true;
        } else {
            return false;
        }
    }


    //Player options
    //Use physical attack
    public static int attack(String mobName, int mobHP, int mobDef, int playerAtk) {
        int damage=playerAtk-mobDef;

        if(damage<0){
            //makes sure damage is not less than 1 fo balance
            damage=1;
        }

        mobHP = mobHP-damage;
        if (mobHP <= 0) {
            System.out.println(mobName + " has fainted!");
            mobHP = 0;
        } else {
            System.out.println("Attacked " + mobName + " for "+damage+" damage! He now has " + mobHP + " HP");
        }
        return mobHP;
    }
    //Use magic attack
    public static int magicAttack(String mobName, int mobHP, int mobRes, int playerMag) {
        int damage=playerMag-mobRes;
        if(damage<0){
            damage=1;
        }
        mobHP=mobHP-damage;
        if (mobHP <= 0) {
            System.out.println(mobName + " has fainted!");
            mobHP = 0;
        } else {
            System.out.println("Attacked " + mobName + " with magic for "+damage+" damage! He now has " + mobHP + " HP");
        }
        return mobHP;
    }
    //Heal yourself or your partner
    public static int heal(int HP, int mag, String receiver) {
        //you can heal yourself above your current hp to we need to fix that. No its not a bug its a feature...
        HP = HP + mag;
        System.out.println("Healed " + receiver + " for " + mag + "! " + receiver + " now has " + HP + " HP!");
        return HP;
        /*
        Scanner scan = new Scanner(System.in);
        System.out.println("Who do you want to heal? 1 for yourself, 2 for " + name + "!");
        int choice = scan.nextInt();
        if (choice == 1) {
            HP1 = HP1 + playerMag;
            System.out.println("Healed for " + playerMag + "! Now has " + HP1+ " HP!");
            return HP1;
        } else {
            HP2 = HP2 + playerMag;
            System.out.println("Healed " + name + "! He has " + HP2 + " HP now!");
            return HP2;
        }

         */
    }

    //enemy moves---------------------------------------------------------------
    public static void enemyTurn(int[] player1Stats, int[] player2Stats, int[] mob1Stats, int[] mob2Stats, String participants, String participants2, String name) {
        if (mob1Stats[0] < 5) {
            mob1Stats[0]=enemySelfHeal(mob1Stats[0], mob1Stats[2], participants);

        } else if (mob2Stats[0] < 5) {
            mob2Stats[0]=enemyPartnerHeal(mob2Stats[0], mob1Stats[2], participants, participants2);
        } else {
            enemyAtk(player1Stats, player2Stats, mob1Stats, mob2Stats, participants, name);
        }
    }
    /*** need names ***/
    //Enemy attacks
    public static int enemyAtk(int[] player1Stats, int[] player2Stats, int[] mob1Stats, int[] mob2Stats, String participants, String name) {
        int damage;
        if (Math.random() < 0.5) {
            //attack player1
            //decide attack or magic
            if (player1Stats[3] < player1Stats[4]) {
                damage=mob1Stats[1]-player1Stats[3];

                if (damage < 0) {
                    damage=0;
                }
                player1Stats[0] = player1Stats[0]-damage;
                System.out.println(participants + " just attacked you with physical damage for "+damage+" damage!");
                System.out.println("You have " + player1Stats[0] + " HP now!");
            } else {
                damage=mob1Stats[2]-player1Stats[4];
                if (damage < 0) {
                    damage=0;
                }

                player1Stats[0] = player1Stats[0]-damage;
                System.out.println(participants + " just attacked you with magical damage for "+damage+" damage!");
                System.out.println("You have " + player1Stats[0] + " HP now!");
            }return player1Stats[0];
        } else {
            //attack player2
            //decide attack or magic
            if (player2Stats[3] < player2Stats[4]) {
                damage= mob1Stats[1]-player2Stats[3];
                if (damage < 0) {
                    damage=0;
                    System.out.println("You fully defended the attack!");
                }
                player2Stats[0] = player2Stats[0]-damage;
                System.out.println(participants + " just attacked your partner " + name + " with physical damage for "+damage+" damage!");
                System.out.println("He has " + player2Stats[0] + " HP now!");
            } else {
                damage= mob1Stats[2]-player2Stats[4];
                if (damage < 0) {
                    damage=0;
                    System.out.println("You fully defended the attack!");
                }
                player2Stats[0] = player2Stats[0] -damage;
                System.out.println(participants + " just attacked your partner " + name + " with magical damage for "+damage+" damage!");
                System.out.println("He has " + player2Stats[0] + " HP now!");
            }return player2Stats[0];
        }
    }
    //enemy heals itself
    public static int enemySelfHeal( int mobHP, int mobMagic, String name)
    {
        mobHP = mobHP + mobMagic;
        System.out.println(name + " heals itself for " + mobMagic+ ". It now has " + mobHP + " HP!");
        return mobHP;
    }
    //enemy heals its partner
    public static int enemyPartnerHeal( int mobHP2, int mobMag1, String name, String name2)
    {
        mobHP2 = mobHP2 + mobMag1;
        System.out.println(name + " heals " + name2 + " for " + mobMag1+ ". It now has " + mobHP2 + " HP!");
        return mobHP2;
    }
    public static void printStats(int[] member1, int[] member2, int[] mob1, int[] mob2, String[][] everyone){
        System.out.println("\nYour stats are HP: "+member1[0]+", Attack: "+member1[1]+", Magic: "+member1[2]+", Defense: "+member1[3]+", Magic Resist: "+member1[4]+", Speed: "+member1[5]+" ");
        System.out.println("Your partner "+everyone[0][1]+" stats are HP: "+member2[0]+", Attack: "+member2[1]+", Magic: "+member2[2]+", Defense: "+member2[3]+", Magic Resist: "+member2[4]+", Speed: "+member2[5]+" ");
        //print enemy stats
        //line break
        System.out.println("\nYour enemy "+everyone[1][0]+" stats are HP: "+mob1[0]+", Attack: "+mob1[1]+", Magic: "+mob1[2]+", Defense: "+mob1[3]+", Magic Resist: "+mob1[4]+", Speed: "+mob1[5]+" ");
        System.out.println("Your partner "+everyone[1][1]+" stats are HP: "+mob2[0]+", Attack: "+mob2[1]+", Magic: "+mob2[2]+", Defense: "+mob2[3]+", Magic Resist: "+mob2[4]+", Speed: "+mob2[5]+"\n");
    }
}