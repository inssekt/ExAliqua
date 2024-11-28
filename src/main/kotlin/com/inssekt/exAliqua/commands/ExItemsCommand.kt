package com.inssekt.exAliqua.commands

import com.inssekt.exAliqua.CustomItems
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

class ExItemsCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender is Player) {
            openItemsGUI(sender)
            return true
        }
        return false
    }

    private fun openItemsGUI(player: Player) {
        // Create a new inventory with a title and 27 slots (standard chest size)
        val inventory: Inventory = Bukkit.createInventory(null, 27, "§6Custom Items")

        // Add items from CustomItems.HAMMER_MAP to the inventory
        var index = 0
        for (item in CustomItems.ITEM_MAP.values) {
            inventory.setItem(index++, item)
        }

        // Open the inventory for the player
        player.openInventory(inventory)
    }
}