package ru.practicum.explore_with_me;

import com.google.protobuf.Timestamp;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.stats.proto.ActionTypeProto;
import ru.practicum.ewm.stats.proto.UserActionControllerGrpc;
import ru.practicum.ewm.stats.proto.UserActionProto;

import java.time.Instant;

@Slf4j
@Service
public class UserActionGrpcClient {
    @GrpcClient("collector")
    UserActionControllerGrpc.UserActionControllerBlockingStub client;

    public void collectUserAction(long userId, long eventId, ActionTypeProto actionType) {
        try {
            UserActionProto collectUserActionRequest = UserActionProto.newBuilder()
                    .setUserId(userId)
                    .setEventId(eventId)
                    .setActionType(actionType)
                    .setTimestamp(getTimestamp())
                    .build();

            client.collectUserAction(collectUserActionRequest);
        } catch (Exception e) {
            log.error("Failed to send request", e);
        }
    }

    private Timestamp getTimestamp() {
        Instant now = Instant.now();
        return Timestamp.newBuilder()
                .setNanos(now.getNano())
                .setSeconds(now.getEpochSecond())
                .build();
    }
}