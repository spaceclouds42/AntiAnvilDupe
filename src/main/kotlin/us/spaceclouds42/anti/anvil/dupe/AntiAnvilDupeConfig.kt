@file:UseSerializers(TextSerializer::class)

package us.spaceclouds42.anti.anvil.dupe

import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import net.minecraft.text.Text
import org.spaceserve.config.IConfigure
import org.spaceserve.config.serializers.TextSerializer
import org.spaceserve.ekho.ekho

@Serializable
data class AntiAnvilDupeConfig(
    var enableAntiDupe: Boolean = true,
    var sendMessageToStaff: Boolean = true,
    var sendMessageToAll: Boolean = false,
) : IConfigure
