package com.ecommerceapp.libs.grpc.mappers;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import com.ecommerceapp.libs.queries.PaginationQuery;
import com.google.protobuf.ProtocolStringList;
import com.google.protobuf.Timestamp;

import products.Pagination.PaginationRequest;

@Mapper(componentModel = "spring", collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface ProtoMapper {
    PaginationQuery map(PaginationRequest request);

    // for string list

    @Named("mapProtoStringListToString")
    default List<String> mapProtoStringList(ProtocolStringList in) {
        if (in == null) {
            return new ArrayList<>();
        }
        return in.stream().toList();
    }

    // for timestamp
    @Named("mapInstantToTimestamp")
    default Timestamp mapInstantToTimestamp(Instant in) {
        if (in == null) {
            return Timestamp.getDefaultInstance();
        }

        return Timestamp.newBuilder()
                .setSeconds(in.getEpochSecond())
                .setNanos(in.getNano())
                .build();
    }

    @Named("mapTimestampToInstant")
    default Instant mapTimestampToInstant(Timestamp timestamp) {
        if (timestamp == null || timestamp.equals(Timestamp.getDefaultInstance())) {
            return null; // Return null if Timestamp is empty
        }
        return Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());
    }

    // for objectId
    @Named("mapStringToObjectId")
    default ObjectId mapStringToObjectId(String id) {
        return new ObjectId(id);
    }
}
