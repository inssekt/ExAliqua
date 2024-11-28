package com.inssekt.exAliqua.gui

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class ExItemsGUIListener : Listener {

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val player = event.whoClicked as? Player ?: return
        val clickedItem = event.currentItem ?: return

        // Ensure the clicked inventory is the custom items GUI
        if (event.view.title == "§6Custom Items" && clickedItem.hasItemMeta()) {
            event.isCancelled = true // Prevent default behavior (e.g., taking items)

            // Give the player the clicked item
            player.inventory.addItem(clickedItem)
            player.sendMessage("§aYou received a ${clickedItem.itemMeta?.displayName}!")
        }
    }
}