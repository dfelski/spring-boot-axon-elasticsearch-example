package org.example.command;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.coreapi.CreateThingCommand;
import org.example.coreapi.ThingCreatedEvent;

import java.util.UUID;

@Aggregate
class ThingAggregate {

    @AggregateIdentifier
    private UUID id;
    private String name;

    protected ThingAggregate(){}

    @CommandHandler
    public ThingAggregate(CreateThingCommand createThingCommand){
        AggregateLifecycle.apply(new ThingCreatedEvent(createThingCommand.getId(), createThingCommand.getName()));
    }
    

    @EventSourcingHandler
    protected void on(ThingCreatedEvent thingCreatedEvent){
        this.id = thingCreatedEvent.getId();
    }

}
