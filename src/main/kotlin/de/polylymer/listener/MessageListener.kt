package de.polylymer.listener

import com.gitlab.kordlib.kordx.emoji.Emojis
import de.polylymer.Manager
import dev.kord.common.entity.Snowflake
import dev.kord.core.behavior.channel.MessageChannelBehavior
import dev.kord.core.behavior.reply
import dev.kord.core.entity.ReactionEmoji
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.on
import kotlinx.coroutines.flow.collect
import java.util.*

object MessageListener {

    init {
        Manager.client.on<MessageCreateEvent> {
            val msg = this.message.content
            if (this.member != null) {
                //cape of the day support for submitted cape-channel
                if (this.member!!.isBot) {
                    if (this.message.channelId.asString == "790946998962487316") {
                        if (this.message.embeds.isNotEmpty()) {
                            //dont ask, mies traurig
                            Thread.sleep(2000)
                            this.message.addReaction(ReactionEmoji.Unicode(Emojis.star.unicode))
                        }
                    }
                } else {
                    if (this.message.channelId.asString == "774596130541142037" && this.message.content.isNotEmpty()) {
                        val umfragenChannel =
                            this.getGuild()!!.getChannel(Snowflake("821017903381741609")) as MessageChannelBehavior
                        val invalidIdeasChannel =
                            this.getGuild()!!.getChannel(Snowflake("821023603369836546")) as MessageChannelBehavior
                        var found = false
                        umfragenChannel.messages.collect {
                            if (!found) {
                                if (it.asMessage().content.toLowerCase().contains(this.message.content.toLowerCase())) {
                                    this.message.channel.createMessage("Diese Idee ist bereits in ${umfragenChannel.mention}!")
                                    invalidIdeasChannel.createMessage("\"${this.message.content}\" by ${this.message.author!!.mention}")
                                    this.message.delete()
                                    found = true
                                }
                            }
                        }
                    }

                    //invite detection
                    if (this.message.content.toLowerCase().contains("discord.gg")) {
                        this.message.delete()
                        this.member!!.kick("Posting invites")
                    } else if (this.message.channelId.asString == "774982518133751858") {
                        //cape of the day support for gallery channel
                        if (this.message.attachments.isNotEmpty()) {
                            if (this.message.attachments.toList()[0].isImage) {
                                if (this.message.attachments.toList()[0].height == 256 && this.message.attachments.toList()[0].width == 512) {
                                    this.message.addReaction(ReactionEmoji.Unicode(Emojis.star.unicode))
                                }
                            }
                        }
                    }
                    //'lies die pins' feature
                    if (this.message.content.toLowerCase().contains("lies") && this.message.content.toLowerCase().contains("pins")) {
                        this.message.channel.pinnedMessages.collect {
                            this.message.channel.createMessage(it.content)
                        }
                    }

                    if (msg.contains("xD")) {
                        if(this.message.author!!.id.asString != "743435051512889434" /*Indikativ*/) {
                            if(!msg.contains("hast du eig ein bisschen obsi")) {
                                this.message.addReaction(ReactionEmoji.Unicode(Emojis.one.unicode))
                                this.message.addReaction(ReactionEmoji.Unicode(Emojis.two.unicode))
                            } else {
                                if(Random().nextInt(5) == 2) {
                                    this.message.addReaction(ReactionEmoji.Unicode(Emojis.regionalIndicatorO.unicode))
                                    this.message.addReaction(ReactionEmoji.Unicode(Emojis.regionalIndicatorB.unicode))
                                    this.message.addReaction(ReactionEmoji.Unicode(Emojis.regionalIndicatorS.unicode))
                                    this.message.addReaction(ReactionEmoji.Unicode(Emojis.regionalIndicatorI.unicode))
                                } else {
                                    this.message.addReaction(ReactionEmoji.Unicode(Emojis.regionalIndicatorN.unicode))
                                    this.message.addReaction(ReactionEmoji.Unicode(Emojis.regionalIndicatorE.unicode))
                                }
                            }
                        }
                    }

                    if(msg.contains("@!370219437242187788")) {
                        this.message.reply { content = "Matrix will nicht gepingt werden, daher der Prefix..." }
                    }

                    //Rules Listener
                    if (msg.contains("#!774271970741190666")){
                        if(msg.contains("1.")){
                            this.message.channel.createMessage(
                                "**Rules - Regeln** \n" +
                                        Emojis.grinning.unicode + "1. Seid lieb zu einander, behandelt euch mit Respekt.\n" +
                                        Emojis.grinning.unicode + "1. Be kind and respectful to others."
                            )
                        }
                        if(msg.contains("2.")){
                            this.message.channel.createMessage(
                                "**Rules - Regeln** \n" +
                                        Emojis.warning.unicode + "2. Anstößige Inhalte sind untersagt\n" +
                                        Emojis.warning.unicode + "2. NSFW content is not allowed."
                            )
                        }
                        if(msg.contains("3.")){
                            this.message.channel.createMessage(
                                "**Rules - Regeln** \n" +
                                        Emojis.mega.unicode + "3. Werbung ist verboten!\n" +
                                        Emojis.mega.unicode + "3. Advertisment is forbidden!"
                            )
                        }
                        if(msg.contains("4.")){
                            this.message.channel.createMessage(
                                "**Rules - Regeln** \n" +
                                        Emojis.shield.unicode + "4. Eure Privaten Informationen bleiben eure privaten Informationen!\n" +
                                        Emojis.shield.unicode + "4. Your private informaton stays your private information!"
                            )
                        }
                        if(msg.contains("5.")){
                            this.message.channel.createMessage(
                                "**Rules - Regeln** \n" +
                                        Emojis.noBell.unicode + "5. Grundlose Pings oder \"Ghost Pings\" sind verboten!\n" +
                                        Emojis.noBell.unicode + "5. Unnecessary pings and \"ghost pings\" aren't allowed!"
                            )
                        }
                        if(msg.contains("6.")){
                            this.message.channel.createMessage(
                                "**Rules - Regeln** \n" +
                                        Emojis.incomingEnvelope.unicode + "6. Spamming ist untersagt!\n" +
                                        Emojis.incomingEnvelope.unicode + "6. Spamming is forbidden!"
                            )
                        }
                        if(msg.contains("7.")){
                            this.message.channel.createMessage(
                                "**Rules - Regeln** \n" +
                                        Emojis.link.unicode + "7. Links zu Webseiten / Discord Servern oder anderem sind untersagt!\n" +
                                        Emojis.link.unicode + "7. Links to other Discord servers or websites that don't have to do something with the NoRisk-Client are forbidden!"
                            )
                        }
                        if(msg.contains("8.")){
                            this.message.channel.createMessage(
                                "**Rules - Regeln** \n" +
                                        Emojis.thinking.unicode + "8. Das Team muss sich zu keinem Zeitpunkt für eine Strafe rechtfertigen.\n" +
                                        Emojis.thinking.unicode + "8. The team does not have to justify any of it's actions."
                            )
                        }
                        if(msg.contains("9.")){
                            this.message.channel.createMessage(
                                "**Rules - Regeln** \n" +
                                        Emojis.airplane.unicode + "9. Das weiterschicken des Clients ist verboten, solange der Downloadlink offline ist.\n" +
                                        Emojis.airplane.unicode + "9. Should the client be offline you must NOT send it to others."
                            )
                        }
                        if(msg.contains("10.")){
                            this.message.channel.createMessage(
                                "**Rules - Regeln** \n" +
                                        Emojis.question.unicode + "10. Support-Missbrauch ist verboten und führt zu einem Bann.\n" +
                                        Emojis.question.unicode + "10. Support-Abuse is forbidden and will be punished with a ban."
                            )
                        }
                        if(msg.contains("11.")){
                            this.message.channel.createMessage(
                                "**Rules - Regeln** \n" +
                                        Emojis.lockWithInkPen.unicode + "11. Nicknames dürfen nicht identisch mit jeglichen Usern sein und keine Ränge beinhalten. (z.B. [Admin] NoRiskk wäre verboten).\n" +
                                        Emojis.lockWithInkPen.unicode + "11. Nicknames mustn't be identical to the one of others and they also mustn't contain any ranks.. (e.g. [Admin] NoRisk is forbidden)."
                            )
                        }
                        if(msg.contains("12.")){
                            this.message.channel.createMessage(
                                "**Rules - Regeln** \n" +
                                        Emojis.x.unicode + "12. Die Ausrede \"Das wusste ich nicht\" oder \"Das war mein Hund\" zählt hier nicht!\n" +
                                        Emojis.x.unicode + "12. The apology \"I didn't know that\" or \"My dog did that\" doesn't count!"
                            )
                        }
                    }
                }
            }
        }
    }

}
