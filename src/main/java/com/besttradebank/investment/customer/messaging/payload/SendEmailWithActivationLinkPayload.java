package com.besttradebank.investment.customer.messaging.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendEmailWithActivationLinkPayload {

    private String recipient;
    private String sender;
    private String emailSubject;
    private String emailBody;

}
