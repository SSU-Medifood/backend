package Mefo.server.domain.storage.service;

import Mefo.server.domain.storage.dto.StorageRequest;
import Mefo.server.domain.storage.entity.Storage;
import Mefo.server.domain.storage.repository.StorageRepository;
import Mefo.server.domain.user.entity.User;
import Mefo.server.global.error.ErrorCode;
import Mefo.server.global.error.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class StorageService {
    private final StorageRepository storageRepository;
    //보관함 생성하기
    @Transactional
    public Storage createStorage(User user, StorageRequest storageRequest){
        Storage storage = new Storage(user, storageRequest.getName());
        user.getStorages().add(storage);
        storageRepository.save(storage);
        return storage;
    }

    //보관함 이름 수정
    @Transactional
    public Storage patchStorage(User user, Long storageId, StorageRequest storageRequest){
        Storage storage = storageRepository.findByIdAndUserId(storageId, user.getId())
                .orElseThrow(()-> new BusinessException(ErrorCode.STORAGE_DOESNT_EXIST));
        storage.patchStorageName(storageRequest.getName());
        storageRepository.save(storage);
        return storage;
    }

    //보관함 삭제하기
    @Transactional
    public void deleteStorage(User user, Long storageId){
        Storage storage = storageRepository.findByIdAndUserId(storageId, user.getId())
                .orElseThrow(()-> new BusinessException(ErrorCode.STORAGE_DOESNT_EXIST));
        user.getStorages().remove(storage);
        storageRepository.delete(storage);
    }

}
