package com.inssekt.exAliqua.events

import com.inssekt.exAliqua.CustomItems
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class HammerEventListener : Listener {

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        val player = event.player
        val itemInHand: ItemStack = player.inventory.itemInMainHand

        if (!isValidHammer(itemInHand)) return

        val block = event.block

        val newBlockType: Material? = when (block.type) {
            Material.COBBLESTONE -> Material.GRAVEL
            Material.GRAVEL -> Material.SAND
            Material.SAND -> Material.DIRT
            else -> null
        }

        if (newBlockType != null) {
            event.isCancelled = true
            block.type = Material.AIR
            block.world.dropItemNaturally(block.location, ItemStack(newBlockType)) // Drop the new item

            decreaseHammerDurability(player, itemInHand)
            player.world.playSound(player.location, Sound.BLOCK_ANVIL_LAND, 1f, 1f)
        }
    }

    private fun isValidHammer(itemInHand: ItemStack): Boolean {
        val meta: ItemMeta = itemInHand.itemMeta ?: return false

        // Check if the display name matches any hammer variant in the HAMMER_MAP
        return CustomItems.HAMMER_MAP.values.any { hammer ->
            val hammerMeta = hammer.itemMeta
            hammerMeta != null && hammerMeta.displayName == meta.displayName
        }
    }

    private fun decreaseHammerDurability(player: Player, itemInHand: ItemStack) {
        val durability = itemInHand.durability

        if (durability < itemInHand.type.maxDurability) {
            itemInHand.durability = (durability + 1).toShort()
        }
    }
}