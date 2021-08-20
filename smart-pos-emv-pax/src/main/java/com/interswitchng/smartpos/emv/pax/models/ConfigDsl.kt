package com.interswitchng.smartpos.emv.pax.models

import com.interswitchng.smartpos.shared.models.posconfig.EmvAIDs
import com.interswitchng.smartpos.shared.models.posconfig.EmvCapk
import com.interswitchng.smartpos.shared.models.posconfig.EmvCard


internal fun aid(closure: EmvAIDs.() -> Unit): EmvAIDs {
    val anAID = com.interswitchng.smartpos.shared.models.posconfig.EmvAIDs()
    closure(anAID)
    return anAID
}

internal fun card(anAID: EmvAIDs, closure: EmvCard.() -> Unit): EmvCard {
    val card = com.interswitchng.smartpos.shared.models.posconfig.EmvCard()
    closure(card)
    anAID.cards.add(card)
    return card
}

internal fun key(aCard: EmvCard, closure: EmvCapk.() -> Unit): EmvCapk {
    val key = com.interswitchng.smartpos.shared.models.posconfig.EmvCapk()
    closure(key)
    aCard.keys.add(key)
    return key
}