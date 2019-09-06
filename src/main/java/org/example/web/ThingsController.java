package org.example.web;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.example.coreapi.CreateThingCommand;
import org.example.coreapi.Thing;
import org.example.coreapi.ThingQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
class ThingsController {

    @Autowired
    private QueryGateway queryGateway;

    @Autowired
    private CommandGateway commandGateway;

    @GetMapping("/{id}")
    ThingDTO get(@PathVariable UUID id) throws Exception{
        Thing thing =  queryGateway.query(new ThingQuery(id), Thing.class).get();
        return new ThingDTO(thing.getId(), thing.getName());
    }

    @PutMapping("/")
    String add(@RequestBody ThingDTO thingDTO) throws Exception{
        CreateThingCommand createThingCommand = new CreateThingCommand(thingDTO.getName());
        commandGateway.send(createThingCommand);
        return createThingCommand.getId().toString();
    }
}
