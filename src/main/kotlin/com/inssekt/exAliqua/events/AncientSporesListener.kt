package com.inssekt.exAliqua.events

import com.inssekt.exAliqua.CustomItems
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

class AncientSporesListener : Listener {

    @EventHandler
    fun onRightClickWithAncientSpores(event: PlayerInteractEvent)
    {
        val player = event.player
        val clickedBlock = event.clickedBlock
        val itemInHand = player.inventory.itemInMainHand

        if (clickedBlock != null && clickedBlock.type == Material.DIRT && isAncientSpores(itemInHand)) {
            clickedBlock.type = Material.MYCELIUM
            itemInHand.amount = itemInHand.amount - 1
            event.isCancelled = true
        }
    }

private fun isAncientSpores(item: ItemStack): Boolean {
    val spores = CustomItems.ANCIENT_SPORES
    return item.type == spores.type && item.itemMeta?.displayName == spores.itemMeta?.displayName
}
}