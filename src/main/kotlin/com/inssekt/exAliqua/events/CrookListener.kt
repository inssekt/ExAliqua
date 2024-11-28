package com.inssekt.exAliqua.events

import com.inssekt.exAliqua.CustomItems
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

class CrookListener : Listener {

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent){
        val block = event.block
        val player = event.player
        val itemInHand = player.inventory.itemInMainHand

        if (block.type.name.contains("LEAVES") && isCrook(itemInHand)) {
            val chance = Random.nextInt(100)
            player.sendMessage(">$chance")

            // 5% chance to drop the silkworm item
            if (chance < 5) {
                dropSilkworm(player, block)
            }
        }
    }

    private fun dropSilkworm(player: Player, block: Block) {
        val silkworm = CustomItems.SILKWORM

        block.world.dropItemNaturally(block.location, silkworm)

        player.sendMessage(ChatColor.GREEN.toString() + "You found a Silkworm!")
        player.playSound(player.location, Sound.ENTITY_SILVERFISH_AMBIENT, 1f, 1f)
    }

    fun isCrook(item: ItemStack): Boolean {
        return item.type == CustomItems.CROOK.type && item.itemMeta?.displayName == CustomItems.CROOK.itemMeta?.displayName
    }
}