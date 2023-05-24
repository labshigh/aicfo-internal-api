package com.labshigh.aicfo.internal.api.common.utils.models;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TelegramMessage {
  public String chat_id;
  public String text;
}
