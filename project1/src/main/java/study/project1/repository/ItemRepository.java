package study.project1.repository;

import study.project1.domain.Item;

import java.util.List;

public interface ItemRepository {

    // 저장
    public Item save(Item item);
    // 하나 조회
    public Item find(Long id);
    // 전체 조회
    public List<Item> findAll();
    // 수정
    public void update(Long id, Item updateParam);
    // 삭제
    public void delete(Long id);

    public void clearStore();

}
