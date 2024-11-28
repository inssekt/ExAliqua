package com.inssekt.exAliqua.events

import com.inssekt.exAliqua.CustomItems
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.Material
import org.bukkit.Sound

class RockGatheringListener : Listener {

    @EventHandler
    fun onPlayerShiftRightClick(event: PlayerInteractEvent) {
        val player = event.player
        val block = event.clickedBlock

        if (event.hand != org.bukkit.inventory.EquipmentSlot.HAND) {
            return
        }

        // Ensure the player is sneaking and right-clicked a valid block
        if (event.action == Action.RIGHT_CLICK_BLOCK &&
            player.isSneaking &&
            (block?.type == Material.GRASS_BLOCK || block?.type == Material.DIRT) &&
            player.inventory.itemInMainHand.type == Material.AIR
        ) {
            event.isCancelled = true // Cancel any other interactions

            // Give the player the custom rock
            player.inventory.addItem(CustomItems.ROCK)
            player.playSound(player.location, Sound.ENTITY_ITEM_PICKUP, 1f, 1f)
        }
    }
}