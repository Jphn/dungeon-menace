/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeto.dugeonmenace.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import projeto.dugeonmenace.GamePanel;
import projeto.dugeonmenace.entity.Entity;
import projeto.dugeonmenace.objectsSprite.*;

/**
 *
 * @author T-GAMER
 */
public class SaveLoad {

    GamePanel gp;

    public SaveLoad(GamePanel gp) {
        this.gp = gp;
    }

    public void save() {
        try {

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));

            DataStorage ds = new DataStorage();

            ds.level = gp.player.level;
            ds.maxLife = gp.player.maxLife;
            ds.life = gp.player.life;
            ds.maxMana = gp.player.maxMana;
            ds.mana = gp.player.mana;
            ds.strength = gp.player.strength;
            ds.dexterity = gp.player.dexterity;
            ds.exp = gp.player.exp;
            ds.nextLevelExp = gp.player.nextLevelExp;
            ds.coin = gp.player.coin;
            
            for(int i =0; i<gp.player.inventory.size();i++){
                ds.itemNames.add(gp.player.inventory.get(i).name);
                ds.itemAmounts.add( gp.player.inventory.get(i).amount);
            
            }
            // PLAYER EQUIPAMENT
            ds.currentWeaponSlot = gp.player.getCurrentWeaponSlot();
            ds.currentShieldSlot = gp.player.getCurrentShieldSlot();
            
            String mapObjectNames[][];
            int mapObjectWorldX[][];
            int mapObjectWorldY[][];
            
            for(int i =0; i<gp.player.inventory.size();i++){
            
            }
            
            //Write the DataStorage object
            oos.writeObject(ds);

        } catch (Exception e) {
            System.out.println("Save file exception!");
        }

    }

    public void load() {
        try {

            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));

            //read DataStorage;
            DataStorage ds = (DataStorage) ois.readObject();

            gp.player.level = ds.level;
            gp.player.maxLife = ds.maxLife;
            gp.player.life = ds.life;
            gp.player.maxMana = ds.maxMana;
            gp.player.mana = ds.mana;
            gp.player.strength = ds.strength;
            gp.player.dexterity = ds.dexterity;
            gp.player.exp = ds.exp;
            gp.player.nextLevelExp = ds.nextLevelExp;
            gp.player.coin = ds.coin;
            
            gp.player.inventory.clear();
            for(int i =0; i<ds.itemNames.size();i++){
                gp.player.inventory.add(getObject(ds.itemNames.get(i)));
                gp.player.inventory.get(i).amount = ds.itemAmounts.get(i);
            
            }
            
            // PLAYER EQUIPAMENT
            gp.player.currentWeapon =gp.player.inventory.get(ds.currentWeaponSlot);
            gp.player.currentShield =gp.player.inventory.get(ds.currentShieldSlot);
            ds.currentShieldSlot = gp.player.getCurrentShieldSlot();
            gp.player.getAttack();
            gp.player.getDefense();
            gp.player.getAttackImage();
            
            
            
        } catch (Exception e) {
            System.out.println("Load file exception!");
        }

    }
    
    public Entity getObject(String itemName){
        Entity obj = null;
        
        
        switch(itemName){
            case "Woodcutter's Axe": obj = new OBJ_Axe(gp);break;
            case "Boots": obj = new OBJ_Boots(gp);break;
            case "Key": obj = new OBJ_Key(gp);break;
            case "Lantern": obj = new OBJ_Lantern(gp);break;
            case "Red Potion": obj = new OBJ_Potion_Red(gp);break;
            case "Blue Shield": obj = new OBJ_Shield_Blue(gp);break;
            case "Wood Shield": obj = new OBJ_Shield_Wood(gp);break;
            case "Normal Sword": obj = new OBJ_Sword_Normal(gp);break;
            case "Tent": obj = new OBJ_Tent(gp);break;
            case "Door": obj = new OBJ_Door(gp);break;
            case "Chest": obj = new OBJ_Chest(gp);break;
        }
        
        return obj;
    }
}


