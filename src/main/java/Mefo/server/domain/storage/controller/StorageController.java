package Mefo.server.domain.storage.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/storage")
@RequiredArgsConstructor
@Tag(name="Storage")
public class StorageController {
    //보관함 생성하기
    //보관함 삭제하기
    //보관함 이름 수정
    //보관함 전체 불러오기
    //보관함에 있는 레시피 호출
}
