/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projeto.dugeonmenace.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author T-GAMER
 */
public class DataStorage implements Serializable {
    //Player STATS
    int maxLife = 6;
    int life = maxLife; // 6 de vida = 3 corações
    int level = 1;
    int maxMana = 4;
    int ammo = 10;
    int mana = maxMana;
    int strength = 1;
    int dexterity = 1;
    int exp = 0;
    int nextLevelExp = 5;
    int coin = 500; 
    
    
    //PLAYER INVENTORY
    
    ArrayList<String> itemNames = new ArrayList<>();
    ArrayList<Integer> itemAmounts = new ArrayList<>();
    
    int currentWeaponSlot;
    int currentShieldSlot;
    //OBJECTS IN THE MAP
    
    String mapObjectNames[][];
    
    int mapObjectWorldX[][];
    int mapObjectWorldY[][];
    
    String mapObjectLootNames[][];
    boolean mapObjectOpened[][];
    
    
}
