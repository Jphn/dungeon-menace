/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeto.dugeonmenace.monster;

import java.util.Random;
import projeto.dugeonmenace.GamePanel;
import projeto.dugeonmenace.Score;
import projeto.dugeonmenace.Scoreboard;
import projeto.dugeonmenace.data.Progress;
import projeto.dugeonmenace.entity.Entity;
import projeto.dugeonmenace.objectsSprite.OBJ_Coin_Bronze;
import projeto.dugeonmenace.objectsSprite.OBJ_Door_Iron;
import projeto.dugeonmenace.objectsSprite.OBJ_Heart;
import projeto.dugeonmenace.objectsSprite.OBJ_ManaCrystal;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

/**
 *
 * @author T-GAMER
 */
public class MON_SkeletonLord extends Entity {
    GamePanel gp; // cuz of different package
    public static final String monName = "Skeleton Lord";

    public MON_SkeletonLord(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_monster;
        boss = true;
        name = monName;
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 40;
        life = maxLife;
        attack = 6;
        defense = 1;
        exp = 40;
        knockBackPower = 5;
        sleep = true;

        int size = gp.tileSize * 5;
        solidArea.x = 48;
        solidArea.y = 48;
        solidArea.width = size - 48 * 2;
        solidArea.height = size - 48;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 160;
        attackArea.height = 160;
        motion1_duration = 25;
        motion2_duration = 50;

        getImage();
        getAttackImage();
        setDialogue();
    }

    public void getImage() {
        int i = 5;
        if (inRage == false) {
            up1 = setup("/monster/skeletonlord_up_1.png", gp.tileSize * i, gp.tileSize * i);
            up2 = setup("/monster/skeletonlord_up_2.png", gp.tileSize * i, gp.tileSize * i);
            down1 = setup("/monster/skeletonlord_down_1.png", gp.tileSize * i, gp.tileSize * i);
            down2 = setup("/monster/skeletonlord_down_2.png", gp.tileSize * i, gp.tileSize * i);
            left1 = setup("/monster/skeletonlord_left_1.png", gp.tileSize * i, gp.tileSize * i);
            left2 = setup("/monster/skeletonlord_left_2.png", gp.tileSize * i, gp.tileSize * i);
            right1 = setup("/monster/skeletonlord_right_1.png", gp.tileSize * i, gp.tileSize * i);
            right2 = setup("/monster/skeletonlord_right_2.png", gp.tileSize * i, gp.tileSize * i);
        }

        if (inRage == true) {
            up1 = setup("/monster/skeletonlord_phase2_up_1.png", gp.tileSize * i, gp.tileSize * i);
            up2 = setup("/monster/skeletonlord_phase2_up_2.png", gp.tileSize * i, gp.tileSize * i);
            down1 = setup("/monster/skeletonlord_phase2_down_1.png", gp.tileSize * i, gp.tileSize * i);
            down2 = setup("/monster/skeletonlord_phase2_down_2.png", gp.tileSize * i, gp.tileSize * i);
            left1 = setup("/monster/skeletonlord_phase2_left_1.png", gp.tileSize * i, gp.tileSize * i);
            left2 = setup("/monster/skeletonlord_phase2_left_2.png", gp.tileSize * i, gp.tileSize * i);
            right1 = setup("/monster/skeletonlord_phase2_right_1.png", gp.tileSize * i, gp.tileSize * i);
            right2 = setup("/monster/skeletonlord_phase2_right_2.png", gp.tileSize * i, gp.tileSize * i);
        }
    }

    public void getAttackImage() {
        int i = 5;
        if (inRage == false) {
            attackUp1 = setup("/monster/skeletonlord_attack_up_1.png", gp.tileSize * i, gp.tileSize * 2 * i);
            attackUp2 = setup("/monster/skeletonlord_attack_up_2.png", gp.tileSize * i, gp.tileSize * 2 * i);
            attackDown1 = setup("/monster/skeletonlord_attack_down_1.png", gp.tileSize * i, gp.tileSize * 2 * i);
            attackDown2 = setup("/monster/skeletonlord_attack_down_2.png", gp.tileSize * i, gp.tileSize * 2 * i);
            attackLeft1 = setup("/monster/skeletonlord_attack_left_1.png", gp.tileSize * 2 * i, gp.tileSize * i);
            attackLeft2 = setup("/monster/skeletonlord_attack_left_2.png", gp.tileSize * 2 * i, gp.tileSize * i);
            attackRight1 = setup("/monster/skeletonlord_attack_right_1.png", gp.tileSize * 2 * i, gp.tileSize * i);
            attackRight2 = setup("/monster/skeletonlord_attack_right_2.png", gp.tileSize * 2 * i, gp.tileSize * i);
        }
        if (inRage == true) {
            attackUp1 = setup("/monster/skeletonlord_phase2_attack_up_1.png", gp.tileSize * i, gp.tileSize * 2 * i);
            attackUp2 = setup("/monster/skeletonlord_phase2_attack_up_2.png", gp.tileSize * i, gp.tileSize * 2 * i);
            attackDown1 = setup("/monster/skeletonlord_phase2_attack_down_1.png", gp.tileSize * i, gp.tileSize * 2 * i);
            attackDown2 = setup("/monster/skeletonlord_phase2_attack_down_2.png", gp.tileSize * i, gp.tileSize * 2 * i);
            attackLeft1 = setup("/monster/skeletonlord_phase2_attack_left_1.png", gp.tileSize * 2 * i, gp.tileSize * i);
            attackLeft2 = setup("/monster/skeletonlord_phase2_attack_left_2.png", gp.tileSize * 2 * i, gp.tileSize * i);
            attackRight1 = setup("/monster/skeletonlord_phase2_attack_right_1.png", gp.tileSize * 2 * i,
                    gp.tileSize * i);
            attackRight2 = setup("/monster/skeletonlord_phase2_attack_right_2.png", gp.tileSize * 2 * i,
                    gp.tileSize * i);
        }
    }

    public void setDialogue() {
        dialogues[0][0] = "No one can steal my treasure!";
        dialogues[0][1] = "You will die here!";
        dialogues[0][2] = "WELCOME TO YOUR DOOM!";
    }

    public void setAction() {
        if (inRage == false && life < maxLife / 2) {
            inRage = true;
            getImage();
            getAttackImage();
            defaultSpeed++;
            speed = defaultSpeed;
            attack += 4;
        }

        if (getTileDistance(gp.player) < 10) {
            moveTowardPlayer(60);
        } else {
            getRandomDirection(120);
        }

        // Check if it is attacks
        if (attacking == false) {
            checkAttackOrNot(60, gp.tileSize * 7, gp.tileSize * 5); // Small rate = More agressive
        }
    }

    public void damageReaction() {
        actionLockCounter = 0;
    }

    public void checkDrop() {
        gp.bossBattleOn = false;
        Progress.skeletonLordDefeated = true;

        // Restore the previous music
        gp.stopMusic();
        gp.playMusic(19);

        // Remove the iron doors
        for (int i = 0; i < gp.obj[1].length; i++) {
            if (gp.obj[gp.currentMap][i] != null &&
                    gp.obj[gp.currentMap][i].name.equals(OBJ_Door_Iron.objName)) {

                gp.playSE(21);
                gp.obj[gp.currentMap][i] = null;
            }
        }

        // CAST A DIE
        int i = new Random().nextInt(100) + 1;

        // SET THE MONSTER DROP
        if (i < 50) {
            dropItem(new OBJ_Coin_Bronze(gp));
        }
        if (i >= 50 && i < 75) {
            dropItem(new OBJ_Heart(gp));
        }
        if (i >= 75 && i < 100) {
            dropItem(new OBJ_ManaCrystal(gp));
        }
    }
}
