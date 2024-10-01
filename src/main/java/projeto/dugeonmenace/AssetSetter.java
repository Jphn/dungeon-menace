/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeto.dugeonmenace;

import projeto.dugeonmenace.data.Progress;
import projeto.dugeonmenace.entity.Npc_Merchant;
import projeto.dugeonmenace.entity.Npc_OldMan;
import projeto.dugeonmenace.monster.*;
import projeto.dugeonmenace.objectsSprite.*;
import tile_interactive.IT_DestructibleWall;

import tile_interactive.IT_DryTree;

/**
 *
 * @author LucianoNeto
 */
public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        int i = 0;
        int mapNum = 0;
        
        gp.obj[mapNum][i] = new OBJ_Coin_Bronze(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 25;
        gp.obj[mapNum][i].worldY = gp.tileSize * 23;
        i++;
        
        gp.obj[mapNum][i] = new OBJ_Coin_Bronze(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 21;
        gp.obj[mapNum][i].worldY = gp.tileSize * 19;
        i++;
                
        gp.obj[mapNum][i] = new OBJ_Coin_Bronze(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 26;
        gp.obj[mapNum][i].worldY = gp.tileSize * 21;
        i++;
        gp.obj[mapNum][i] = new OBJ_Lantern(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 24;
        gp.obj[mapNum][i].worldY = gp.tileSize * 25;    
        i++;
        
        gp.obj[mapNum][i] = new OBJ_Tent(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 19;
        gp.obj[mapNum][i].worldY = gp.tileSize * 20;
        i++;
        
        gp.obj[mapNum][i] = new OBJ_Key(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 21;
        gp.obj[mapNum][i].worldY = gp.tileSize * 22;
        i++;
        
        gp.obj[mapNum][i] = new OBJ_Key(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 25;
        gp.obj[mapNum][i].worldY = gp.tileSize * 19;
        i++;
        
        gp.obj[mapNum][i] = new OBJ_Key(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 21;
        gp.obj[mapNum][i].worldY = gp.tileSize * 23;
        i++;
        
        gp.obj[mapNum][i] = new OBJ_Axe(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 34;
        gp.obj[mapNum][i].worldY = gp.tileSize * 20;
        i++;
        
        gp.obj[mapNum][i] = new OBJ_Shield_Blue(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 35;
        gp.obj[mapNum][i].worldY = gp.tileSize * 21;
        i++;
        
        gp.obj[mapNum][i] = new OBJ_Potion_Red(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 22;
        gp.obj[mapNum][i].worldY = gp.tileSize * 27;
        i++;
        
        gp.obj[mapNum][i] = new OBJ_Heart(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 22;
        gp.obj[mapNum][i].worldY = gp.tileSize * 29;
        i++;
        
        gp.obj[mapNum][i] = new OBJ_ManaCrystal(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 22;
        gp.obj[mapNum][i].worldY = gp.tileSize * 31;
        i++;
        
        gp.obj[mapNum][i] = new OBJ_Door(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 12;
        gp.obj[mapNum][i].worldY = gp.tileSize * 12;
        i++;
        
        mapNum = 1;
        gp.obj[mapNum][i] = new OBJ_Door(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 12;
        gp.obj[mapNum][i].worldY = gp.tileSize * 13;
        i++;
        
        mapNum = 2;
        i = 0;
        gp.obj[mapNum][i] = new OBJ_Chest(gp);
        gp.obj[mapNum][i].setLoot(new OBJ_Pickaxe(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize * 20;
        gp.obj[mapNum][i].worldY = gp.tileSize * 37;
        
        mapNum = 3;
        i = 0;
        gp.obj[mapNum][i] = new OBJ_Door_Iron(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 25;
        gp.obj[mapNum][i].worldY = gp.tileSize * 15;
        i++;
    }

    public void setNPC() {
        int mapNum = 0;
        gp.npc[mapNum][0] = new Npc_OldMan(gp);
        gp.npc[mapNum][0].worldX = gp.tileSize * 21;
        gp.npc[mapNum][0].worldY = gp.tileSize * 21;
        
        mapNum = 1;
        gp.npc[mapNum][0] = new Npc_Merchant(gp);
        gp.npc[mapNum][0].worldX = gp.tileSize * 12;
        gp.npc[mapNum][0].worldY = gp.tileSize * 7;
        
    }

    public void setMonster() {
        int i = 0;
        int mapNum = 0;
        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 21;
        gp.monster[mapNum][i].worldY = gp.tileSize * 38;
        i++;
        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 23;
        gp.monster[mapNum][i].worldY = gp.tileSize * 42;
        i++;
        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 24;
        gp.monster[mapNum][i].worldY = gp.tileSize * 37;
        i++;
        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 34;
        gp.monster[mapNum][i].worldY = gp.tileSize * 42;
        i++;
        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 38;
        gp.monster[mapNum][i].worldY = gp.tileSize * 42;
        i++;
        
        gp.monster[mapNum][i] = new MON_Orc(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 12;
        gp.monster[mapNum][i].worldY = gp.tileSize * 33;
        i++;
        
        mapNum = 1;
        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 38;
        gp.monster[mapNum][i].worldY = gp.tileSize * 42;
        i++;
        
        
         mapNum = 2;
        i = 0;

        gp.monster[mapNum][i] = new MON_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*34;
        gp.monster[mapNum][i].worldY = gp.tileSize*39;
        i++;

        gp.monster[mapNum][i] = new MON_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*36;
        gp.monster[mapNum][i].worldY = gp.tileSize*25;
        i++;

        gp.monster[mapNum][i] = new MON_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*39;
        gp.monster[mapNum][i].worldY = gp.tileSize*26;
        i++;
        
        gp.monster[mapNum][i] = new MON_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*17;
        gp.monster[mapNum][i].worldY = gp.tileSize*37;
        i++;

        
        gp.monster[mapNum][i] = new MON_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*28;
        gp.monster[mapNum][i].worldY = gp.tileSize*11;
        i++;

        gp.monster[mapNum][i] = new MON_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*10;
        gp.monster[mapNum][i].worldY = gp.tileSize*19;
        i++;
        
        mapNum = 3;
        i = 0;

        if(Progress.skeletonLordDefeated == false)
        {
            gp.monster[mapNum][i] = new MON_SkeletonLord(gp);
            gp.monster[mapNum][i].worldX = gp.tileSize*23;
            gp.monster[mapNum][i].worldY = gp.tileSize*16;
            i++;
        }

    }
    
    public void setInteractiveTile() {
        int i = 0;
        int mapNum = 0;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 27 , 12); i++;   
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 28 , 12); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 29 , 12); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 30 , 12); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 31 , 12); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 32 , 12); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 33 , 12); i++;
        
         mapNum = 2;
        i = 0;

        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,18,30);i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,17,31);i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,17,32);i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,17,34);i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,18,34);i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,10,33);i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,10,22);i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,38,24);i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,38,18);i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,38,19);i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,38,21);i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,18,13);i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,18,14);i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,22,28);i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,30,28);i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,32,28);i++;

        
        
        
    }
}