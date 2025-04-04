package com.ecommerceapp.users.core.port.inbound.queries;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetUsersByIdListQuery {
    private List<String> ids;
}
