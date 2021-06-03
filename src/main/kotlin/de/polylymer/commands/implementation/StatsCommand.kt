package de.polylymer.commands.implementation

import de.polylymer.commands.SlashCommand
import dev.kord.common.Color
import dev.kord.common.annotation.KordPreview
import dev.kord.core.entity.Guild
import dev.kord.core.entity.Member
import dev.kord.core.entity.interaction.Interaction
import dev.kord.rest.Image
import dev.kord.rest.builder.message.EmbedBuilder
import de.polylymer.KordEXT
import de.polylymer.KordEXT.guild
import de.polylymer.KordEXT.member
import de.polylymer.database.MongoManager
import dev.kord.common.entity.Snowflake
import dev.kord.core.behavior.interaction.followUp
import dev.kord.core.entity.interaction.string

@KordPreview
object StatsCommand : SlashCommand(
    name = "stats",
    description = "Shows your profile-stats"/*,
    {
        string("advanced", "Shows your advanced stats"){
            required = false
        }
    }*/
) {

    override suspend fun handleCommand(interaction: Interaction) {
        interaction.ackowledgePublic().followUp {
            val advanced = interaction.command.options["advanced"]?.string()

            /* Beta von den Advanced Commands
            if(advanced.toLowerCase().contains("advanced")) {
                embed {
                    val guild: Guild = interaction.kord.getGuild(interaction.data.guildId.value!!)!!
                    val member: Member = guild.getMember(interaction.data.member.value!!.userId)
                    title = "Advanced stats of ${member.asUser().username}"
                    val thumb = EmbedBuilder.Thumbnail()
                    thumb.url = member.asMember().asUser().avatar.url
                    thumbnail = thumb
                    val foot = EmbedBuilder.Footer()
                    foot.icon = guild.getIconUrl(Image.Format.GIF)!!
                    foot.text = guild.name
                    footer = foot
                    color = Color(0, 250, 40)
                    if(!member.displayName.isNullOrEmpty()) {
                        field {
                            name = "Nickname"
                            value = member.displayName.toString()
                        }
                    }
                    field{
                        name = "ID"
                        value = member.id.asString
                    }
                    if (!member.memberData.premiumSince.value.isNullOrEmpty()) {
                        field {
                            name = "Nitro-booster since"
                            value = member.memberData.premiumSince.value!!.split("T")[0].replace("-", " ")
                        }
                    }
                    field{
                        name = "Messages"
                        value = "Coming soon"
                    }
                    field {
                        val capeOfTheDayChannel = interaction.guild().getChannel(Snowflake("811526154469113886"))
                        name = "Capes of the Day"
                        value =
                            "This user has ${MongoManager.getUserData(interaction.member().id.asString).capesOfTheDay} capes in ${capeOfTheDayChannel.mention}"
                    }
                }
            } else {*/
                embed {
                    val guild: Guild = interaction.kord.getGuild(interaction.data.guildId.value!!)!!
                    val member: Member = guild.getMember(interaction.data.member.value!!.userId)
                    title = "Profile of ${member.asUser().username}"
                    val thumb = EmbedBuilder.Thumbnail()
                    thumb.url = member.asMember().asUser().avatar.url
                    thumbnail = thumb
                    val foot = EmbedBuilder.Footer()
                    foot.icon = guild.getIconUrl(Image.Format.GIF)!!
                    foot.text = guild.name
                    footer = foot
                    color = Color(0, 251, 255)
                    field {
                        name = "Joined At"
                        value = member.memberData.joinedAt.split("T")[0].replace("-", " ")
                    }
                    field {
                        name = "Pending"
                        value = member.memberData.pending.discordBoolean.toString()
                    }
                    if (!member.memberData.premiumSince.value.isNullOrEmpty()) {
                        field {
                            name = "Nitro-booster since"
                            value = member.memberData.premiumSince.value!!.split("T")[0].replace("-", " ")
                        }
                    }
                    field {
                        name = "Roles"
                        var string = ""
                        for (id in member.memberData.roles) {
                            string += "@${interaction.guild().getRole(id).name}, "
                        }
                        value = if (interaction.member().asMember().memberData.roles.isEmpty()) {
                            "No roles"
                        } else {
                            string
                        }
                    }
                    field {
                        val capeOfTheDayChannel = interaction.guild().getChannel(Snowflake("811526154469113886"))
                        name = "Capes of the Day"
                        value =
                            "This user has ${MongoManager.getUserData(interaction.member().id.asString).capesOfTheDay} capes in ${capeOfTheDayChannel.mention}"
                    }
                }
            //}
        }
    }
}