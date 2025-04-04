package com.ecommerceapp.users.core.port.inbound.results;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetUsersByIdListResult {
    List<UserResult> users;
}
