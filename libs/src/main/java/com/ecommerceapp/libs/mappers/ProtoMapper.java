package com.ecommerceapp.libs.mappers;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import com.google.protobuf.LazyStringArrayList;
import com.google.protobuf.ProtocolStringList;
import com.google.protobuf.Timestamp;

@Mapper(componentModel = "spring")
public interface ProtoMapper {

    // for string list

    @Named("mapProtoStringListToString")
    default List<String> mapProtoStringList(ProtocolStringList in) {
        if (in == null) {
            return new ArrayList<>();
        }
        return in.stream().toList();
    }

    @Named("mapListStringToProtoStringList")
    default ProtocolStringList mapListStringToProtoStringList(List<String> in) {
        return in == null ? new LazyStringArrayList() : new LazyStringArrayList(in);

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
