package org.example.coreapi

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.*

data class CreateThingCommand(val name: String) {
    var id: UUID = UUID.randomUUID()
}

data class ThingCreatedEvent(@TargetAggregateIdentifier val id: UUID, val name: String)

data class ThingQuery(val id:UUID)
data class Thing(val id: UUID, val name: String)