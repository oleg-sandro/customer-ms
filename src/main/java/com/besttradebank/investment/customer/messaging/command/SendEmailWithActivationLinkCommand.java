package com.besttradebank.investment.customer.messaging.command;

import com.besttradebank.investment.customer.messaging.payload.SendEmailWithActivationLinkPayload;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.UtilityClass;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SendEmailWithActivationLinkCommand extends CustomerCommand<SendEmailWithActivationLinkPayload> {

    public SendEmailWithActivationLinkCommand(SendEmailWithActivationLinkPayload payload) {
        super(payload);
    }

    public SendEmailWithActivationLinkCommand(UUID commandId, SendEmailWithActivationLinkPayload payload) {
        super(commandId, payload);
    }

    @Override
    public String getTopic() {
        return Topic.NAME;
    }

    @UtilityClass
    public static class Topic {

        public static final String NAME = "send_email_with_activation_link_command";

    }
}
