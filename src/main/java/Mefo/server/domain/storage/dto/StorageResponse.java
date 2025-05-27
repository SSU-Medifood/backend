package Mefo.server.domain.storage.dto;

import Mefo.server.domain.storage.entity.Storage;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class StorageResponse {
    private Long id;
    private String name;
    public static StorageResponse from(Storage storage){
        return StorageResponse.builder()
                .id(storage.getId())
                .name(storage.getStorageName())
                .build();
    }

    public static List<StorageResponse> from(List<Storage> storages){
        List<StorageResponse> storageResponses = new ArrayList<>();
        for(Storage storage : storages){
            StorageResponse storageResponse = StorageResponse.builder()
                    .id(storage.getId())
                    .name(storage.getStorageName())
                    .build();
            storageResponses.add(storageResponse);
        }
        return storageResponses;
    }
}
