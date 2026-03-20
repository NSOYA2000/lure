package com.lurefish.service.impl;

import com.lurefish.model.Fish;
import com.lurefish.repository.FishRepository;
import com.lurefish.service.FishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FishServiceImpl implements FishService {

    private final FishRepository fishRepository;

    @Autowired
    public FishServiceImpl(FishRepository fishRepository) {
        this.fishRepository = fishRepository;
    }

    @Override
    public List<Fish> getAllFish() {
        return fishRepository.findAll();
    }

    @Override
    public Fish getFishById(Long id) {
        return fishRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fish not found with id: " + id));
    }

    @Override
    public Fish createFish(Fish fish) {
        // 确保ID为null以触发自动生成
        fish.setId(null);
        return fishRepository.save(fish);
    }

    @Override
    public Fish updateFish(Long id, Fish fish) {
        // 检查鱼种是否存在
        Fish existingFish = getFishById(id);

        // 更新字段
        existingFish.setName(fish.getName());
        existingFish.setScientificName(fish.getScientificName());
        existingFish.setDescription(fish.getDescription());
        existingFish.setHabitat(fish.getHabitat());
        existingFish.setSeason(fish.getSeason());
        existingFish.setWaterLayer(fish.getWaterLayer());
        existingFish.setDifficulty(fish.getDifficulty());
        existingFish.setTackle(fish.getTackle());
        existingFish.setLures(fish.getLures());
        existingFish.setTechniques(fish.getTechniques());
        existingFish.setTips(fish.getTips());
        existingFish.setSizeRange(fish.getSizeRange());
        existingFish.setWeightRange(fish.getWeightRange());
        existingFish.setImageUrl(fish.getImageUrl());

        return fishRepository.save(existingFish);
    }

    @Override
    public void deleteFish(Long id) {
        // 检查鱼种是否存在
        Fish fish = getFishById(id);
        fishRepository.delete(fish);
    }
}