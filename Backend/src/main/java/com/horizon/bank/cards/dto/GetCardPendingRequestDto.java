package com.horizon.bank.cards.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.Nullable;

@Getter
@Setter
public class GetCardPendingRequestDto {
    @JsonAlias("user_id")
    @Nullable
    public String UserId;

    @JsonAlias("account_number")
    @Nullable
    public String accountNumber;
}
