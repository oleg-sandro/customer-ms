package com.besttradebank.investment.customer.messaging.command;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class CustomerCommand<T> {

    private UUID commandId;
    private T payload;

    protected CustomerCommand(T payload) {
        commandId = UUID.randomUUID();
        this.payload = payload;
    }

    public abstract String getTopic();
}
