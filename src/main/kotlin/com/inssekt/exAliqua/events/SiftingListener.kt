package com.inssekt.exAliqua.events

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.*
import kotlin.random.Random

class SiftingListener : Listener {


    @EventHandler
    fun onLoomOpen(event: InventoryOpenEvent) {
        val player = event.player
        val inventory = event.inventory

        val itemInHand = player.inventory.itemInMainHand

        // Check if the inventory being opened is a loom
        if (inventory.type == InventoryType.LOOM) {
            if (itemInHand.type == Material.GRAVEL || itemInHand.type == Material.DIRT || itemInHand.type == Material.SAND) {
                event.isCancelled = true // Cancel the event to prevent the GUI from opening
            }
        }
    }

    // Map to store the count of right-clicks for each player and the block location they're interacting with
    private val playerClickCounts = mutableMapOf<Player, Int>()
    private val playerBlockLocations = mutableMapOf<Player, Location?>()
    private val playerOriginalItems = mutableMapOf<Player, Material?>()

    // Loot tables for each sifting block (gravel, dirt, sand)
    private val gravelLootTable = listOf(
        ItemStack(Material.IRON_NUGGET) to 0.5, // 50% chance for iron nugget
        ItemStack(Material.GOLD_NUGGET) to 0.3,  // 30% chance for gold nugget
        ItemStack(Material.COAL) to 0.2          // 20% chance for coal
    )

    private val dirtLootTable = listOf(
        ItemStack(Material.WHEAT_SEEDS) to 0.6,  // 60% chance for wheat seeds
        ItemStack(Material.POTATO) to 0.2,       // 20% chance for potato
        ItemStack(Material.IRON_NUGGET) to 0.1,   // 10% chance for iron nugget
        ItemStack(Material.GOLD_NUGGET) to 0.1    // 10% chance for gold nugget
    )

    private val sandLootTable = listOf(
        ItemStack(Material.GLASS) to 0.5,        // 50% chance for glass
        ItemStack(Material.QUARTZ) to 0.3,        // 30% chance for quartz
        ItemStack(Material.DIAMOND) to 0.2       // 20% chance for diamond
    )

    @EventHandler
    fun onPlayerRightClick(event: PlayerInteractEvent) {
        val player = event.player
        val block = event.clickedBlock ?: return

        // Only handle right-clicks on looms
        if (event.action == Action.RIGHT_CLICK_BLOCK && block.type == Material.LOOM) {
            val itemInHand = player.inventory.itemInMainHand

            // Check if the player is holding gravel, dirt, or sand
            if (itemInHand.type == Material.GRAVEL || itemInHand.type == Material.DIRT || itemInHand.type == Material.SAND) {
                val blockLocation = block.location

                // Check if the player has interacted with the same block before
                if (playerBlockLocations.getOrDefault(player, null) == blockLocation) {
                    val clickCount = playerClickCounts.getOrDefault(player, 0)

                    playerClickCounts[player] = clickCount + 1
                    player.playSound(player.location, Sound.BLOCK_SAND_HIT, 1f, 1f)
                    val originalItem = playerOriginalItems.getOrDefault(player, Material.AIR)
                    // If the player has clicked the block 5 times, perform the sifting action
                    if (clickCount + 1 >= 5) {
                        if (originalItem != null) {
                            siftItem(player, originalItem)
                        }
                        playerClickCounts[player] = 0
                        playerBlockLocations[player] = null
                        playerOriginalItems[player] = null;
                        player.playSound(player.location, Sound.BLOCK_GRINDSTONE_USE, 1f, 1f)
                        event.isCancelled = true // Prevent default block interaction (if needed)
                    }
                } else {
                    // On first click, remove one item from the player's hand
                    if (itemInHand.amount > 0) {
                        itemInHand.amount -= 1
                        player.inventory.setItemInMainHand(itemInHand)
                    }
                    playerClickCounts[player] = 0
                    playerBlockLocations[player] = blockLocation
                    playerOriginalItems[player] = itemInHand.type
                    player.playSound(player.location, Sound.ENTITY_ITEM_PICKUP, 1f, 1f)
                    player.sendMessage("You are now sifting at this loom!")
                }
            } else {
                player.sendMessage("You need to hold gravel, dirt, or sand to sift!")
            }
        }
    }

    // Method to handle the sifting logic (what happens when the player reaches 5 clicks)
    private fun siftItem(player: Player, itemType: Material) {
        val lootTable = when (itemType) {
            Material.GRAVEL -> gravelLootTable
            Material.DIRT -> dirtLootTable
            Material.SAND -> sandLootTable
            else -> emptyList()
        }

        // Select an item from the loot table based on chances
        val selectedLoot = getLootFromTable(lootTable)
        if (selectedLoot != null) {
            // Add the sifted item to the player's inventory
            player.inventory.addItem(selectedLoot)
            player.sendMessage("You have sifted $itemType and received ${selectedLoot.type.name.lowercase(Locale.getDefault())}!")
        } else {
            player.sendMessage("The sifting process didn't produce any results!")
        }
    }

    // Select a loot item from the loot table based on the chances
    private fun getLootFromTable(lootTable: List<Pair<ItemStack, Double>>): ItemStack? {
        val totalChance = lootTable.sumByDouble { it.second }
        val randomChance = Random.nextDouble(0.0, totalChance)

        var accumulatedChance = 0.0
        for ((item, chance) in lootTable) {
            accumulatedChance += chance
            if (randomChance <= accumulatedChance) {
                return item.clone() // Return a copy of the item to avoid reference issues
            }
        }
        return null // If no loot is selected, return null
    }
}